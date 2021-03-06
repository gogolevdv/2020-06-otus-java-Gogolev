package ru.otus.messagingsystem.core.service;

import ru.otus.messagingsystem.core.model.User;

import java.util.List;
import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);
    String getUserData(long id);
    Optional<User> getRandomUser();
    List<User> getAllUsers();
}
