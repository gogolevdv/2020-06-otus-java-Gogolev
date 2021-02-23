package ru.otus.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;

public class NumbersClient {
    private static final Logger logger = LoggerFactory.getLogger(NumbersClient.class);

        public static void count() {
        logger.info("Client start");
        int currentValue = 0;
        for (int i = 0; i < 50; i++) {
            Set<Map.Entry<Object, Object>> entrySet = GrpcClient.shareList.entrySet();
            Object key = 0;
            for (Map.Entry<Object, Object> pair : entrySet) {
                if (pair.getValue().equals(0)) {
                    key = pair.getKey();// нашли значение value 0 и берем ключ
                    // меняем значение value на 1, как признак, что значение уже учитывалось
                    GrpcClient.shareList.replace(key, 1);
                }
            }

            currentValue = currentValue + (int) key + 1;

            logger.info(String.format("currentValue:%d", currentValue));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
