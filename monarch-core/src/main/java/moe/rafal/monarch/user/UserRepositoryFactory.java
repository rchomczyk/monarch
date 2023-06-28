package moe.rafal.monarch.user;

import moe.rafal.monarch.datasource.PooledDatasource;

public class UserRepositoryFactory {

    private UserRepositoryFactory() {

    }

    public static UserRepository createUserRepository(PooledDatasource datasource) {
        return new UserRepositorySql(datasource);
    }
}
