package ru.otus.messagingsystem.front;

import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;
import ru.otus.messagingsystem.core.model.User;
import ru.otus.messagingsystem.core.service.messagetypes.StatusMsgData;
import ru.otus.messagingsystem.core.service.messagetypes.UserListMgrData;
import ru.otus.messagingsystem.core.service.messagetypes.UserMsgData;


public class FrontendServiceImpl implements FrontendService {

    private final MsClient msClient;
    private final String databaseServiceClientName;

    public FrontendServiceImpl(MsClient msClient, String databaseServiceClientName) {
        this.msClient = msClient;
        this.databaseServiceClientName = databaseServiceClientName;
    }

    @Override
    public void getUserData(long userId, MessageCallback<User> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, new User(userId),
                MessageType.USER_DATA, dataConsumer);
        msClient.sendMessage(outMsg);
    }

 @Override
    public void saveUser(User user, MessageCallback<StatusMsgData> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, new StatusMsgData("SUCCESS",user),
                MessageType.SAVE_USER, dataConsumer);
        msClient.sendMessage(outMsg);
    }

    @Override
    public void getAllUsers(MessageCallback<UserListMgrData> dataConsumer) {
        Message outMsg = msClient.produceMessage(databaseServiceClientName, new UserListMgrData(),
                MessageType.ALL_USERS, dataConsumer);
        msClient.sendMessage(outMsg);
    }

}
