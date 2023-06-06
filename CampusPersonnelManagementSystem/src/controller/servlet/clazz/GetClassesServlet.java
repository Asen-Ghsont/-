package controller.servlet.clazz;

import entity.Clazz;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import service.ServiceFactory;
import service.clazz.ClazzService;

import java.io.IOException;

@WebServlet("/classes")
public class GetClassesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONArray jsonArray = new JSONArray();
        ClazzService clazzService = ServiceFactory.getClazzService();
        for (Clazz clazz: clazzService.getAll()) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("clazzId", clazz.getId());
            jsonObject.put("name", clazz.getName());
            jsonObject.put("department", clazz.getDepartment());
            jsonArray.put(jsonObject);
        }
        resp.getWriter().write(jsonArray.toString());
    }
}
