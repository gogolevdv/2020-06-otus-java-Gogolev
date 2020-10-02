package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.UserDao;
import ru.otus.core.model.User;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.mapper.JdbcMapper;

import java.sql.SQLException;
import java.util.Optional;

public class UserDaoJdbcMapper implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoJdbcMapper.class);

    private final JdbcMapper<User> jdbcMapper;


    public UserDaoJdbcMapper(JdbcMapper<User> jdbcMapper) {

        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<User> findById(long id) throws Exception {

        return jdbcMapper.findById(id);
    }

    @Override
    public long insertUser(User user) throws SQLException, IllegalAccessException {
        return jdbcMapper.insert(user);
    }

    @Override
    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }

}
