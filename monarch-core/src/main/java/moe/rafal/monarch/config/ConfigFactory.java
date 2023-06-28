package moe.rafal.monarch.config;

import eu.okaeri.configs.ConfigManager;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.configurer.Configurer;
import eu.okaeri.configs.serdes.OkaeriSerdesPack;

import java.nio.file.Path;
import java.util.function.Supplier;

public class ConfigFactory {

    private final Path dataPath;
    private final Supplier<Configurer> configurerSupplier;

    public ConfigFactory(Path dataPath, Supplier<Configurer> configurerSupplier) {
        this.dataPath = dataPath;
        this.configurerSupplier = configurerSupplier;
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> configClass, String fileName, OkaeriSerdesPack... serdesPacks) {
        return produceConfig(configClass, dataPath.resolve(fileName), serdesPacks);
    }

    public <T extends OkaeriConfig> T produceConfig(Class<T> configClass, Path filePath, OkaeriSerdesPack... serdesPacks) {
        return ConfigManager.create(configClass, initializer -> initializer
            .withConfigurer(configurerSupplier.get(), serdesPacks)
            .withBindFile(filePath)
            .saveDefaults()
            .load(true));
    }
}
