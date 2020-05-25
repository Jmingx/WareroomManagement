package club.jming.filter;

import club.jming.utils.Decoding;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * V1.0
 * 用于拦截req，将parameter对转换字符集，然后装进attribute
 */
@Deprecated
public class TranscodingFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        Map map = request.getParameterMap();
        Set<String> set = map.keySet();
        String source ;
        for (String string : set){
            source = Decoding.transcoding(servletRequest.getParameter(string));
            request.setAttribute(string,source);
        }
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}
