package com.company.mappers;

import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
public class BeanPropertyRowMapper<T> implements RowMapper<T> {
    private final Class<T> resultClass;

    public T mapRow(ResultSet resultSet) throws SQLException {
        try {
            T obj = resultClass.getConstructor().newInstance();

            for (Field declaredField : resultClass.getDeclaredFields()) {
                declaredField.setAccessible(true);
                declaredField.set(obj, extractValue(resultSet, declaredField));
            }

            return obj;
        } catch (InstantiationException | InvocationTargetException
                | NoSuchMethodException | IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    private Object extractValue(ResultSet resultSet, Field field) throws SQLException {
        String fieldName = field.getName();
        if (!containsColumn(resultSet, fieldName)) {
            fieldName = toSnakeCase(fieldName);
            if (!containsColumn(resultSet, fieldName)) {
                return null;
            }
        } return resultSet.getObject(fieldName, field.getType());
    }


    private boolean containsColumn(ResultSet resultSet, String name) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String s = resultSet.getMetaData().getColumnName(i);
            if (s.equals(name)) {
                return true;
            }
        }
        return false;
    }

    private String toSnakeCase(String fieldName) {
        Pattern pattern = Pattern.compile("[A-Z]");
        Matcher matcher = pattern.matcher(fieldName);
        return matcher.replaceAll(res -> "_" + res.group().toLowerCase());
    }

}

