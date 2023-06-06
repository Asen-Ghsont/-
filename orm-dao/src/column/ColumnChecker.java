package column;

import java.lang.reflect.Field;

public class ColumnChecker {
    public static boolean isLegal(Object object, Field field) {
        if (object == null || field == null) throw new NullPointerException();
        field.setAccessible(true);
        Object value;
        try {
            value = field.get(object);
        } catch (Exception e) {
            return false;
        }
        if (value == null) return false;
        return isLegal(ColumnInfo.parse(field), field, value);
    }

    public static boolean isLegal(ColumnInfo columnInfo, Field field, Object value) {
        if (columnInfo == null) return false;
        switch (field.getType().getSimpleName()) {
            case "String" -> {
                int len = ((String) value).length();
                return len <= columnInfo.getMax() && len >= columnInfo.getMin();
            }
            case "Integer", "int" -> {
                return (Integer) value <= columnInfo.getMax() && (Integer) value >= columnInfo.getMin();
            }
            case "Boolean", "boolean" -> {
                return true;
            }
            default -> {
                return false;
            }
        }

    }
}
