package ru.otus.messagingsystem.core.service.messagetypes;

import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagingsystem.core.model.User;

public class UserMsgData extends ResultDataType {

    private final User user;

    public UserMsgData(User user) {
        this.user = user;
    }

    public User getData() {
        return user;
    }

    @Override
    public String toString() {
        return "UserMsgData{" +
                "user =" + user +
                '}';
    }
}

