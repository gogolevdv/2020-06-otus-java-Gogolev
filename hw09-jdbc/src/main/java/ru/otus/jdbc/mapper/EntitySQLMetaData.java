package ru.otus.jdbc.mapper;

public interface EntitySQLMetaData {
    String getSelectAllSql();

    String getSelectByIdSql() throws Exception;

    String getInsertSql();

    String getUpdateSql();
}
