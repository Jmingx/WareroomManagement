package club.jming.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * V1.0
 * 主页的按钮控制器，用来进行页面跳转，起名不是很合理...
 */
@Deprecated
public class buttonServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String button = req.getParameter("button");
        if (button.equals("business")){
            resp.sendRedirect("/business?type=show");
        }
        if (button.equals("component")){
            resp.sendRedirect("/component?type=show");
        }
        if (button.equals("supplier")){
            resp.sendRedirect("/supplier?type=show");
        }
        if (button.equals("report")){
            resp.sendRedirect("/report?type=show");
        }
        if (button.equals("inventory")){
            resp.sendRedirect("/inventory?type=show");
        }
    }
}
