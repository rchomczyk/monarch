package moe.rafal.monarch;

import eu.okaeri.configs.serdes.OkaeriSerdesPack;
import eu.okaeri.configs.serdes.SerdesRegistry;
import org.jetbrains.annotations.NotNull;

public class SerdesMonarch implements OkaeriSerdesPack {

    @Override
    public void register(@NotNull SerdesRegistry registry) {
        registry.register(new StringMessageKeyTransformer());
    }
}
