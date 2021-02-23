package ru.otus.server;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.service.RemoteServiceImpl;

import java.io.IOException;

public class GrpcServer {

    private static final Logger logger = LoggerFactory.getLogger(GrpcServer.class);

    public static void main(String[] args) throws IOException, InterruptedException {


        RemoteServiceImpl remoteService = new RemoteServiceImpl();

        Server server = ServerBuilder.forPort(8080).addService(remoteService).build();

        server.start();
        logger.info("server waiting for client connections...");
        server.awaitTermination();
    }
}
