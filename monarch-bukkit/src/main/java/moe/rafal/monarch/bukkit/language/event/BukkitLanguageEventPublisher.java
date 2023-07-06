package moe.rafal.monarch.bukkit.language.event;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

class BukkitLanguageEventPublisher implements LanguageEventPublisher {

    private final Plugin plugin;

    BukkitLanguageEventPublisher(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public <T extends LanguageEvent> T fireAndForget(T event) {
        runTaskSynchronously(() -> {
            PluginManager pluginManager = plugin.getServer().getPluginManager();
            pluginManager.callEvent(event);
        });
        return event;
    }

    private void runTaskSynchronously(Runnable task) {
        plugin.getServer().getScheduler().runTask(plugin, task);
    }
}
