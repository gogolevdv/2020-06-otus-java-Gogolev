package ru.otus.messagingsystem.front;

import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagingsystem.core.model.User;
import ru.otus.messagingsystem.core.service.messagetypes.StatusMsgData;
import ru.otus.messagingsystem.core.service.messagetypes.UserListMgrData;
import ru.otus.messagingsystem.core.service.messagetypes.UserMsgData;

public interface FrontendService {
    void getUserData(long userId, MessageCallback<User> dataConsumer);
    void saveUser(User user, MessageCallback<StatusMsgData> dataConsumer);
    void getAllUsers(MessageCallback<UserListMgrData> dataConsumer);
}

