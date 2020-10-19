package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;

public class MyCache<K, V> implements HwCache<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyCache.class);

    private final WeakHashMap<K, V> cache = new WeakHashMap<>();
    private final List<WeakReference<HwListener<K, V>>> listeners = new ArrayList<>();

    public MyCache() {
    }

    public void put(K key, V value) {
        this.cache.put(key, value);
        this.notyfyListeners(key, value, "put");
    }

    public void remove(K key) {
        if (this.cache.containsKey(key)) {
            this.cache.remove(key);
            this.notyfyListeners(key, this.get(key), "remove");
        }
    }

    public V get(K key) {
        V value = this.cache.get(key);
        this.notyfyListeners(key, value, "get");
        return value;
    }

    public int getCacheSize() {
        return this.cache.size();
    }

    public void addListener(HwListener<K, V> listener) {
        this.listeners.add(new WeakReference<>(listener));
    }

    public void removeListener(HwListener<K, V> listener) {
        this.listeners.remove(listener);
    }

    private void notyfyListeners(K key, V value, String action) {
        this.listeners.forEach((listeners) -> {
            try {
                (listeners.get()).notify(key, value, action);
            }catch (Exception e){
                logger.error(e.getMessage(), e);
            }
        });
    }
}
