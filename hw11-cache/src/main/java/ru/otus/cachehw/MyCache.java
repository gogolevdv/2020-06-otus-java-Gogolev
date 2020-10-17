package ru.otus.cachehw;

import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private HwListener<K, V> listener;

    public MyCache() {
    }

    public void put(K key, V value) {
        this.cache.put(key, value);
        listener.notify(key, value, "put");
    }

    public void remove(K key) {
        if (this.cache.containsKey(key)) {
            listener.notify(key, this.get(key), "remove");
            this.cache.remove(key);
        }
    }

    public V get(K key) {
        V value = this.cache.get(key);
        listener.notify(key, value, "get");
        return value;
    }

    public int getCacheSize() {
        return this.cache.size();
    }

    public void addListener(HwListener<K, V> listener) {
        this.listener = listener;
    }

    public void removeListener(HwListener<K, V> listener) {
        this.listener = null;
    }
}
