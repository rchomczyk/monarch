package moe.rafal.monarch.datasource;

import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.jetbrains.annotations.NotNull;

class DatasourceSpecificationSerializer implements ObjectSerializer<DatasourceSpecification> {

    @Override
    public boolean supports(@NotNull Class<? super DatasourceSpecification> type) {
        return DatasourceSpecification.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(@NotNull DatasourceSpecification object, @NotNull SerializationData data, @NotNull GenericsDeclaration generics) {
        data.add("jdbc-url", object.jdbcUrl());
        data.add("username", object.username());
        data.add("password", object.password());
    }

    @Override
    public DatasourceSpecification deserialize(@NotNull DeserializationData data, @NotNull GenericsDeclaration generics) {
        return new DatasourceSpecification(
            data.get("jdbc-url", String.class),
            data.get("username", String.class),
            data.get("password", String.class));
    }
}
