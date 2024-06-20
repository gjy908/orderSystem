package org.example.servlet.customer;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bean.Table;
import org.example.service.TableService;
import org.example.vo.MyResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/customer/table")
public class TableServlet extends HttpServlet {
    private final TableService tableService = new TableService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String rId = req.getParameter("rId");
        System.out.println("rId = " + rId);
        if (rId == null) {
            resp.sendRedirect(req.getContextPath() + "/customer/restaurant");
            return;
        }
        List<Table> tableList = tableService.getAllTablesByRestaurantId(Long.valueOf(rId));
        req.setAttribute("tableList", tableList);
        req.getRequestDispatcher("/customer/table.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        String tableId = req.getParameter("tableId");

        PrintWriter out = resp.getWriter();

        // 创建响应对象
        MyResponse myResponse = new MyResponse();
        try {
            // 实现预订逻辑
            boolean isReserved = tableService.updateTableStatus(Long.parseLong(tableId), (byte) 2);
            if (isReserved) {
                myResponse.setSuccess(true);
                myResponse.setMessage("预订成功");
            } else {
                myResponse.setSuccess(false);
                myResponse.setMessage("预订失败");
            }
        } catch (Exception e) {
            myResponse.setSuccess(false);
            myResponse.setMessage("预订失败: " + e.getMessage());
        }

        // 使用 Jackson 序列化响应对象为 JSON
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(myResponse);
        out.print(jsonResponse);
        out.flush();

    }
}
