package moe.rafal.monarch.language.index;

import moe.rafal.monarch.language.Language;

public class LanguageIndex extends IndexMapBased<Integer, String, Language> {

    @Override
    public void index(Language value) {
        getValuesByKey().put(value.id(), value);
        getValuesByVal().put(value.tag(), value);
    }
}
