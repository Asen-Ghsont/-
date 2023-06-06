package controller.servlet.user;

import entity.User;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.json.JSONObject;
import service.ServiceFactory;
import util.GetJSONObject;
import util.NoticeAction;

import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final int MAX_INVALID_TIME = 60;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        JSONObject jsonObject = GetJSONObject.from(req);

        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");

        User user = new User(username, password);

        if (ServiceFactory.getUserService().authenticate(user)) {
            resp.setStatus(HttpServletResponse.SC_OK);
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            session.setMaxInactiveInterval(MAX_INVALID_TIME);
            Cookie cookie = new Cookie("username", username);
            cookie.setMaxAge(60);
            resp.addCookie(cookie);
            NoticeAction.notice("用户" + username + "登录", true);
        } else {
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            NoticeAction.notice("用户" + username + "登录", false);
        }
    }
}
