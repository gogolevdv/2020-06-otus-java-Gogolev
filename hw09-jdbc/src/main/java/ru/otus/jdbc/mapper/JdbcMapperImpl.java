package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.core.sessionmanager.SessionManager;
import ru.otus.jdbc.DbExecutor;
import ru.otus.jdbc.sessionmanager.SessionManagerJdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class JdbcMapperImpl<T> implements JdbcMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcMapperImpl.class);

    private final SessionManagerJdbc sessionManager;
    private final DbExecutor<T> dbExecutor;
    private final EntityClassMetaData<T> entityClassMetaData;
    private final EntitySQLMetaData sqlMetaData;


    public JdbcMapperImpl(SessionManagerJdbc sessionManager, DbExecutor dbExecutor, EntitySQLMetaData sqlMetaData, EntityClassMetaData entityClassMetaData) {
        this.sessionManager = sessionManager;
        this.dbExecutor = dbExecutor;
        this.sqlMetaData = sqlMetaData;
        this.entityClassMetaData = entityClassMetaData;

    }

    @Override
    public long insert(T objectData) throws IllegalAccessException, SQLException {

        return dbExecutor.executeInsert(getConnection(),
                sqlMetaData.getInsertSql(), getFieldValuesList(objectData));
    }

    @Override
    public void update(T objectData) throws IllegalAccessException, SQLException {

        dbExecutor.executeInsert(sessionManager.getCurrentSession().getConnection(),
                sqlMetaData.getUpdateSql(), getFieldValuesList(objectData));

    }

    @Override
    public void insertOrUpdate(Object objectData) {

    }

    @Override
    public Optional<T> findById(Object id) {

        try {

            return dbExecutor.executeSelect(getConnection(), sqlMetaData.getSelectByIdSql(), id,
                    rs -> {
                        try {
                            if (rs.next()) {
                                return createResulSet(rs);
                            }
                        } catch (SQLException e) {
                            logger.info(e.getMessage(), e);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        throw new UnsupportedOperationException();
                    });
        } catch (Exception e) {

        }
        return Optional.empty();
    }

    public SessionManager getSessionManager() {
        return this.sessionManager;
    }

    public Connection getConnection() {
        return this.sessionManager.getCurrentSession().getConnection();
    }

    private T createResulSet(ResultSet rs) throws Exception {
        try {
            var instance = entityClassMetaData.getConstructor().newInstance();

            var idField = entityClassMetaData.getIdField();
            idField.setAccessible(true);
            idField.set(instance, rs.getObject(idField.getName()));

            for (var filed : entityClassMetaData.getFieldsWithoutId()) {
                filed.setAccessible(true);
                filed.set(instance, rs.getObject(filed.getName()));
            }

            return instance;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    private List<Object> getFieldValuesList(T objectData) throws IllegalAccessException {
        var params = new ArrayList<>();
        for (var filed : entityClassMetaData.getFieldsWithoutId()) {
            filed.setAccessible(true);
            params.add(filed.get(objectData));
        }
        return params;
    }
}

