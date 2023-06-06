package controller.servlet.clazz;

import entity.Clazz;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.ServiceFactory;
import util.GetJSONObject;
import util.NoticeAction;

import java.io.IOException;

@WebServlet("/add-class")
public class AddClassServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        String clazzId = jsonObject.getString("clazzId");
        String name = jsonObject.getString("name");
        String department = jsonObject.getString("department");
        Clazz clazz = new Clazz(clazzId, name, department);

        JSONObject result = new JSONObject();
        if (ServiceFactory.getClazzService().add(clazz)) {
            result.put("success", true);
            NoticeAction.notice("添加班级" + clazzId, true);
        } else {
            result.put("success", false);
            NoticeAction.notice("添加班级" + clazzId, false);
            result.put("message", "格式错误");
        }
        resp.getWriter().write(result.toString());
    }
}