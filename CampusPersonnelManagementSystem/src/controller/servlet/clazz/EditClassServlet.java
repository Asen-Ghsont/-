package controller.servlet.clazz;

import entity.Clazz;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import service.ServiceFactory;
import util.GetJSONObject;
import util.NoticeAction;

import java.io.IOException;

@WebServlet("/edit-class")
public class EditClassServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        //获取JSON中的属性值
        String clazzId = jsonObject.getString("clazzId");
        String name = jsonObject.getString("name");
        String department = jsonObject.getString("department");
        Clazz clazz = new Clazz(clazzId, name, department);

        //返回result作为结果
        JSONObject result = new JSONObject();
        if (ServiceFactory.getClazzService().change(clazz)) {
            result.put("success", true);
            NoticeAction.notice("修改课程" + clazzId, true);
        } else {
            result.put("success", false);
            NoticeAction.notice("修改课程" + clazzId, false);
            result.put("message", "格式错误");
        }
        resp.getWriter().write(result.toString());
    }
}
