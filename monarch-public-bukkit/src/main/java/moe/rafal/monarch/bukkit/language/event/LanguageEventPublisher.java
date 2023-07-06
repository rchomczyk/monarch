package moe.rafal.monarch.bukkit.language.event;

public interface LanguageEventPublisher {

    <T extends LanguageEvent> T publishAndForget(T event);
}
