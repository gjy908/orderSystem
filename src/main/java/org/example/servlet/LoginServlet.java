package org.example.servlet;

import org.example.bean.Role;
import org.example.bean.User;
import org.example.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final UserService userService = new UserService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");

        req.setCharacterEncoding("UTF-8");

        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        System.out.println("password = " + password);
        System.out.println("email = " + email);
        User user = userService.login(email, password);
        // 角色: 0-顾客, 1-厨师,2-餐馆管理员

        if (user == null) {
            req.setAttribute("message", "用户名或密码错误");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }
        System.out.println("user = " + user.getEmail());
        req.getSession().setAttribute("user", user);

        switch (Role.fromByte(user.getRole())) {
            case CUSTOMER:
               resp.sendRedirect("/customer/restaurant");
                break;
            case COOK:
                resp.sendRedirect("/cook/ordering");
                break;
            case ADMIN:
                resp.sendRedirect("/admin/ordering");
                break;
            default:
                req.getRequestDispatcher("/login").forward(req, resp);
                break;
        }






    }
}
//顾客