package controller.servlet.clazz;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.ServiceFactory;
import util.GetJSONObject;
import util.NoticeAction;

import java.io.IOException;

@WebServlet("/delete-class")
public class DeleteClassServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        String clazzId = jsonObject.getString("clazzId");
        JSONObject result = new JSONObject();
        if (ServiceFactory.getClazzService().delete(clazzId)) {
            result.put("success", true);
            NoticeAction.notice("删除班级" + clazzId, true);
        } else {
            result.put("success", false);
            NoticeAction.notice("删除班级" + clazzId, false);
            result.put("message", "删除失败");
        }
        resp.getWriter().write(result.toString());
    }
}
