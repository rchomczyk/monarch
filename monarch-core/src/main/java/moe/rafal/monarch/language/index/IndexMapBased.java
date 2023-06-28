package moe.rafal.monarch.language.index;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

abstract class IndexMapBased<K1, K2, V> implements Index<K1, K2, V> {

    private final Map<K1, V> valuesByKey;
    private final Map<K2, V> valuesByVal;

    IndexMapBased() {
        this.valuesByKey = new ConcurrentHashMap<>();
        this.valuesByVal = new ConcurrentHashMap<>();
    }

    @Override
    public V getByKey(K1 key) {
        return valuesByKey.get(key);
    }

    @Override
    public V getByVal(K2 val) {
        return valuesByVal.get(val);
    }

    public Map<K1, V> getValuesByKey() {
        return valuesByKey;
    }

    public Map<K2, V> getValuesByVal() {
        return valuesByVal;
    }
}
