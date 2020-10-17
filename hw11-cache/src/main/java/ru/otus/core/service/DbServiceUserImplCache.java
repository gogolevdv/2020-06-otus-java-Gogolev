package ru.otus.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.cachehw.HwCache;
import ru.otus.cachehw.HwListener;
import ru.otus.cachehw.MyListener;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;

import java.util.Optional;

public class DbServiceUserImplCache implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImplCache.class);

    private final UserDao userDao;

    private final HwCache<Long,User> myCache;

    public DbServiceUserImplCache(UserDao userDao, HwCache<Long,User> myCache) {
        this.userDao = userDao;
        this.myCache = myCache;
    }

    @Override
    public long saveUser(User user) {
        try (var sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.insertOrUpdate(user);
                Long userId = user.getId();
                myCache.put(userId, user);
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
                Optional<User> userOptional;

                User user = myCache.get(id);
                if (user == null) {
                    userOptional = userDao.findById(id);
                    logger.info("user: {}", userOptional.orElse(null));
                } else {
                    userOptional = Optional.of(user);
                }

                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
