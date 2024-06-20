package org.example.servlet.customer;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bean.Menu;
import org.example.service.MenuService;
import org.example.temp.OrderItemData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/menu")
public class MenuServlet extends HttpServlet {
    private final MenuService menuService = new MenuService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long rId;
        Long tId;
        System.out.println("res.getP = " + req.getParameter("rId"));
        System.out.println("res.getP = " + req.getParameter("tId"));

        try {
            rId = Long.valueOf(req.getParameter("rId"));
             tId = Long.valueOf(req.getParameter("tId"));
        } catch (Exception e) {
            resp.sendRedirect(req.getContextPath() + "/customer/restaurant");
            return;
        }

        List<Menu> menuList = menuService.getMenuListByRestaurantId(rId);

        req.setAttribute("rId", rId);
        req.setAttribute("tId", tId);
        req.setAttribute("menuList", menuList);
        req.getRequestDispatcher("/customer/menu.jsp").forward(req, resp);

    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {





    }
}
