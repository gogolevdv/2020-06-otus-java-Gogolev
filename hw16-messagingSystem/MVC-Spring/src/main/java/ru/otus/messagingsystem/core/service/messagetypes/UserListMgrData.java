package ru.otus.messagingsystem.core.service.messagetypes;

import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagingsystem.core.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserListMgrData extends ResultDataType {

    private final List<User> userList;

    public UserListMgrData(List<User> userList) {
        this.userList = userList;
    }

    public UserListMgrData() {
        this.userList = new ArrayList<>();
    }

    public List<User> getUserList() {
        return userList;
    }

    @Override
    public String toString() {
        return "UserListMgrData{" +
                "userList=" + userList +
                '}';
    }
}
