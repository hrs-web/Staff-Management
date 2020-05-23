package cn.hrs.web.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 测试过滤敏感词的servlet
 */
@WebServlet("/testServlet")
public class TestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获取名字
        /*String name = req.getParameter("name");
        String msg = req.getParameter("msg");
        System.out.println(name+":"+msg);*/
        String[] names = req.getParameterValues("name");
        String[] msgs = req.getParameterValues("msg");
        for (String str : names){
            System.out.println(str);
        }
        for (String str : msgs){
            System.out.println(str);
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
