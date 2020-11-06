package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.AddressDataSet;
import ru.otus.core.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);


    private final UserDao userDao;

    public DbServiceUserImpl(UserDao userDao) {

        this.userDao = userDao;
        User user_1 = new User(0, "Вася_1", 35, "user1", "user1");
        User user_2 = new User(0, "Вася_2", 36, "user2", "user2");
        saveUser(user_1);
        saveUser(user_2);
        AddressDataSet addr_u_1 = new AddressDataSet();
        addr_u_1.setStreet("qwe");

        user_1.setAddress(addr_u_1);
        addr_u_1.setUser(user_1);


//        saveUser(new User(0, "Вася_2", 36, "user2", "user2"));
//        saveUser(new User(0, "Вася_3", 37, "user3", "user3"));
//        saveUser(new User(0, "Вася_4", 38, "user4", "user4"));
//        saveUser(new User(0, "Вася_5", 39, "user5", "user5"));



    }

    @Override
    public long saveUser(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.insertOrUpdate(user);
                long userId = user.getId();
                sessionManager.commitSession();

                logger.info("created user: {}", userId);
                return userId;
            } catch (Exception e) {
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
    }

    @Override
    public Optional<User> getUser(long id) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);

                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public Optional<User> getRandomUser() {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findRandomUser();

                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    @Override
    public List<User> getAllUsers() {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {

                return userDao.getAllUsers();

            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return List.of();
        }
    }
}
