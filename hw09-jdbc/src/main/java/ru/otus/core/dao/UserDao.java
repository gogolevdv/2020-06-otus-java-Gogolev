package ru.otus.core.dao;

import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;

import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id) throws Exception;

    long insertUser(User user) throws SQLException, IllegalAccessException;

//    void updateUser(User user);
//    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
