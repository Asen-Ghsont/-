package row;

import column.Column;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

public class RowInfo {
    private final Object object;
    private final Map<String, String> fields = new LinkedHashMap<>();

    private RowInfo(Object object) {
        this.object = object;
        Class<?> clazz = object.getClass();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            if (field.getAnnotation(Column.class) != null) {
                fields.put(field.getName(), stringOf(field));
            }
        }
    }

    public static RowInfo parse(Object object) {
        return new RowInfo(object);
    }

    private String stringOf(Field field) {
        Object value = null;
        try {
            value = field.get(object);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (value == null) return null;
        switch (field.getType().getSimpleName()) {
            case "Integer" -> {
                return String.valueOf(value);
            }
            case "String" -> {
                return "'" + value + "'";
            }
            case "Boolean" -> {
                if (value == Boolean.TRUE) return "1";
                else return "0";
            }
        }
        return null;
    }

    @Override
    public String toString() {
        StringBuilder fieldName = new StringBuilder();
        StringBuilder fieldValue = new StringBuilder();
        fieldName.append("(");
        fieldValue.append(" values(");
        for (Map.Entry<String, String> entry : fields.entrySet()) {
            fieldName.append(entry.getKey()).append(",");
            fieldValue.append(entry.getValue()).append(",");
        }
        if (fieldName.length() > 1)
            fieldName.deleteCharAt(fieldName.length() - 1);
        fieldName.append(")");
        if (fieldValue.length() > 8)
            fieldValue.deleteCharAt(fieldValue.length() - 1);
        fieldValue.append(")");
        return fieldName.append(fieldValue).toString();
    }
}
