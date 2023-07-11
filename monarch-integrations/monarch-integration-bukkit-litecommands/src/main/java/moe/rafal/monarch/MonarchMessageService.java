package moe.rafal.monarch;

import moe.rafal.linguist.LinguistBukkit;
import moe.rafal.linguist.integration.litecommands.LiteTranslatableMessage;
import moe.rafal.linguist.placeholder.Placeholder;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.Blocking;

import java.util.Locale;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static moe.rafal.monarch.MonarchConstant.DEFAULT_LOCALE;

public class MonarchMessageService {

    private final MonarchFacade monarch;
    private final LinguistBukkit linguist;

    public MonarchMessageService(MonarchFacade monarch, LinguistBukkit linguist) {
        this.monarch = monarch;
        this.linguist = linguist;
    }

    @Blocking
    public Component getMessageSync(LiteTranslatableMessage specification) {
        return getMessage(specification).join();
    }

    @Blocking
    public Component getMessageSync(LiteTranslatableMessage specification, UUID viewerUniqueId) {
        return getMessage(specification, viewerUniqueId).join();
    }

    private Component getMessage(LiteTranslatableMessage specification, Locale target) {
        return linguist.translate(getTargetOrDefault(target),
            specification.getMessageKey(),
            specification.getPlaceholders().toArray(Placeholder[]::new));
    }

    public CompletableFuture<Component> getMessage(LiteTranslatableMessage specification) {
        return getMessage(specification, CompletableFuture.completedFuture(DEFAULT_LOCALE));
    }

    public CompletableFuture<Component> getMessage(LiteTranslatableMessage specification, UUID viewerUniqueId) {
        return getMessage(specification, monarch.getLocale(viewerUniqueId));
    }

    private CompletableFuture<Component> getMessage(LiteTranslatableMessage specification, CompletableFuture<Locale> targetResolver) {
        return targetResolver.thenApply(target -> getMessage(specification, target))
            .exceptionally(exception -> {
                exception.printStackTrace();
                return null;
            });
    }

    private Locale getTargetOrDefault(Locale target) {
        return Optional.ofNullable(target).orElse(DEFAULT_LOCALE);
    }
}
