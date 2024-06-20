package org.example.dao;

import org.example.bean.Order;
import org.example.bean.OrderItem;
import org.example.utils.DBUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDao {




    //    查询最近正在进行的一个订单
    public Order getRecentOrderByUId(Long uId) {

        String sql = "SELECT * FROM orders WHERE user_id = ? AND status = 0 ORDER BY created_at DESC LIMIT 1";
        ResultSet resultSet = DBUtil.executeQuery(sql, uId);
        Order order = null;
        try {
            if (resultSet.next()) {
                order = new Order();
                order.setOrderId(resultSet.getLong("order_id"));
                order.setUserId(resultSet.getLong("user_id"));
                order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                order.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                order.setStatus( resultSet.getByte("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }


    //    创建订单
    public boolean createOrder(Long uId, Long tId, Long rId) {
        String sql = "INSERT INTO orders (user_id, table_id, restaurant_id) VALUES (?, ?, ?)";
        return DBUtil.executeUpdate(sql, uId, tId,rId) > 0;
    }

    public boolean addOrderItem(Long orderId, Long menuId, Integer quantity) {

        String sql = "INSERT INTO order_items (order_id, menu_id, quantity) VALUES (?, ?, ?)";
        return DBUtil.executeUpdate(sql, orderId, menuId, quantity)>1;
    }


    public boolean createOrderItem(Long uId, Long menuId, Long tId,Long rId,Integer quantity) {


        Order order = getRecentOrderByUId(uId);
        if (order == null) {
         createOrder(uId, tId, rId);
        }
        order = getRecentOrderByUId(uId);

        return addOrderItem(order.getOrderId(), menuId, quantity);
    }

    public List<OrderItem> getOrderItemsByOrderId(long orderId) {
        List<OrderItem> orderItems = new ArrayList<>();

        String orderItemsQuery = "SELECT * FROM order_items WHERE order_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement orderItemsStmt = conn.prepareStatement(orderItemsQuery)) {

            orderItemsStmt.setLong(1, orderId);
            ResultSet orderItemsRs = orderItemsStmt.executeQuery();

            while (orderItemsRs.next()) {
                OrderItem orderItem = new OrderItem(
                        orderItemsRs.getLong("order_item_id"),
                        orderItemsRs.getLong("order_id"),
                        orderItemsRs.getLong("menu_id"),
                        orderItemsRs.getInt("quantity"),
                        orderItemsRs.getByte("status"),
                        orderItemsRs.getTimestamp("created_at").toLocalDateTime(),
                        orderItemsRs.getTimestamp("updated_at").toLocalDateTime()
                );
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orderItems;
    }

    public boolean updateOrderItemStatus(Long orderItemId, Byte status) {
        String sql = "UPDATE order_items SET status = ? WHERE order_item_id = ?";
        return DBUtil.executeUpdate(sql, status, orderItemId) > 0;
    }

}
