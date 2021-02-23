package ru.otus.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.CountRequest;
import ru.otus.protobuf.generated.CountResponse;
import ru.otus.protobuf.generated.CountServiceGrpc;

import java.util.concurrent.CountDownLatch;

public class ClientStreamObserver {
    private static final Logger logger = LoggerFactory.getLogger(ClientStreamObserver.class);

    public static void go() throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        CountDownLatch latch = new CountDownLatch(1);

        CountServiceGrpc.CountServiceStub stub = CountServiceGrpc.newStub(channel);

        stub.startCountStream(CountRequest.newBuilder().setFirstValue(0).setLastValue(30).build(), new StreamObserver<>() {
            @Override
            public void onNext(CountResponse value) {

                int currId = value.getDataResp();
                logger.info(String.format("new Value:%d", currId));
                // Помещаем значение полученное от сервера в Map
                // в value записываем 0, как признак, что это значение еще не учитывалось
                GrpcClient.shareList.put(currId, 0);
            }

            @Override
            public void onError(Throwable t) {
                System.err.println(t);
            }

            @Override
            public void onCompleted() {
                logger.info("request completed");
                latch.countDown();
            }
        });

        new Thread(NumbersClient::count,"main").start();

        latch.await();

        channel.shutdown();


    }



}
