package moe.rafal.monarch.user;

import moe.rafal.monarch.datasource.PooledDatasource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.UUID;

class UserRepositorySql implements UserRepository {

    private static final String USER_SCHEMA_CREATE_QUERY =
        """
        CREATE TABLE IF NOT EXISTS
            `monarch_users`
        (
            `uuid` VARCHAR(36) PRIMARY KEY,
            `language_id` INT(11) REFERENCES `monarch_languages`(`uuid`)
        );
        """;
    private static final String USER_FIND_BY_UUID_QUERY =
        """
        SELECT
            `uuid`, `language_id`
        FROM
            `monarch_users`
        WHERE
            `uuid` = ?;
        """;
    private static final String USER_SAVE_QUERY =
        """
        INSERT INTO `monarch_users`
            (`uuid`, `language_id`)
        VALUES
            (?, ?)
        ON DUPLICATE KEY UPDATE
            `language_id` = ?;
        """;
    private final PooledDatasource datasource;

    public UserRepositorySql(PooledDatasource datasource) {
        this.datasource = datasource;
    }

    @Override
    public void createSchema() {
        try (Connection connection = datasource.borrowConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(USER_SCHEMA_CREATE_QUERY);
        } catch (SQLException exception) {
            throw new IllegalStateException("Could not create schema for users.");
        }
    }

    @Override
    public User findUserByUuid(UUID uuid) throws UserFindException {
        try (Connection connection = datasource.borrowConnection(); PreparedStatement statement = connection.prepareStatement(USER_FIND_BY_UUID_QUERY)) {
            statement.setString(1, uuid.toString());

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return new User(UUID.fromString(
                    resultSet.getString("uuid")),
                    resultSet.getInt("language_id"));
            }

            return null;
        } catch (SQLException exception) {
            throw new UserFindException("Could not find user by id, because of occurrence of unexpected exception.", exception);
        }
    }

    @Override
    public void saveUser(User user) throws UserSaveException {
        try (Connection connection = datasource.borrowConnection(); PreparedStatement statement = connection.prepareStatement(USER_SAVE_QUERY)) {
            statement.setString(1, user.getUuid().toString());
            statement.setInt(2, user.getLanguageId());
            statement.setInt(3, user.getLanguageId());
            statement.executeUpdate();
        } catch (SQLException exception) {
            throw new UserSaveException("Could not save user, because of occurrence of unexpected exception.", exception);
        }
    }
}
