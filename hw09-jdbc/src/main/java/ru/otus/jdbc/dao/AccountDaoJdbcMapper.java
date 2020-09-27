package ru.otus.jdbc.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.dao.AccountDao;
import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.mapper.JdbcMapper;

import java.sql.SQLException;
import java.util.Optional;

public class AccountDaoJdbcMapper implements AccountDao {
    private static final Logger logger = LoggerFactory.getLogger(AccountDaoJdbcMapper.class);

    private final JdbcMapper<Account> jdbcMapper;


    public AccountDaoJdbcMapper(JdbcMapper<Account> jdbcMapper) {

        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<Account> findByNo(long no) throws Exception {

        return jdbcMapper.findById(no);
    }

    @Override
    public long insertAccount(Account account) throws SQLException, IllegalAccessException {
        return jdbcMapper.insert(account);
    }

    @Override
    public SessionManager getSessionManager() {
        return jdbcMapper.getSessionManager();
    }

}
