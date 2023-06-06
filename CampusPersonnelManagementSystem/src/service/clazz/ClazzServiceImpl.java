package service.clazz;

import column.ColumnInfo;
import entity.Clazz;
import table.Table;
import table.TableFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClazzServiceImpl implements ClazzService {
    private final Table<Clazz> table;

    public ClazzServiceImpl() {
        table = TableFactory.registerTable(Clazz.class);
    }

    @Override
    public boolean add(Clazz clazz) {
        return table.add(clazz);
    }

    @Override
    public boolean delete(String id) {
        try {
            return table.delete(Clazz.class.getDeclaredField("id"), id);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean change(Clazz clazz) {
        try {
            List<Clazz> list = table.getTargetInfo(Clazz.class.getDeclaredField("id"), clazz.getId());
            if (list.size() != 1) return false;
            else {
                Clazz targetClazz = list.get(0);
                Map<Field, Object> values = new HashMap<>();
                for (Map.Entry<Field, ColumnInfo> entry : table.getTableInfo().getColumnInfos().entrySet()) {
                    Field field = entry.getKey();
                    Object value1 = field.get(clazz);
                    Object value2 = field.get(targetClazz);
                    if (!value1.equals(value2)) {
                        values.put(field, value1);
                    }
                }
                return table.change(Clazz.class.getDeclaredField("id"), clazz.getId(), values);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

    @Override
    public List<Clazz> getAll() {
        return table.getAllInfo();
    }

    @Override
    public List<String> getAllId() {
        List<String> ids = new ArrayList<>();
        try {
            List<Object> list = table.getAllFieldInfo(Clazz.class.getDeclaredField("id"));
            for (Object o: list) {
                ids.add((String) o);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
