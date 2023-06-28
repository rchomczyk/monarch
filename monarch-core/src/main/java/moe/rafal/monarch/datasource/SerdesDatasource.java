package moe.rafal.monarch.datasource;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import org.jetbrains.annotations.NotNull;

public class SerdesDatasource implements OkaeriSerdesPack {

    @Override
    public void register(@NotNull SerdesRegistry registry) {
        registry.register(new DatasourceSpecificationSerializer());
    }
}
