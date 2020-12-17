package ru.otus.messagingsystem.core.dao;

import ru.otus.messagingsystem.core.model.User;
import ru.otus.messagingsystem.core.sessionmanager.SessionManager;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    Optional<User> findById(long id);
    Optional<User> findRandomUser();
    Optional<User> findByLogin(String login);
    List<User> getAllUsers();

    long insertUser(User user);

    void updateUser(User user);
    void insertOrUpdate(User user);


    SessionManager getSessionManager();
}
