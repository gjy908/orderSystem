package org.example.servlet.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bean.User;
import org.example.service.OrderingService;
import org.example.temp.OrderDetails;
import org.example.vo.MyResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/customer/ordering")
public class OrderingServlet extends HttpServlet {
   private final OrderingService orderingService = new OrderingService();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();

        // 获取请求中的参数
        String orderItemIdStr = req.getParameter("orderItemId");

        Long orderItemId = Long.parseLong(orderItemIdStr);

        PrintWriter out = resp.getWriter();

        MyResponse myResponse = new MyResponse();

        try {


            boolean success = orderingService.updateOrderItemStatus(orderItemId);
            if (success) {
                myResponse.setSuccess(true);
                myResponse.setMessage("退菜成功");
            } else {
                myResponse.setSuccess(false);
                myResponse.setMessage("退菜失败");
            }

        } catch (Exception e) {

            e.printStackTrace();
            // 异常处理，返回JSON格式的响应

        } finally {
            out.write(mapper.writeValueAsString(myResponse));
            out.flush();
            out.close();
        }

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        OrderDetails details = orderingService.getOrderDetails(user.getUserId());
        req.setAttribute("orderDetails",details );
        req.getRequestDispatcher("/customer/ordering.jsp").forward(req, resp);

    }
}
