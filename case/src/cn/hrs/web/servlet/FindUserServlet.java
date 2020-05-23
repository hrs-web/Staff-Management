package cn.hrs.web.servlet;

import cn.hrs.domian.User;
import cn.hrs.service.UserService;
import cn.hrs.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 通过id获取用户user
 */
@WebServlet("/findUserServlet")
public class FindUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1.获取用户id
        String id = req.getParameter("id");

        // 2.调用service中的方法进行查询
        UserService service = new UserServiceImpl();
        User user = service.findUserById(id);

        // 3.将User存储到request
        req.setAttribute("user",user);

        // 4.转发到update.jsp
        req.getRequestDispatcher("update.jsp").forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
