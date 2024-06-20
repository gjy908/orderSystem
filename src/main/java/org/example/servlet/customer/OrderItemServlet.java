package org.example.servlet.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bean.User;
import org.example.service.OrderService;
import org.example.temp.OrderItemData;
import org.example.vo.MyResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
@WebServlet("/customer/order")
public class OrderItemServlet extends HttpServlet {

    private final OrderService orderService = new OrderService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");

        ObjectMapper mapper = new ObjectMapper();

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        // 从请求中读取JSON数据
        StringBuilder sb = new StringBuilder();

        MyResponse myResponse = new MyResponse();
        try (BufferedReader reader = req.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 如果读取失败，返回400错误
            myResponse.setMessage("Bad Request: Unable to read input");
            myResponse.setSuccess(false);
            sendResponse(resp, myResponse, mapper);
            return;
        }

        // 使用Jackson将JSON字符串反序列化为Java对象
        OrderItemData orderItemData;
        try {
            orderItemData = mapper.readValue(sb.toString(), OrderItemData.class);
            System.out.println("orderItemData = " + orderItemData.toString());
        } catch (IOException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 如果反序列化失败，返回400错误
            myResponse.setMessage("Bad Request: Invalid JSON format");
            myResponse.setSuccess(false);
            sendResponse(resp, myResponse, mapper);
            return;
        }


        try {
            orderService.addOrderItem(user.getUserId(), orderItemData.getMenuId(), orderItemData.gettId(), orderItemData.getrId(), orderItemData.getQuantity());

            resp.setStatus(HttpServletResponse.SC_CREATED); // 添加成功返回201
            myResponse.setMessage("Order added successfully");
            myResponse.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR); // 服务器内部错误返回500
            myResponse.setMessage("Internal Server Error: Unable to add order item");
            myResponse.setSuccess(false);
        }

        sendResponse(resp, myResponse, mapper);
    }

    private void sendResponse(HttpServletResponse resp, MyResponse myResponse, ObjectMapper mapper) throws IOException {
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(mapper.writeValueAsString(myResponse));
        }
    }
}
