package org.example.servlet.cook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bean.Menu;
import org.example.bean.User;
import org.example.service.MenuService;
import org.example.vo.MyResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cook/menu")
public class menuServlet extends HttpServlet {
    MenuService menuService = new MenuService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        List<Menu> menuList = menuService.getMenuListByRestaurantId(user.getRestaurantId());
        req.setAttribute("menuList", menuList);

        req.getRequestDispatcher("/cook/menu.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求和响应的字符编码
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        ObjectMapper mapper = new ObjectMapper();
        MyResponse myResponse = new MyResponse();
        try {

            // 获取请求参数
            String menuId = req.getParameter("menuId");
            String status = req.getParameter("status");

            System.out.println("menuId = " + menuId);
            boolean success = menuService.updateMenuStatus(Long.valueOf(menuId), Byte.valueOf(status));

            if (success) {
                myResponse.setSuccess(true);
                myResponse.setMessage("更新成功");
            } else {
                myResponse.setSuccess(false);
                myResponse.setMessage("更新失败");
            }
        } catch (Exception e) {
            myResponse.setSuccess(false);
            myResponse.setMessage("更新失败");
        } finally {
            // 将响应数据转换为 JSON 格式
            String json = mapper.writeValueAsString(myResponse);
            // 将 JSON 数据写入响应
            resp.getWriter().write(json);
        }
    }
}
