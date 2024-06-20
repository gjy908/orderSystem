package org.example.servlet.admin;

import org.example.bean.Revenue;
import org.example.bean.User;
import org.example.service.TongjiService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/tongji")
public class TongjiServlet extends HttpServlet {

    private final TongjiService tongjiService = new TongjiService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }


        List<Revenue> revenueList = tongjiService.getAllRevenueByRestaurantId(user.getRestaurantId());

        for (Revenue revenue : revenueList) {
            System.out.println(revenue.getIsDeleted());
        }
        req.setAttribute("revenueList", revenueList);

        req.getRequestDispatcher("/admin/tongji.jsp").forward(req, resp);

    }


}
