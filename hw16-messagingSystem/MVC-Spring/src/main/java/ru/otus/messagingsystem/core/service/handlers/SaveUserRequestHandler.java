package ru.otus.messagingsystem.core.service.handlers;

import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;
import ru.otus.messagingsystem.core.model.User;
import ru.otus.messagingsystem.core.service.DBServiceUser;
import ru.otus.messagingsystem.core.service.messagetypes.StatusMsgData;

import java.util.Optional;

public class SaveUserRequestHandler implements RequestHandler<User> {

    public static final String SUCCESS = "SUCCESS";
    public static final String ERROR = "ERROR";
    private final DBServiceUser dbServiceUser;

    public SaveUserRequestHandler(DBServiceUser dbServiceUser){this.dbServiceUser=dbServiceUser;}


    @Override
    public Optional<Message> handle(Message msg) {
        StatusMsgData statusMsgData = MessageHelper.getPayload(msg);
        User user = statusMsgData.getUser();
        if (user!=null){
            dbServiceUser.saveUser(user);
            statusMsgData = new StatusMsgData(SUCCESS,user);
        }else {
            statusMsgData = new StatusMsgData(ERROR,new User());
        }
        return Optional.of(MessageBuilder.buildReplyMessage(msg, statusMsgData));
    }
}
