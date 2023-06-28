package moe.rafal.monarch.datasource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

class PooledDatasourceHikari implements PooledDatasource {

    private final HikariDataSource underlyingDatasource;

    PooledDatasourceHikari(DatasourceSpecification specification) {
        this.underlyingDatasource = new HikariDataSource(getHikariConfig(specification));
    }

    @Override
    public Connection borrowConnection() throws SQLException {
        return underlyingDatasource.getConnection();
    }

    @Override
    public void ditchConnections() {
        underlyingDatasource.close();
    }

    private HikariConfig getHikariConfig(DatasourceSpecification specification) {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(specification.jdbcUrl());
        hikariConfig.setUsername(specification.username());
        hikariConfig.setPassword(specification.password());
        return hikariConfig;
    }
}
