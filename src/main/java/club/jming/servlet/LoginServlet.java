package club.jming.servlet;

import club.jming.dao.UserDAO;
import club.jming.entity.User;
import club.jming.utils.ResMessageBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ValueFilter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 登录servlet，进行账号密码验证，包括退出时的返回页面
 * 有空增加一个验证码功能
 */
public class LoginServlet extends HttpServlet {

    ValueFilter filter = new ValueFilter() {
        @Override
        public Object process(Object object, String name, Object value) {

            System.out.println("filter");

            if(value instanceof BigDecimal || value instanceof Double || value instanceof Float){
                DecimalFormat df   =new   java.text.DecimalFormat("#.00");
                return df.format(value);
            }
            return value;
        }
    };

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
        user.setUsername(req.getParameter("name"));
        user.setPassword(req.getParameter("password"));

        System.out.println(req.getParameter("name"));
        System.out.println(req.getParameter("password"));

        System.out.println(111);
        if (userDAO.login(user)) {
            req.getSession().setAttribute("USER",user);
            System.out.println(22222);
            resp.setContentType("application/json;charset=utf-8");
            //code不为0报错
            resp.getWriter().write(JSON.toJSONString(ResMessageBuilder.resMessage(0,"success","登录成功"),filter));
        } else {
            System.out.println(3333333);
            resp.getWriter().write(JSON.toJSONString(ResMessageBuilder.resMessage(0,"fail","用户名或密码错误"),filter));
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
            req.getSession().removeAttribute("USER");
            resp.sendRedirect("/page/login.jsp");
        }
    }
}
