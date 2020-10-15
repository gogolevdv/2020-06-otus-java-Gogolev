package ru.otus.cachehw;

public interface HwListener<K, V> {
    void notify(K var1, V var2, String var3);
}
