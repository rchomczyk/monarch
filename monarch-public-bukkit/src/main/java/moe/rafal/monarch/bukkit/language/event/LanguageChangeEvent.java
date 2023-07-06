package moe.rafal.monarch.bukkit.language.event;

import moe.rafal.monarch.language.Language;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class LanguageChangeEvent extends LanguageEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();
    private final UUID uuid;
    private final Language from;
    private final Language into;

    public LanguageChangeEvent(UUID uuid, Language from, Language into) {
        this.uuid = uuid;
        this.from = from;
        this.into = into;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Language getFrom() {
        return from;
    }

    public Language getInto() {
        return into;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
