package ru.otus.messagingsystem.core.service.handlers;

import ru.otus.messagingsystem.core.dao.UserDao;
import ru.otus.messagingsystem.core.service.DBServiceUser;
import ru.otus.messagingsystem.core.model.User;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.messagingsystem.core.service.messagetypes.UserMsgData;

import java.util.Optional;
import java.util.function.Consumer;


public class GetUserDataRequestHandler implements RequestHandler<User> {

    private final DBServiceUser dbServiceUser;

    public GetUserDataRequestHandler(DBServiceUser dbServiceUser) {
        this.dbServiceUser=dbServiceUser;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        User user = MessageHelper.getPayload(msg);
        User data = new User();
             Optional<User> user1 = dbServiceUser.getUser(user.getId());
             if (user1.isPresent()) {data = user1.get();}
             return Optional.of(MessageBuilder.buildReplyMessage(msg, data));
    }
}
