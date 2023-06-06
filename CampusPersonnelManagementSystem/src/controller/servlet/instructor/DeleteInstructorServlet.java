package controller.servlet.instructor;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.ServiceFactory;
import util.GetJSONObject;
import util.NoticeAction;

import java.io.IOException;

@WebServlet("/delete-instructor")
public class DeleteInstructorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        String instructorId = jsonObject.getString("instructorId");
        JSONObject result = new JSONObject();
        if (ServiceFactory.getInstructorService().delete(instructorId)) {
            result.put("success", true);
            NoticeAction.notice("删除辅导员" + instructorId, true);
        } else {
            result.put("success", false);
            NoticeAction.notice("删除辅导员" + instructorId, false);
            result.put("message", "你没有权限");
        }
        resp.getWriter().write(result.toString());
    }
}
