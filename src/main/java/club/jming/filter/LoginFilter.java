package club.jming.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LoginFilter implements Filter {

    private List<String> excluded = new ArrayList<>();

    public void destroy() {
        System.out.println(this.getClass() + " destroy ...." + new SimpleDateFormat("yy-MM-dd hh:mm:ss").format(new Date()));
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest request = (HttpServletRequest) req;

        HttpServletResponse response = (HttpServletResponse) resp;

        Object user = request.getSession().getAttribute("USER");


        if (user != null) {

            chain.doFilter(req, resp);

        } else {
            //获取URI
            String uri = request.getRequestURI();
            //登录界面、css、js资源除外
            if (uri.endsWith("login.jsp") || uri.endsWith(".css") || uri.endsWith(".js") || uri.endsWith("login")||uri.endsWith(".jpg")) {
                System.out.println(uri);
                chain.doFilter(req, resp);
            }else {
                response.sendRedirect(request.getContextPath() + "/page/login.jsp");
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

        System.out.println(this.getClass() + " init ...." + new SimpleDateFormat("yy-MM-dd hh:mm:ss").format(new Date()));
    }
}
