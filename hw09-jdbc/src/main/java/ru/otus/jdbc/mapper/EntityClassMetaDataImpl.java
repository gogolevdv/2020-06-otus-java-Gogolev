package ru.otus.jdbc.mapper;

import ru.otus.core.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {

    private final String className;
    private final Constructor<T> constructor;
    private final Field idField;
    private final List<Field> allFields;
    private final List<Field> fieldsWithoutId;

    public EntityClassMetaDataImpl(Class<T> tClass) {
        className = tClass.getSimpleName();

        Field[] fields = tClass.getDeclaredFields();

        allFields = new ArrayList<>();
        Arrays.stream(fields).forEach(val -> {
            allFields.add(val);
        });

        final Field[] idField1 = new Field[1];
        allFields.forEach(val -> {
            if (val.isAnnotationPresent(Id.class)) idField1[0] = val;
        });
        idField = idField1[0];

        fieldsWithoutId = new ArrayList<>();
        Arrays.stream(fields).forEach(val -> {
            if (!val.isAnnotationPresent(Id.class)) fieldsWithoutId.add(val);
        });

        try {
            constructor = tClass.getDeclaredConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }


    @Override
    public String getName() {
        return className;
    }

    @Override
    public Constructor getConstructor() {
        return constructor;
    }

    @Override
    public Field getIdField() throws Exception {

        if (allFields.contains(idField)) return idField;
        else
            throw new Exception("The field with annotation not found");
    }

    @Override
    public List<Field> getAllFields() {
        return allFields;
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        return fieldsWithoutId;
    }
}
