package service.student;

import entity.Student;

import java.util.List;

public interface StudentService {
    List<Student> getAll();
    boolean add(Student student);
    boolean delete(String id);
    boolean change(Student student);
    List<String> getAllId();
}
