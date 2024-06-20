package org.example.dao;

import org.example.bean.Menu;
import org.example.bean.Order;
import org.example.bean.OrderItem;
import org.example.utils.DBUtil;
import org.example.vo.OrderWithItems;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    public List<Order> getOrdersByRestaurantId(long restaurantId) {
        List<Order> orders = new ArrayList<>();

        String orderQuery = "SELECT * FROM orders WHERE restaurant_id = ? AND status = 0";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement orderStmt = conn.prepareStatement(orderQuery)) {

            orderStmt.setLong(1, restaurantId);
            ResultSet orderRs = orderStmt.executeQuery();

            while (orderRs.next()) {
                Order order = new Order(
                        orderRs.getLong("order_id"),
                        orderRs.getLong("table_id"),
                        orderRs.getLong("restaurant_id"),
                        orderRs.getLong("user_id"),
                        orderRs.getByte("status"),
                        orderRs.getTimestamp("created_at").toLocalDateTime(),
                        orderRs.getTimestamp("updated_at").toLocalDateTime()
                );
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }


}
