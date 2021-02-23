package ru.otus.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GrpcClient {
    private static final Logger logger = LoggerFactory.getLogger(GrpcClient.class);

    // Map для обмена между сервером и клиентом
    public static final Map<Object, Object> shareList = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {

        ClientStreamObserver.go();

    }
}
