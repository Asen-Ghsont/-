package column;

import java.lang.reflect.Field;

public class ColumnInfo {
    private Field field;
    private String label;
    private boolean isPrimeKey;
    private boolean isNullable;
    private int max;
    private int min;

    public static ColumnInfo parse(Field field) {
        if (field == null) throw new NullPointerException();
        Column column = field.getAnnotation(Column.class);
        if (column == null) return null;
        ColumnInfo columnInfo = new ColumnInfo();
        columnInfo.field = field;
        columnInfo.label = column.label();
        columnInfo.isPrimeKey = column.isPrimeKey();
        columnInfo.isNullable = column.isNullable();
        columnInfo.max = column.max();
        columnInfo.min = column.min();
        return columnInfo;
    }

    @Override
    public String toString() {
        StringBuilder sql = new StringBuilder();
        sql.append(field.getName());
        sql.append(" ");
        switch (field.getType().getSimpleName()) {
            case "String" -> {
                sql.append("varchar");
                sql.append("(").append(max).append(") ");
            }
            case "Integer", "int" -> sql.append("int ");
            case "Boolean", "boolean" -> sql.append("bit ");
        }
        if (!isNullable) {
            sql.append("not null");
        }
        return sql.toString();
    }

    public String getLabel() {
        return label;
    }

    public boolean isPrimeKey() {
        return isPrimeKey;
    }

    public boolean isNullable() {
        return isNullable;
    }

    public int getMax() {
        return max;
    }

    public int getMin() {
        return min;
    }


}
