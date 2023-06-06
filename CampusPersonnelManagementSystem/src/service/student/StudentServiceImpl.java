package service.student;

import column.ColumnInfo;
import entity.Student;
import table.Table;
import table.TableFactory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentServiceImpl implements StudentService {
    private final Table<Student> table;

    public StudentServiceImpl() {
        table = TableFactory.registerTable(Student.class);
    }

    @Override
    public boolean add(Student student) {
        try {
            List<Student> list = table.getTargetInfo(Student.class.getDeclaredField("id"), student.getId());
            if (!list.isEmpty()) return false;
            return table.add(student);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(String id) {
        try {
            return table.delete(Student.class.getDeclaredField("id"), id);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean change(Student newStudent) {
        String id = newStudent.getId();
        try {
            List<Student> list = table.getTargetInfo(Student.class.getDeclaredField("id"), id);
            if (list.size() != 1) return false;
            else {
                Student targetStudent = list.get(0);
                Map<Field, Object> values = new HashMap<>();
                for (Map.Entry<Field, ColumnInfo> entry : table.getTableInfo().getColumnInfos().entrySet()) {
                    Field field = entry.getKey();
                    Object oldValue = field.get(targetStudent);
                    Object newValue = field.get(newStudent);
                    if (!oldValue.equals(newValue)) {
                        values.put(field, newValue);
                    }
                }
                return table.change(Student.class.getDeclaredField("id"), id, values);
            }
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Student> getAll() {
        return table.getAllInfo();
    }

    @Override
    public List<String> getAllId() {
        List<String> ids = new ArrayList<>();
        try {
            List<Object> list = table.getAllFieldInfo(Student.class.getDeclaredField("id"));
            for (Object o : list) {
                ids.add((String) o);
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
        return ids;
    }
}
