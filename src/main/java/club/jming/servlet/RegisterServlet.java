package club.jming.servlet;

import club.jming.dao.UserDAO;
import club.jming.entity.User;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 注册服务器
 */
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String name = (String) req.getAttribute("name");
        String password = (String) req.getAttribute("password");

        resp.setCharacterEncoding("UTF-8");
        UserDAO userDAO = new UserDAO();
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);

        resp.setContentType("utf8");
        if (userDAO.isRegister(user)){
            resp.getWriter().println("1");
        }else {
            userDAO.register(user);
            resp.getWriter().println("2");
        }
    }
}
