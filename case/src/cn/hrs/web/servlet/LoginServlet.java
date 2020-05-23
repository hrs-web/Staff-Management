package cn.hrs.web.servlet;

import cn.hrs.domian.Administrator;
import cn.hrs.service.UserService;
import cn.hrs.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.设置编码方式
        req.setCharacterEncoding("utf-8");

        // 2.获取数据
        // 2.1.获取用户输入的验证吗
        String verifycode = req.getParameter("verifycode");

        // 3.验证码效验
        HttpSession session = req.getSession();
        String checkcode_server =(String) session.getAttribute("CHECKCODE_SERVER"); //获取自动生成的验证码
        session.removeAttribute("CHECKCODE_SERVER");  //确保验证码一次性
        if (!checkcode_server.equalsIgnoreCase(verifycode)){
            req.setAttribute("login_msg","验证码错误！");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
            return;
        }
        Map<String, String[]> map = req.getParameterMap();
        // 4.封装user
        Administrator administrator = new  Administrator();
        try {
            BeanUtils.populate(administrator,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 5.调用server查询
        UserService service = new UserServiceImpl();
        administrator = service.login(administrator);
        // 6.判断是否登录成功
        if (administrator != null) {
            //登录成功
            //将用户存入session
            session.setAttribute("administrator",administrator);
            //页面跳转
            resp.sendRedirect(req.getContextPath()+"/index.jsp");
        }else {
            //登录失败
            //提示错误信息
            req.setAttribute("login_msg","用户名或密码错误！");
            //请求转发
            req.getRequestDispatcher("/login.jsp").forward(req,resp);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
