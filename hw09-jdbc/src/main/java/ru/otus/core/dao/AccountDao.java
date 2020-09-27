package ru.otus.core.dao;

import ru.otus.core.model.Account;
import ru.otus.core.sessionmanager.SessionManager;

import java.sql.SQLException;
import java.util.Optional;

public interface AccountDao {
    Optional<Account> findByNo(long no) throws Exception;

    long insertAccount(Account account) throws SQLException, IllegalAccessException;

    SessionManager getSessionManager();
}
