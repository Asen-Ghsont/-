package service.clazz;

import entity.Clazz;

import java.util.List;

public interface ClazzService {
    boolean add(Clazz clazz);
    boolean delete(String id);
    boolean change(Clazz clazz);
    List<Clazz> getAll();
    List<String> getAllId();
}
