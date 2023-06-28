package moe.rafal.monarch;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface MonarchFacade {

    CompletableFuture<@Nullable Locale> getLocale(@NotNull UUID uniqueId);
}
