package ru.otus.messagingsystem.core.service.handlers;

import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.messagingsystem.core.model.User;
import ru.otus.messagingsystem.core.service.DBServiceUser;
import ru.otus.messagingsystem.core.service.messagetypes.StatusMsgData;
import ru.otus.messagingsystem.core.service.messagetypes.UserListMgrData;

import java.util.Optional;

public class GetAllUsersRequestHandler implements RequestHandler<UserListMgrData> {

    private final DBServiceUser dbServiceUser;

    public GetAllUsersRequestHandler(DBServiceUser dbServiceUser){this.dbServiceUser=dbServiceUser;}


    @Override
    public Optional<Message> handle(Message msg) {
        UserListMgrData userListMgrData = new UserListMgrData(dbServiceUser.getAllUsers());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, userListMgrData));
    }
}
