package org.example.servlet.admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin/checkoutOrder")
public class checkoutOrderServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    OrderService orderService = new OrderService();
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String orderId = request.getParameter("orderId");

        Map<String, Object> responseMap = new HashMap<>();
        try {
            // 调用服务层的方法来处理订单结算
            boolean success = orderService.checkoutOrder(Long.valueOf(orderId));
            if (success) {
                responseMap.put("success", true);
            } else {
                responseMap.put("success", false);
                responseMap.put("message", "结算失败，可能是因为订单状态不允许结算或其他原因");
            }
        } catch (Exception e) {
            responseMap.put("success", false);
            responseMap.put("message", "结算失败：" + e.getMessage());
        }

        // 设置响应类型为JSON
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // 将结果写入响应
        String jsonResponse = objectMapper.writeValueAsString(responseMap);
        response.getWriter().write(jsonResponse);
    }




}
