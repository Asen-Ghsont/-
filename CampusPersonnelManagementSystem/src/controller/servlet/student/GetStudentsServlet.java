package controller.servlet.student;

import entity.Student;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import service.ServiceFactory;
import service.student.StudentService;

import java.io.IOException;

@WebServlet("/students")
public class GetStudentsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONArray jsonArray = new JSONArray();
        StudentService studentService = ServiceFactory.getStudentService();
        for (Student student: studentService.getAll()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("studentId", student.getId());
            jsonObject.put("name", student.getName());
            jsonObject.put("gender", student.getGender());
            jsonObject.put("age", student.getAge());
            jsonObject.put("department", student.getDepartment());
            jsonArray.put(jsonObject);
        }
        resp.getWriter().write(jsonArray.toString());
    }
}
