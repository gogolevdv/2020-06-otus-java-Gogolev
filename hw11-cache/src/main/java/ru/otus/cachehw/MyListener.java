package ru.otus.cachehw;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyListener<K, V> implements HwListener<K, V> {
    private static final Logger logger = LoggerFactory.getLogger(MyListener.class);

    public MyListener() {
    }

    @Override
    public void notify(Object key, Object value, String action) {
        logger.info("key:{}, value:{}, action: {}", key, value, action);
    }
}
