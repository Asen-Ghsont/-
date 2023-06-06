package table;

import column.Column;
import column.ColumnInfo;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class TableInfo<T> {
    private String tableName;
    private Class<T> clazz;
    private Map<Field, ColumnInfo> columnInfos = new LinkedHashMap<>();

    private TableInfo() {
    }

    protected static <T> TableInfo<T> parse(Class<T> clazz) {
        if (clazz == null) throw new NullPointerException();
        TableInfo<T> tableInfo = new TableInfo<>();
        tableInfo.clazz = clazz;
        tableInfo.tableName = clazz.getSimpleName();
        for (Field field: clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(Column.class) != null) {
                tableInfo.columnInfos.put(field, ColumnInfo.parse(field));
            }
        }
        return tableInfo;
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        sql.append("create table").append(" if not exists ");
        sql.append(tableName).append("(").append("\n");
        for (ColumnInfo columnInfo: columnInfos.values()) {
            sql.append("    ");
            sql.append(columnInfo).append(",\n");
        }
        boolean hasPrimeKey = false;

        for (Map.Entry<Field, ColumnInfo> entry: columnInfos.entrySet()) {
            if (entry.getValue().isPrimeKey()) {
                if (!hasPrimeKey) {
                    sql.append("    ");
                    hasPrimeKey = true;
                    sql.append("PRIMARY KEY(");
                }
                sql.append(entry.getKey().getName()).append(",");
            }
        }
        if (hasPrimeKey) {
            sql.deleteCharAt(sql.length() - 1);
            sql.append(")\n");
        } else {
            sql.deleteCharAt(sql.length() - 2);
        }
        sql.append(");");
        return sql.toString();
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public Map<Field, ColumnInfo> getColumnInfos() {
        return columnInfos;
    }

    public void setColumnInfos(Map<Field, ColumnInfo> columnInfos) {
        this.columnInfos = columnInfos;
    }
}
