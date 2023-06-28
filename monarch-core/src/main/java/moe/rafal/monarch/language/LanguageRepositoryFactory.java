package moe.rafal.monarch.language;

import moe.rafal.monarch.datasource.PooledDatasource;

public class LanguageRepositoryFactory {

    private LanguageRepositoryFactory() {

    }

    public static LanguageRepository createLanguageRepository(PooledDatasource datasource) {
        return new LanguageRepositorySql(datasource);
    }
}
