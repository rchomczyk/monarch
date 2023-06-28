package moe.rafal.monarch.language;

import java.util.Set;

public interface LanguageRepository {

    void createSchema();

    Set<Language> findLanguages() throws LanguageFindException;

    Language saveLanguage(String languageTag) throws LanguageSaveException;
}
