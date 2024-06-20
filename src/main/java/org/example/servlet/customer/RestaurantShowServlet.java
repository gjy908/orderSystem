package org.example.servlet.customer;

import org.example.bean.Restaurant;
import org.example.service.RestaurantService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/customer/restaurant")
public class RestaurantShowServlet extends HttpServlet {

    RestaurantService restaurantService = new RestaurantService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Restaurant> restaurantList = restaurantService.getAllRestaurants();
        req.setAttribute("restaurantList", restaurantList);
        req.getRequestDispatcher("/customer/restaurant.jsp").forward(req, resp);

    }
}
