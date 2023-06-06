package service;

import service.clazz.ClazzService;
import service.clazz.ClazzServiceImpl;
import service.instructor.InstructorService;
import service.instructor.InstructorServiceImpl;
import service.student.StudentService;
import service.student.StudentServiceImpl;
import service.user.UserService;
import service.user.UserServiceImpl;
import table.TableFactory;

import java.util.Properties;

public class ServiceFactory {
    private static StudentService studentService;
    private static UserService userService;
    private static ClazzService clazzService;
    private static InstructorService instructorService;

    static {
        Properties properties = new Properties();
        properties.put("databaseName", "person");
        TableFactory.setConnectConfig(properties);
    }

    public static StudentService getStudentService() {
        if (studentService == null) {
            studentService = new StudentServiceImpl();
        }
        return studentService;
    }

    public static UserService getUserService() {
        if (userService == null) {
            userService = new UserServiceImpl();
        }
        return userService;
    }

    public static ClazzService getClazzService() {
        if (clazzService == null) {
            clazzService = new ClazzServiceImpl();
        }
        return clazzService;
    }

    public static InstructorService getInstructorService() {
        if (instructorService == null) {
            instructorService = new InstructorServiceImpl();
        }
        return instructorService;
    }
}
