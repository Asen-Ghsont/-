package controller.servlet.student;

import entity.Student;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.ServiceFactory;
import util.GetJSONObject;
import util.NoticeAction;

import java.io.IOException;

@WebServlet("/edit-student")
public class EditStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        // 获取JSON中的属性值
        String studentId = jsonObject.getString("studentId");
        String name = jsonObject.getString("name");
        String gender = jsonObject.getString("gender");
        int age = jsonObject.getInt("age");
        String department = jsonObject.getString("department");
        Student student = new Student(studentId, name, gender, age, department);

        JSONObject result = new JSONObject();
        if (ServiceFactory.getStudentService().change(student)) {
            result.put("success", true);
            NoticeAction.notice("修改学生" + studentId, true);
        } else {
            result.put("success", false);
            NoticeAction.notice("修改学生" + studentId, false);
            result.put("message", "格式错误或者你没有权限");
        }
        resp.getWriter().write(result.toString());
    }
}
