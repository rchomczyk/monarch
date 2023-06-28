package moe.rafal.monarch.bukkit.language;

import dev.rollczi.litecommands.argument.ArgumentName;
import dev.rollczi.litecommands.argument.simple.OneArgument;
import dev.rollczi.litecommands.command.LiteInvocation;
import dev.rollczi.litecommands.suggestion.Suggestion;
import moe.rafal.monarch.language.Language;
import moe.rafal.monarch.language.index.LanguageIndex;
import panda.std.Option;
import panda.std.Result;

import java.util.List;
import java.util.Set;

@ArgumentName("language")
public class LanguageArgument<M> implements OneArgument<Language> {

    private final LanguageIndex languageIndex;
    private final List<Suggestion> languageSuggestions;
    private final M languageNotFoundMessage;

    public LanguageArgument(LanguageIndex languageIndex, M languageNotFoundMessage) {
        this.languageIndex = languageIndex;
        this.languageSuggestions = getLanguageSuggestions(languageIndex.getValuesByVal().keySet());
        this.languageNotFoundMessage = languageNotFoundMessage;
    }

    @Override
    public Result<Language, ?> parse(LiteInvocation invocation, String argument) {
        return Option.of(languageIndex.getByVal(argument))
            .toResult(languageNotFoundMessage);
    }

    @Override
    public List<Suggestion> suggest(LiteInvocation invocation) {
        return languageSuggestions;
    }

    private List<Suggestion> getLanguageSuggestions(Set<String> languageTags) {
        return languageTags.stream()
            .map(Suggestion::of)
            .toList();
    }
}
