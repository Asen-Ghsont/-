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

@WebServlet("/edit-instructor")
public class EditInstructorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        // 获取JSON中的属性值
        String instructorId = jsonObject.getString("instructorId");
        String name = jsonObject.getString("name");
        String gender = jsonObject.getString("gender");
        int age = jsonObject.getInt("age");
        Instructor instructor = new Instructor(instructorId, name, gender, age);

        JSONObject result = new JSONObject();
        if (ServiceFactory.getInstructorService().change(instructor)) {
            result.put("success", true);
            NoticeAction.notice("修改辅导员" + instructorId, true);
        } else {
            result.put("success", false);
            NoticeAction.notice("修改辅导员" + instructorId, false);
            result.put("message", "格式错误或者你没有权限");
        }
        resp.getWriter().write(result.toString());
    }
}
