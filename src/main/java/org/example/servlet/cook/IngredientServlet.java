package org.example.servlet.cook;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.bean.Ingredient;
import org.example.bean.User;
import org.example.service.IngredientService;
import org.example.vo.MyResponse;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cook/ingredient")
public class IngredientServlet extends HttpServlet {

    private final IngredientService ingredientService = new IngredientService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        User user = (User) req.getSession().getAttribute("user");
        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        List<Ingredient> ingredientList = ingredientService.getIngredientsByRestaurantId(user.getRestaurantId());
        req.setAttribute("ingredientList", ingredientList);
        req.getRequestDispatcher("/cook/ingredient.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        MyResponse myResponse = new MyResponse();
        ObjectMapper mapper = new ObjectMapper();

        try {
            String ingredientId = req.getParameter("ingredientId");
            String quantity = req.getParameter("quantity");
            boolean success = ingredientService.updateIngredientQuantity(Long.valueOf(ingredientId), Integer.valueOf(quantity));

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
        }

        resp.getWriter().write(mapper.writeValueAsString(myResponse));
    }
}
