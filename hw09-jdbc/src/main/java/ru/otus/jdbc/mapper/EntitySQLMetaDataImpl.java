package ru.otus.jdbc.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl<T> implements EntitySQLMetaData {
    private static final Logger logger = LoggerFactory.getLogger(EntityClassMetaDataImpl.class);

    private final EntityClassMetaData<T> classMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<T> classMetaData) {
        this.classMetaData = classMetaData;
    }

    @Override
    public String getSelectAllSql() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getSelectByIdSql() throws Exception {

        var allFields = classMetaData.getAllFields();

        String fieldsList = allFields.stream().map(Field::getName).collect(Collectors.joining(","));

        return String.format("select %s from %s where %s  = ?",
                fieldsList, classMetaData.getName(), classMetaData.getIdField().getName()).toUpperCase();
    }

    @Override
    public String getInsertSql() {
        var fieldsWithoutId = classMetaData.getFieldsWithoutId();
        List<String> allNameFieldsWithoutId = new ArrayList<>();
        List<String> valuesPattermArray = new ArrayList<>();
        for (Field fieldName : fieldsWithoutId) {
            allNameFieldsWithoutId.add(fieldName.getName());
            valuesPattermArray.add("?");
        }
        String fieldsList = String.join(",", allNameFieldsWithoutId);
        String valuesList = String.join(",", valuesPattermArray);

        return String.format("insert into %s (%s) values (%s)",
                classMetaData.getName(), fieldsList, valuesList)
                .toUpperCase();
    }

    @Override
    public String getUpdateSql() {
        throw new UnsupportedOperationException();
    }
}