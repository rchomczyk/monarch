package moe.rafal.monarch.language;

import moe.rafal.monarch.language.index.LanguageIndex;

import java.util.Collection;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.function.Predicate.not;

public class LanguageService {

    private final LanguageRepository languageRepository;
    private final LanguageIndex languageIndex;

    public LanguageService(LanguageRepository languageRepository, LanguageIndex languageIndex) {
        this.languageRepository = languageRepository;
        this.languageIndex = languageIndex;
    }

    public void indexLanguages(Collection<Locale> locales) {
        Set<Language> remoteBunchOfLanguages = languageRepository.findLanguages();

        Set<String> actualLocaleTags = locales.stream().map(this::getIEFTLocaleTag).collect(Collectors.toSet());
        Set<String> remoteLocaleTags = remoteBunchOfLanguages.stream().map(Language::tag).collect(Collectors.toSet());

        Set<Language> actualBunchOfLanguages = actualLocaleTags.stream()
            .filter(Objects::nonNull)
            .filter(not(remoteLocaleTags::contains))
            .map(languageRepository::saveLanguage)
            .collect(Collectors.toSet());

        Stream.concat(remoteBunchOfLanguages.stream(), actualBunchOfLanguages.stream())
            .distinct()
            .forEach(languageIndex::index);
    }

    private String getIEFTLocaleTag(Locale locale) {
        return locale.toLanguageTag().replace("_", "-");
    }
}
