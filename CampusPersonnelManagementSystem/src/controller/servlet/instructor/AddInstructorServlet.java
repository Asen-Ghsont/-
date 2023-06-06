package controller.servlet.instructor;

import entity.Instructor;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.ServiceFactory;
import util.GetJSONObject;
import util.NoticeAction;

import java.io.IOException;

@WebServlet("/add-instructor")
public class AddInstructorServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        // 获取JSON中的属性值
        String instructorId = jsonObject.getString("instructorId");
        String name = jsonObject.getString("name");
        String gender = jsonObject.getString("gender");
        int age = jsonObject.getInt("age");
        Instructor instructor = new Instructor(instructorId, name, gender, age);

        // 返回result
        JSONObject result = new JSONObject();
        if (ServiceFactory.getInstructorService().add(instructor)) {
            result.put("success", true);
            NoticeAction.notice("添加辅导员" + instructorId, true);
        } else {
            result.put("success", false);
            NoticeAction.notice("添加辅导员" + instructorId, false);
            result.put("message", "格式错误");
        }
        resp.getWriter().write(result.toString());
    }
}
