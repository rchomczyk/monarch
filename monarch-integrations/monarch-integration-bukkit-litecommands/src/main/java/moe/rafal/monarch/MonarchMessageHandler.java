package moe.rafal.monarch;

import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.handle.Handler;
import moe.rafal.linguist.integration.litecommands.LiteTranslatableMessage;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class MonarchMessageHandler implements Handler<CommandSender, LiteTranslatableMessage> {

    private final MonarchMessageService messageService;

    public MonarchMessageHandler(MonarchMessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void handle(CommandSender sender, LiteInvocation invocation, LiteTranslatableMessage specification) {
        getTargetedMessage(specification, sender).thenAccept(sender::sendMessage);
    }

    private CompletableFuture<Component> getTargetedMessage(LiteTranslatableMessage specification, CommandSender viewer) {
        if (viewer instanceof Player player) {
            return messageService.getMessage(specification, player.getUniqueId());
        }

        return messageService.getMessage(specification);
    }
}
