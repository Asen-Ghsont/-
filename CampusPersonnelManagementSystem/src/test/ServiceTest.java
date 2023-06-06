package test;

import entity.Clazz;
import entity.User;
import service.ServiceFactory;
import service.clazz.ClazzService;
import service.student.StudentService;
import service.user.UserService;

public class ServiceTest {
    public static void main(String[] args) {
        StudentService studentService = ServiceFactory.getStudentService();
        System.out.println(studentService.getAllId());
        UserService userService = ServiceFactory.getUserService();
        System.out.println(userService.authenticate(new User("阿森", "123456")));
        ClazzService clazzService = ServiceFactory.getClazzService();
        System.out.println(clazzService.add(new Clazz("123", "测试班", "测试系")));
    }
}
