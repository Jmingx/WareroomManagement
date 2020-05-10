package club.jming.servlet;

import club.jming.dao.UserDAO;
import club.jming.entity.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录servlet，进行账号密码验证，包括退出时的返回页面
 * 有空增加一个验证码功能
 */
public class LoginServlet extends HttpServlet {
    /**
     * 密码账号验证
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = new User();
        UserDAO userDAO = new UserDAO();
        user.setUsername((String) req.getAttribute("name"));
        user.setPassword((String) req.getAttribute("password"));

        if (userDAO.login(user)) {
            req.getSession().setAttribute("username", req.getAttribute("name"));
            resp.sendRedirect("home.jsp");
        } else {
            resp.sendRedirect("LOGIN.jsp");
        }
    }

    /**
     * 退出时的返回页面
     *
     * @param req
     * @param resp
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String type = req.getParameter("type");
//        System.out.println(type);
        if (type.equals("logout")) {
            req.getSession().setAttribute("username", null);
            resp.sendRedirect("LOGIN.jsp");
        }
    }
}
