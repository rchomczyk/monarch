package moe.rafal.monarch.bukkit.message;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import moe.rafal.linguist.LinguistBukkit;
import moe.rafal.linguist.definition.TranslationDefinition;
import moe.rafal.linguist.integration.litecommands.LiteTranslatableMessage;
import moe.rafal.monarch.language.index.LanguageIndex;
import moe.rafal.monarch.user.User;
import moe.rafal.monarch.user.UserService;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Locale;
import java.util.concurrent.CompletableFuture;

public class BukkitMessageHandler implements Handler<CommandSender, LiteTranslatableMessage> {

    private static final Locale DEFAULT_LOCALE = Locale.forLanguageTag("en-US");
    private final LinguistBukkit linguist;
    private final LanguageIndex languageIndex;
    private final UserService userService;

    public BukkitMessageHandler(LinguistBukkit linguist, LanguageIndex languageIndex, UserService userService) {
        this.linguist = linguist;
        this.languageIndex = languageIndex;
        this.userService = userService;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, LiteTranslatableMessage message) {
        CompletableFuture
            .supplyAsync(() -> linguist.translate(new TranslationDefinition(getLocale(sender),
                message.getMessageKey(),
                message.getPlaceholders())))
            .thenAccept(sender::sendMessage);
    }

    private Locale getLocale(CommandSender sender) {
        return sender instanceof Player player
            ? getLocaleFromUser(userService.findUserByUuid(player.getUniqueId()).join())
            : DEFAULT_LOCALE;
    }

    private Locale getLocaleFromUser(User user) {
        return Locale.forLanguageTag(languageIndex.getByKey(user.getLanguageId()).tag());
    }
}
