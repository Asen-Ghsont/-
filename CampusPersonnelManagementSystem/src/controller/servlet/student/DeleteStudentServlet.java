package controller.servlet.student;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.ServiceFactory;
import util.GetJSONObject;
import util.NoticeAction;

import java.io.IOException;

@WebServlet("/delete-student")
public class DeleteStudentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        String studentId = jsonObject.getString("studentId");
        JSONObject result = new JSONObject();
        if (ServiceFactory.getStudentService().delete(studentId)) {
            result.put("success", true);
            NoticeAction.notice("删除学生" + studentId, true);
        } else {
            result.put("success", false);
            NoticeAction.notice("删除学生" + studentId, false);
            result.put("message", "你没有权限");
        }
        resp.getWriter().write(result.toString());
    }
}
