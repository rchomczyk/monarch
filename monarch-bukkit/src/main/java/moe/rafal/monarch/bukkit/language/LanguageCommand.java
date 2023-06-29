package moe.rafal.monarch.bukkit.language;

import dev.rollczi.litecommands.argument.Arg;
import dev.rollczi.litecommands.command.execute.Execute;
import dev.rollczi.litecommands.command.permission.Permission;
import dev.rollczi.litecommands.command.route.Route;
import moe.rafal.linguist.integration.litecommands.LiteTranslatableMessage;
import moe.rafal.linguist.placeholder.Placeholder;
import moe.rafal.monarch.language.Language;
import moe.rafal.monarch.language.index.LanguageIndex;
import moe.rafal.monarch.user.User;
import moe.rafal.monarch.user.UserService;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

import static moe.rafal.monarch.bukkit.message.BukkitMessageFactory.translation;

@Permission("monarch.command.language")
@Route(name = "language", aliases = "lang")
public class LanguageCommand {

    private final LanguageIndex languageIndex;
    private final UserService userService;

    public LanguageCommand(LanguageIndex languageIndex, UserService userService) {
        this.languageIndex = languageIndex;
        this.userService = userService;
    }

    @Execute
    public CompletableFuture<LiteTranslatableMessage> getLanguage(Player executor) {
        return userService.findUserByUuid(executor.getUniqueId()).thenCompose(this::getLanguage);
    }

    private CompletableFuture<LiteTranslatableMessage> getLanguage(User user) {
        return userService.findUserByUuid(user.getUuid())
            .thenApply(User::getLanguageId)
            .thenApply(languageIndex::getByKey)
            .thenApply(Language::tag)
            .thenApply(languageTag -> translation("command.language.get.success", new Placeholder("language_tag", languageTag)))
            .exceptionally(exception -> {
                exception.printStackTrace();
                return translation("command.language.get.failure");
            });
    }

    @Execute
    public CompletableFuture<LiteTranslatableMessage> setLanguage(Player executor, @Arg Language language) {
        return userService.findUserByUuid(executor.getUniqueId()).thenCompose(user -> setLanguage(user, language));
    }

    private CompletableFuture<LiteTranslatableMessage> setLanguage(User user, Language language) {
        user.setLanguageId(language.id());
        return userService.saveUser(user)
            .thenApply(__ -> translation("command.language.set.success", new Placeholder("language_tag", language.tag())))
            .exceptionally(exception -> {
                exception.printStackTrace();
                return translation("command.language.set.failure");
            });
    }
}
