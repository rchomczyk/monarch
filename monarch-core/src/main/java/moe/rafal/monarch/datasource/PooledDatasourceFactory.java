package moe.rafal.monarch.datasource;

public class PooledDatasourceFactory {

    private PooledDatasourceFactory() {

    }

    public static PooledDatasource createDatasource(DatasourceSpecification specification) {
        return new PooledDatasourceHikari(specification);
    }
}
