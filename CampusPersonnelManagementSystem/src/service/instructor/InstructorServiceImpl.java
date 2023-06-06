package service.instructor;

import column.ColumnInfo;
import entity.Instructor;
import table.Table;
import table.TableFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstructorServiceImpl implements InstructorService {
    private final Table<Instructor> table;

    public InstructorServiceImpl() {
        table = TableFactory.registerTable(Instructor.class);
    }

    @Override
    public boolean add(Instructor instructor) {
        try {
            List<Instructor> list = table.getTargetInfo(Instructor.class.getDeclaredField("id"), instructor.getId());
            if (!list.isEmpty()) return false;
            return table.add(instructor);
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    @Override
    public boolean delete(String id) {
        try {
            return table.delete(Instructor.class.getDeclaredField("id"), id);
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    @Override
    public boolean change(Instructor newInstructor) {
        String id = newInstructor.getId();
        try {
            List<Instructor> list = table.getTargetInfo(Instructor.class.getDeclaredField("id"), id);
            if (list.size() != 1) return false;
            else {
                Instructor targetInstructor = list.get(0);
                Map<Field, Object> values = new HashMap<>();
                for (Map.Entry<Field, ColumnInfo> entry : table.getTableInfo().getColumnInfos().entrySet()) {
                    Field field = entry.getKey();
                    Object oldValue = field.get(targetInstructor);
                    Object newValue = field.get(newInstructor);
                    if (!oldValue.equals(newValue)) {
                        values.put(field, newValue);
                    }
                }
                return table.change(Instructor.class.getDeclaredField("id"), id, values);
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Instructor> getAll() {
        return table.getAllInfo();
    }

    @Override
    public List<String> getAllId() {
        List<String> ids = new ArrayList<>();
        try {
            List<Object> list = table.getAllFieldInfo(Instructor.class.getDeclaredField("id"));
            for (Object o: list) {
                ids.add((String) o);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
