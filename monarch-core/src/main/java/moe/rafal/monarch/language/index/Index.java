package moe.rafal.monarch.language.index;

interface Index<K1, K2, V> {

    void index(V value);

    V getByKey(K1 key);

    V getByVal(K2 val);
}
