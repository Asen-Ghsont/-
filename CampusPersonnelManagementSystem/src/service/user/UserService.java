package service.user;

import entity.User;

public interface UserService {
    boolean authenticate(User user);
    boolean add(User user);
}
