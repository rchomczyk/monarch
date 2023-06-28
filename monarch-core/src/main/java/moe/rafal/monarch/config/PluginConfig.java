package moe.rafal.monarch.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import moe.rafal.monarch.datasource.DatasourceSpecification;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
public class PluginConfig extends OkaeriConfig {

    public DatasourceSpecification datasource = new DatasourceSpecification(
        "jdbc:mysql://127.0.0.1:3306/auroramc_dev",
        "shitzuu",
        "my-secret-password");
}
