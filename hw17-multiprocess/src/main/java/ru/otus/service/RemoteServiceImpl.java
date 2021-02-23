package ru.otus.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.CountRequest;
import ru.otus.protobuf.generated.CountResponse;
import ru.otus.protobuf.generated.CountServiceGrpc;

public class RemoteServiceImpl extends CountServiceGrpc.CountServiceImplBase {


    @Override
    public void startCountStream(CountRequest request, StreamObserver<CountResponse> responseStreamObserver){

        for (int i = request.getFirstValue(); i < request.getLastValue(); i++) {
            CountResponse response = CountResponse.newBuilder().setDataResp(i).build();
            responseStreamObserver.onNext(response);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        responseStreamObserver.onCompleted();

    }
}
