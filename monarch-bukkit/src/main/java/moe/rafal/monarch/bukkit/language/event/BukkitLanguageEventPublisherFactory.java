package moe.rafal.monarch.bukkit.language.event;

import org.bukkit.plugin.Plugin;

public class BukkitLanguageEventPublisherFactory {

    private BukkitLanguageEventPublisherFactory() {

    }

    public static LanguageEventPublisher createLanguageEventPublisher(Plugin plugin) {
        return new BukkitLanguageEventPublisher(plugin);
    }
}
