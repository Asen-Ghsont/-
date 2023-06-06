package service.user;

import entity.User;
import table.Table;
import table.TableFactory;

import java.io.File;
import java.util.List;

public class UserServiceImpl implements UserService{
    private final Table<User> table;

    public UserServiceImpl() {
        table = TableFactory.registerTable(User.class);
    }

    @Override
    public boolean authenticate(User user) {
        try {
            List<User> list = table.getTargetInfo(User.class.getDeclaredField("name"), user.getName());
            if (list.size() != 1) return false;
            else {
                User targetUser = list.get(0);
                return (targetUser.getPassword().equals(user.getPassword()));
            }
        } catch (NoSuchFieldException e) {
            return false;
        }
    }

    @Override
    public boolean add(User user) {
        if (!authenticate(user)) {
            return table.add(user);
        } else return false;
    }
}
