package moe.rafal.monarch.bukkit.message;

import moe.rafal.linguist.integration.litecommands.LiteTranslatableMessage;
import moe.rafal.linguist.key.MessageKey;
import moe.rafal.linguist.placeholder.Placeholder;

import java.util.Arrays;

public class BukkitMessageFactory {

    private BukkitMessageFactory() {

    }

    public static LiteTranslatableMessage translation(String key, Placeholder... placeholders) {
        return new LiteTranslatableMessage(new MessageKey(key), Arrays.asList(placeholders));
    }
}
