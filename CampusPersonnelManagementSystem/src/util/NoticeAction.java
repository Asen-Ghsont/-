package util;

import java.util.Date;

public class NoticeAction {
    public static void notice(String message) {
        String className = new Throwable().getStackTrace()[1].getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        System.out.println("\033[31;2m" + new Date() + "\033[0m" + " [" + className + "] " + message);
    }

    public static void notice(String message, boolean success) {
        String result;
        if (success) {
            result = "成功";
        } else {
            result = "失败";
        }
        String className = new Throwable().getStackTrace()[1].getClassName();
        className = className.substring(className.lastIndexOf(".") + 1);
        System.out.println("\033[31;2m" + new Date() + "\033[0m" + " [" + className + "] " + message + result);
    }
}
