package ru.otus.jdbc.mapper;

import ru.otus.core.sessionmanager.SessionManager;

import java.sql.SQLException;
import java.util.Optional;

public interface JdbcMapper<T> {
    long insert(T objectData) throws IllegalAccessException, SQLException;

    void update(T objectData) throws IllegalAccessException, SQLException;

    void insertOrUpdate(T objectData);

    Optional<T> findById(Object id) throws SQLException;

    SessionManager getSessionManager();
}
