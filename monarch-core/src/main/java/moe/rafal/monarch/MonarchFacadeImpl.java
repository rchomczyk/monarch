package moe.rafal.monarch;

import moe.rafal.monarch.language.Language;
import moe.rafal.monarch.language.index.LanguageIndex;
import moe.rafal.monarch.user.User;
import moe.rafal.monarch.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class MonarchFacadeImpl implements MonarchFacade {

    private final LanguageIndex languageIndex;
    private final UserService userService;

    public MonarchFacadeImpl(LanguageIndex languageIndex, UserService userService) {
        this.languageIndex = languageIndex;
        this.userService = userService;
    }

    @Override
    public CompletableFuture<@Nullable Locale> getLocale(@NotNull UUID uniqueId) {
        return userService.findUserByUuid(uniqueId)
            .thenApply(User::getLanguageId)
            .thenApply(languageIndex::getByKey)
            .thenApply(Language::tag)
            .thenApply(Locale::forLanguageTag);
    }
}
