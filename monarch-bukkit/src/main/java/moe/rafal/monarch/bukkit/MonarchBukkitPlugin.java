package moe.rafal.monarch.bukkit;

import dev.rollczi.litecommands.bukkit.adventure.paper.LitePaperAdventureFactory;
import dev.rollczi.litecommands.bukkit.tools.BukkitOnlyPlayerContextual;
import dev.rollczi.litecommands.command.permission.RequiredPermissions;
import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import moe.rafal.linguist.LinguistBukkit;
import moe.rafal.linguist.integration.litecommands.LiteTranslatableMessage;
import moe.rafal.linguist.placeholder.Placeholder;
import moe.rafal.monarch.MonarchFacade;
import moe.rafal.monarch.MonarchFacadeImpl;
import moe.rafal.monarch.bukkit.language.event.BukkitLanguageEventPublisherFactory;
import moe.rafal.monarch.bukkit.language.event.LanguageEventPublisher;
import moe.rafal.monarch.bukkit.message.BukkitMessageHandler;
import moe.rafal.monarch.config.ConfigFactory;
import moe.rafal.monarch.config.PluginConfig;
import moe.rafal.monarch.datasource.PooledDatasource;
import moe.rafal.monarch.datasource.PooledDatasourceFactory;
import moe.rafal.monarch.datasource.SerdesDatasource;
import moe.rafal.monarch.language.Language;
import moe.rafal.monarch.bukkit.language.LanguageArgument;
import moe.rafal.monarch.bukkit.language.LanguageCommand;
import moe.rafal.monarch.language.LanguageRepository;
import moe.rafal.monarch.language.LanguageRepositoryFactory;
import moe.rafal.monarch.language.LanguageService;
import moe.rafal.monarch.language.index.LanguageIndex;
import moe.rafal.monarch.bukkit.user.UserListener;
import moe.rafal.monarch.user.UserRepository;
import moe.rafal.monarch.user.UserRepositoryFactory;
import moe.rafal.monarch.user.UserService;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.plugin.java.JavaPlugin;

import static moe.rafal.monarch.bukkit.message.BukkitMessageFactory.translation;

public class MonarchBukkitPlugin extends JavaPlugin {

    private static final String PLUGIN_CONFIG_FILE_NAME = "config.yml";
    private static final String TRANSLATIONS_DIRECTORY_NAME = "translations";
    private PooledDatasource datasource;

    @Override
    public void onEnable() {
        ConfigFactory configFactory = new ConfigFactory(getDataFolder().toPath(), YamlBukkitConfigurer::new);

        PluginConfig pluginConfig = configFactory.produceConfig(PluginConfig.class, PLUGIN_CONFIG_FILE_NAME, new SerdesDatasource());

        this.datasource = PooledDatasourceFactory.createDatasource(pluginConfig.datasource);

        LinguistBukkit linguist = LinguistBukkit.Companion.setup(this, getDataFolder().toPath(), TRANSLATIONS_DIRECTORY_NAME);

        LanguageIndex languageIndex = new LanguageIndex();
        LanguageRepository languageRepository = LanguageRepositoryFactory.createLanguageRepository(datasource);
        languageRepository.createSchema();
        LanguageService languageService = new LanguageService(languageRepository, languageIndex);
        languageService.indexLanguages(linguist.supportedLocales());
        LanguageEventPublisher languageEventPublisher = BukkitLanguageEventPublisherFactory.createLanguageEventPublisher(this);

        UserRepository userRepository = UserRepositoryFactory.createUserRepository(datasource);
        userRepository.createSchema();
        UserService userService = new UserService(userRepository);

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new UserListener(userService), this);

        ServicesManager servicesManager = getServer().getServicesManager();
        servicesManager.register(MonarchFacade.class, new MonarchFacadeImpl(languageIndex, userService), this, ServicePriority.Normal);

        LitePaperAdventureFactory.builder(getServer(), getName())
            .argument(Language.class, new LanguageArgument<>(languageIndex,
                translation("command.language.index.miss")))
            .contextualBind(Player.class, new BukkitOnlyPlayerContextual<>(
                translation("command.source.nosupport")))
            .redirectResult(RequiredPermissions.class, LiteTranslatableMessage.class, permissions ->
                translation("command.access.violation", new Placeholder("permissions", String.join(", ", permissions.getPermissions()))))
            .resultHandler (LiteTranslatableMessage.class, new BukkitMessageHandler(linguist, languageIndex, userService))
            .commandInstance(new LanguageCommand(languageIndex, languageEventPublisher, userService))
            .register();
    }

    @Override
    public void onDisable() {
        this.datasource.ditchConnections();
    }
}
