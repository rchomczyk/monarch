package moe.rafal.monarch.language;

import moe.rafal.monarch.datasource.PooledDatasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

class LanguageRepositorySql implements LanguageRepository {

    private static final String LANGUAGE_SCHEMA_CREATE_QUERY =
        """
        CREATE TABLE IF NOT EXISTS
            `monarch_languages`
        (
            `id` INT(11) PRIMARY KEY AUTO_INCREMENT,
            `tag` VARCHAR(35) NOT NULL
        );
        """;
    private static final String LANGUAGE_FIND_ALL_QUERY =
        """
        SELECT
            `id`, `tag`
        FROM
            `monarch_languages`;
        """;
    private static final String LANGUAGE_SAVE_QUERY =
        """
        INSERT INTO `monarch_languages`
            (`tag`)
        VALUES
            (?);
        """;
    private final PooledDatasource datasource;

    public LanguageRepositorySql(PooledDatasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public void createSchema() {
        try (Connection connection = datasource.borrowConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(LANGUAGE_SCHEMA_CREATE_QUERY);
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not create schema for languages.");
        }
    }

    @Override
    public Set<Language> findLanguages() throws LanguageFindException {
        try (Connection connection = datasource.borrowConnection(); Statement statement = connection.createStatement()) {
            Set<Language> languages = new HashSet<>();

            ResultSet resultSet = statement.executeQuery(LANGUAGE_FIND_ALL_QUERY);
            while (resultSet.next()) {
                languages.add(findLanguage(resultSet));
            }

            return languages;
        } catch (SQLException exception) {
            throw new LanguageFindException("Could not find languages, because of occurrence of unexpected exception.", exception);
        }
    }

    private Language findLanguage(ResultSet resultSet) throws SQLException {
        return new Language(resultSet.getInt("id"), resultSet.getString("tag"));
    }

    @Override
    public Language saveLanguage(String languageTag) throws LanguageSaveException {
        try (Connection connection = datasource.borrowConnection(); PreparedStatement statement = connection.prepareStatement(LANGUAGE_SAVE_QUERY, RETURN_GENERATED_KEYS)) {
            statement.setString(1, languageTag);
            statement.executeUpdate();
            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    return new Language(keys.getInt(1), languageTag);
                }

                throw new LanguageSaveException("Could not find generated id for the language.");
            }
        } catch (SQLException exception) {
            throw new LanguageSaveException("Could not save language, because of occurrence of unexpected exception.", exception);
        }
    }
}
