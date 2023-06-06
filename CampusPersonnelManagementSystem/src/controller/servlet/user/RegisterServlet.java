package controller.servlet.user;

import entity.User;
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

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        User user = new User(username, password);

        if (ServiceFactory.getUserService().add(user)) {
            resp.setStatus(HttpServletResponse.SC_CREATED);
            NoticeAction.notice("用户" + username + "注册", true);
        } else {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
            NoticeAction.notice("用户" + username + "注册", false);
        }
    }
}
