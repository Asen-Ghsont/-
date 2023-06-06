package controller.servlet.instructor;

import entity.Instructor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import service.ServiceFactory;
import service.instructor.InstructorService;

import java.io.IOException;

@WebServlet("/instructors")
public class GetInstructorsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONArray jsonArray = new JSONArray();
        InstructorService instructorService = ServiceFactory.getInstructorService();
        for (Instructor instructor: instructorService.getAll()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("instructorId", instructor.getId());
            jsonObject.put("name", instructor.getName());
            jsonObject.put("gender", instructor.getGender());
            jsonObject.put("age", instructor.getAge());
            jsonArray.put(jsonObject);
        }
        resp.getWriter().write(jsonArray.toString());
    }
}
