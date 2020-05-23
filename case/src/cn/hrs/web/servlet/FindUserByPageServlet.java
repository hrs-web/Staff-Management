package cn.hrs.web.servlet;

import cn.hrs.domian.PageBean;
import cn.hrs.domian.User;
import cn.hrs.service.UserService;
import cn.hrs.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 分页查询
 */
@WebServlet("/findUserByPageServlet")
public class FindUserByPageServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.设置编码
        req.setCharacterEncoding("utf-8");
        // 2.获取参数
        String currentPage = req.getParameter("currentPage"); //当前页码
        String rows = req.getParameter("rows"); //当前显示的条数
        Map<String, String[]> condition = req.getParameterMap(); //获取条件查询的参数

        // 如果页码和显示条数为空的话就赋初始化值
        if (currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if (rows == null || "".equals(rows)){
            rows = "5";
        }

        // 3.调用service查询
        UserService service = new UserServiceImpl();
        PageBean<User> pb = service.findUserByPage(currentPage,rows,condition);

        // 4.将pb、condition储存到request
        req.setAttribute("pb",pb);
        req.setAttribute("condition",condition);

        // 5.转发到list.jsp
        req.getRequestDispatcher("list.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
