package org.example.servlet.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bean.Table;
import org.example.bean.User;
import org.example.dao.OrderItemDao;
import org.example.service.OrderingService;
import org.example.service.TableService;
import org.example.temp.OrderDetails;
import org.example.vo.MyResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/admin/ordering")
public class OrdingServlet extends HttpServlet {

    OrderingService orderingService = new OrderingService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }
        List<OrderDetails> orderDetailsList = orderingService.findOrderDetailsByRestaurantIdAndStatus(user.getRestaurantId());

        System.out.println("orderDetailsList = " + orderDetailsList);
        req.setAttribute("orderDetailsList", orderDetailsList);
        req.getRequestDispatcher("/admin/ordering.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        resp.setContentType("application/json;charset=utf-8");

        OrderItemDao orderItemDao = new OrderItemDao();
        MyResponse myResponse = new MyResponse();

        try {

            String orderId = req.getParameter("orderId");
            String orderItemId = req.getParameter("orderItemId");
            Byte status = Byte.parseByte(req.getParameter("status"));

            boolean success = orderItemDao.updateOrderItemStatus(Long.parseLong(orderItemId), status);

            if (success) {
                myResponse.setSuccess(true);
                myResponse.setMessage("更新成功");
            } else {
                myResponse.setSuccess(false);
                myResponse.setMessage("更新失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(myResponse);
        resp.getWriter().write(s);

    }
}
