package ru.otus.messagingsystem.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.MessageSystemImpl;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.messagingsystem.core.service.DBServiceUser;
import ru.otus.messagingsystem.core.service.DbServiceUserImpl;
import ru.otus.messagingsystem.core.service.handlers.GetAllUsersRequestHandler;
import ru.otus.messagingsystem.core.service.handlers.GetUserDataRequestHandler;
import ru.otus.messagingsystem.core.service.handlers.SaveUserRequestHandler;
import ru.otus.messagingsystem.front.FrontendService;
import ru.otus.messagingsystem.front.FrontendServiceImpl;
import ru.otus.messagingsystem.front.handlers.GetUserDataResponseHandler;

@Configuration
public class MessageSystemConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(MessageSystemConfiguration.class);


    public static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";
    public static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    @Bean
    public MessageSystem getMessageSystem() {
        return new MessageSystemImpl();
    }

    @Bean
    public void dispose() {
    }

    @Bean
    public CallbackRegistry getCallBackRegistry() {
        return new CallbackRegistryImpl();
    }

    @Bean("databaseMsClient")
    public MsClient getDatabaseMsClient(
            MessageSystem messageSystem,
            CallbackRegistry callbackRegistry,
            @Autowired DBServiceUser dbServiceUser
    ) {
        logger.info("databaseMsClient run, dnSeviceUser:{}", dbServiceUser);
        HandlersStore requestHandlerDatabaseStore = new HandlersStoreImpl();
        requestHandlerDatabaseStore.addHandler(MessageType.USER_DATA, new GetUserDataRequestHandler(dbServiceUser));
        requestHandlerDatabaseStore.addHandler(MessageType.SAVE_USER, new SaveUserRequestHandler(dbServiceUser));
        requestHandlerDatabaseStore.addHandler(MessageType.ALL_USERS, new GetAllUsersRequestHandler(dbServiceUser));
        MsClient databaseMsClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerDatabaseStore, callbackRegistry);
        messageSystem.addClient(databaseMsClient);

        return databaseMsClient;

    }

    @Bean("frontendMsClient")
    public MsClient getFrontendMsClient(
            MessageSystem messageSystem,
            CallbackRegistry callbackRegistry
    ) {

        logger.info("frontendMsClient, MS:{}", messageSystem);

        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(callbackRegistry));

        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME,
                messageSystem, requestHandlerFrontendStore, callbackRegistry);
        messageSystem.addClient(frontendMsClient);

        return frontendMsClient;
    }

    @Bean
    FrontendService getFrontendService(@Qualifier("frontendMsClient") MsClient msClient) {
        return new FrontendServiceImpl(msClient, DATABASE_SERVICE_CLIENT_NAME);
    }

}
