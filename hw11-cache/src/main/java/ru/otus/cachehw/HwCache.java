package ru.otus.cachehw;

public interface HwCache<K, V> {
    void put(K var1, V var2);

    void remove(K var1);

    V get(K var1);

    int getCacheSize();

    void addListener(HwListener<K, V> var1);

    void removeListener(HwListener<K, V> var1);
}