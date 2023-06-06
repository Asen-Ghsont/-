package service.instructor;

import entity.Instructor;

import java.util.List;

public interface InstructorService {
    boolean add(Instructor instructor);
    boolean delete(String id);
    boolean change(Instructor instructor);
    List<Instructor> getAll();
    List<String> getAllId();
}
