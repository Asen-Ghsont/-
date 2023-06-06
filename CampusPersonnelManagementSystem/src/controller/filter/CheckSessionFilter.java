package controller.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import util.NoticeAction;

import java.io.IOException;

@WebFilter("/management/*")
public class CheckSessionFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            NoticeAction.notice("用户" + session.getAttribute("username") + "执行访问");
            chain.doFilter(request, response);
        } else {
            response.sendRedirect("/login/index.html");
        }
    }
}
