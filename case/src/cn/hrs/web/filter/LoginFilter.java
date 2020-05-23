package cn.hrs.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 登录验证的过滤器
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        // 0.类型转换
        HttpServletRequest request = (HttpServletRequest) req;
        // 1.获取请求路径
        String uri = request.getRequestURI();
        // 2.判断是否有登录相关的资源路径,要注意排除掉css/js/图片/验证码等资源
        if (uri.contains("/login.jsp") || uri.contains("/loginServlet") || uri.contains("/css/") || uri.contains("/js/") || uri.contains("/fonts/") || uri.contains("/checkCodeServlet")){
            // 包含，用户就是想登录。放行
            chain.doFilter(req, resp);
        }else {
            // 验证用户是否已登录
            // 3.从session中获取user对象
            Object administrator = request.getSession().getAttribute("administrator");
            if (administrator != null){
                // 已登录，放行
                chain.doFilter(req,resp);
            }else {
                // 未登录，提示并跳转到login.jsp
                request.setAttribute("login_msg","您尚未登录，请登录");
                request.getRequestDispatcher("/login.jsp").forward(request,resp);
            }
        }
        
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
