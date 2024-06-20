package org.example.servlet.admin;

import org.example.bean.Table;
import org.example.bean.User;
import org.example.service.TableService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/table")
public class TableServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }


        TableService tableService = new TableService();
        List<Table> tableList = tableService.getAllTablesByRestaurantId(user.getRestaurantId());

        req.setAttribute("tableList", tableList);
        req.getRequestDispatcher("/admin/table.jsp").forward(req, resp);
    }

}
