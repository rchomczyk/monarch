package moe.rafal.monarch;

import eu.okaeri.configs.schema.GenericsPair;
import eu.okaeri.configs.serdes.BidirectionalTransformer;
import eu.okaeri.configs.serdes.SerdesContext;
import moe.rafal.linguist.key.MessageKey;
import org.jetbrains.annotations.NotNull;

public class StringMessageKeyTransformer extends BidirectionalTransformer<String, MessageKey> {

    @Override
    public GenericsPair<String, MessageKey> getPair() {
        return genericsPair(String.class, MessageKey.class);
    }

    @Override
    public MessageKey leftToRight(@NotNull String data, @NotNull SerdesContext serdesContext) {
        return new MessageKey(data);
    }

    @Override
    public String rightToLeft(@NotNull MessageKey data, @NotNull SerdesContext serdesContext) {
        return data.getKey();
    }
}
