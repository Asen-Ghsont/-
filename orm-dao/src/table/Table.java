package table;

import column.ColumnChecker;
import column.ColumnInfo;
import database.DBConnection;
import database.DBOperation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.*;

public class Table<T> {
    private final TableInfo<T> tableInfo;
    private final DBOperation dbOperation;

    protected Table(Class<T> clazz) {
        tableInfo = TableInfo.parse(clazz);
        dbOperation = new DBOperation();
        if (!dbOperation.creatTable(this)) {
            throw new RuntimeException("Database connect failed.");
        }
    }

    public boolean add(T t) {
        if (isNotLegal(t)) return false;
        try {
            return dbOperation.insert(this, t);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * TODO 检查是否为主键
     */
    private void checkIsPrimeKey(Field key) {
        ColumnInfo columnInfo = tableInfo.getColumnInfos().get(key);
        if (columnInfo == null) throw new NullPointerException();
        if (!columnInfo.isPrimeKey()) throw new RuntimeException("The key is not a prime key.");
    }

    /**
     * TODO 检查数据 是否 不合法
     */
    private boolean isNotLegal(T t) {
        for (Field field : tableInfo.getColumnInfos().keySet()) {
            if (!ColumnChecker.isLegal(t, field)) {
                return true;
            }
        }
        return false;
    }

    public boolean delete(Field key, Object value) {
        try {
            return dbOperation.delete(this, key.getName(), stringOf(value));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean change(Field primeKey, Object primeValue, Map<Field, Object> valuesForChange) {
        try {
            checkIsPrimeKey(primeKey);
            Map<String, String> values = new HashMap<>();
            for (Map.Entry<Field, Object> entry : valuesForChange.entrySet()) {
                Field field = entry.getKey();
                Object value = entry.getValue();
                ColumnInfo columnInfo = tableInfo.getColumnInfos().get(field);
                if (!ColumnChecker.isLegal(columnInfo, field, value)) {
                    return false;
                }
                values.put(entry.getKey().getName(), stringOf(entry.getValue()));
            }
            return dbOperation.change(this, primeKey.getName(), stringOf(primeValue), values);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * TODO 获取所有的对象
     */
    public List<T> getAllInfo() {
        return getObject(dbOperation.getAllInfo(this));
    }

    /**
     * TODO 获取指定的键值的对象
     */
    public List<T> getTargetInfo(Field key, Object value) {
        return getObject(dbOperation.getTargetInfo(this, key.getName(), stringOf(value)));
    }

    /**
     * TODO 获取全部的指定列的数据
     */
    public List<Object> getAllFieldInfo(Field field) {
        ResultSet resultSet = dbOperation.getAllColumnInfo(this, field.getName());
        return getFieldValue(resultSet);
    }

    /**
     * TODO 获取指定的键值的、指定列的数据
     */
    public List<Object> getTargetFieldInfo(Field key, Object value, Field field) {
        ResultSet resultSet = dbOperation.getTargetColumnInfo(this, key.getName(), stringOf(value), field.getName());
        return getFieldValue(resultSet);
    }

    /**
     * TODO 将数据修改为数据库形式
     */
    private static String stringOf(Object value) {
        String valueTypeName = value.getClass().getSimpleName();
        switch (valueTypeName) {
            case "String" -> {
                return "'" + value + "'";
            }
            case "Integer", "int" -> {
                return value.toString();
            }
            case "Boolean", "boolean" -> {
                if (value.equals(Boolean.TRUE)) {
                    return "1";
                } else {
                    return "0";
                }
            }
            default -> throw new IllegalStateException("Unexpected value: " + valueTypeName);
        }
    }

    /**
     * TODO 解析返回的ResultSet，将其转化为数组
     */
    private List<T> getObject(ResultSet resultSet) {
        List<T> ans = new ArrayList<>();
        try {
            Constructor<?> constructor = null;
            int len = resultSet.getMetaData().getColumnCount();
            for (Constructor<?> c : tableInfo.getClazz().getDeclaredConstructors()) {
                if (c.getParameters().length == len) {
                    constructor = c;
                    break;
                }
            }
            while (resultSet.next()) {
                Object[] values = new Object[len];
                for (int i = 0; i < len; i++) {
                    values[i] = resultSet.getObject(i + 1);
                }
                ans.add((T) Objects.requireNonNull(constructor).newInstance(values));
            }

            //close
            DBConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ans;
    }

    /**
     * TODO 解析返回的ResultSet，将其转化为映射表
     */
    private List<Object> getFieldValue(ResultSet resultSet) {
        List<Object> list = new ArrayList<>();
        try {
            while (resultSet.next()) {
                list.add(resultSet.getObject(1));
            }
            //close
            DBConnection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public TableInfo<T> getTableInfo() {
        return tableInfo;
    }
}