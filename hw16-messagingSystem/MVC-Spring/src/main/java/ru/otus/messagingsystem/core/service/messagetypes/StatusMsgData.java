package ru.otus.messagingsystem.core.service.messagetypes;

import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagingsystem.core.model.User;

public class StatusMsgData extends ResultDataType {

    private final String status;
    private final User user;

    public StatusMsgData(String status,User user){
        this.status=status;
        this.user=user;
    }

    public String getStatus(){return status;}
    public User getUser(){return user;}

}
