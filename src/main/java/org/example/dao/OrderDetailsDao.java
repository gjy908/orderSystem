package org.example.dao;

import org.example.temp.OrderDetails;
import org.example.temp.OrderDetails.*;
import org.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailsDao {


    // 查询uid为x的用户的最近一次orders.status=0的OrderDetails信息
    public OrderDetails findLatestOrderDetailsByUserIdAndStatus(long userId) throws SQLException {
        OrderDetails orderDetails = null;
        Order order = null;
        List<OrderItemDetails> orderItems = new ArrayList<>();

        // 步骤一：查询最近一次的订单信息（status=0）
        String orderSql = "SELECT o.order_id, o.table_id, o.restaurant_id, o.user_id, o.status, o.created_at, o.updated_at " +
                "FROM orders o " +
                "WHERE o.user_id = ? AND o.status = ? " +
                "ORDER BY o.created_at DESC " +
                "LIMIT 1";

        // 步骤二：根据订单信息查询订单项信息
        String orderItemSql = "SELECT oi.order_item_id, oi.order_id, oi.menu_id, oi.quantity, oi.status AS itemStatus, oi.created_at AS itemCreatedAt, oi.updated_at AS itemUpdatedAt " +
                "FROM order_items oi " +
                "WHERE oi.order_id = ?";

        // 步骤三：根据订单项信息查询菜单信息
        String menuSql = "SELECT m.menu_id, m.restaurant_id, m.name AS menuName, m.description AS menuDescription, m.price AS menuPrice, m.image_url AS menuImageUrl, m.status AS menuStatus, m.commend AS menuCommend, m.created_at AS menuCreatedAt, m.updated_at AS menuUpdatedAt " +
                "FROM menu m " +
                "WHERE m.menu_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement orderStmt = conn.prepareStatement(orderSql);
             PreparedStatement orderItemStmt = conn.prepareStatement(orderItemSql);
             PreparedStatement menuStmt = conn.prepareStatement(menuSql)) {

            // 执行步骤一查询
            orderStmt.setLong(1, userId);
            orderStmt.setByte(2, (byte) 0);
            try (ResultSet orderRs = orderStmt.executeQuery()) {
                if (orderRs.next()) {
                    orderDetails = new OrderDetails();
                    order = mapOrder(orderRs);
                    orderDetails.setOrder(order);

                    // 执行步骤二查询
                    orderItemStmt.setLong(1, order.getOrderId());
                    try (ResultSet orderItemRs = orderItemStmt.executeQuery()) {
                        while (orderItemRs.next()) {
                            OrderItemDetails orderItemDetails = new OrderItemDetails();
                            OrderItem orderItem = mapOrderItem(orderItemRs);

                            // 执行步骤三查询
                            menuStmt.setLong(1, orderItem.getMenuId());
                            try (ResultSet menuRs = menuStmt.executeQuery()) {
                                if (menuRs.next()) {
                                    Menu menu = mapMenu(menuRs);
                                    orderItemDetails.setOrderItem(orderItem);
                                    orderItemDetails.setMenu(menu);
                                }
                            }

                            orderItems.add(orderItemDetails);
                        }
                    }

                    // 设置订单项列表到 OrderDetails 对象
                    orderDetails.setOrderItems(orderItems);
                }
            }
        }

        return orderDetails;
    }

    // 将查询结果映射为Order对象
    private Order mapOrder(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setOrderId(rs.getLong("order_id"));
        order.setTableId(rs.getLong("table_id"));
        order.setRestaurantId(rs.getLong("restaurant_id"));
        order.setUserId(rs.getLong("user_id"));
        order.setStatus(rs.getByte("status"));
        order.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
        order.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
        return order;
    }

    // 将查询结果映射为OrderItem对象
    private OrderItem mapOrderItem(ResultSet rs) throws SQLException {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrderItemId(rs.getLong("order_item_id"));
        orderItem.setOrderId(rs.getLong("order_id"));
        orderItem.setMenuId(rs.getLong("menu_id"));
        orderItem.setQuantity(rs.getInt("quantity"));
        orderItem.setStatus(rs.getByte("itemStatus"));
        orderItem.setCreatedAt(rs.getTimestamp("itemCreatedAt").toLocalDateTime());
        orderItem.setUpdatedAt(rs.getTimestamp("itemUpdatedAt").toLocalDateTime());
        return orderItem;
    }

    // 将查询结果映射为Menu对象
    private Menu mapMenu(ResultSet rs) throws SQLException {
        Menu menu = new Menu();
        menu.setMenuId(rs.getLong("menu_id"));
        menu.setRestaurantId(rs.getLong("restaurant_id"));
        menu.setName(rs.getString("menuName"));
        menu.setDescription(rs.getString("menuDescription"));
        menu.setPrice(rs.getBigDecimal("menuPrice"));
        menu.setImageUrl(rs.getString("menuImageUrl"));
        menu.setStatus(rs.getByte("menuStatus"));
        menu.setCommend(rs.getByte("menuCommend"));
        menu.setCreatedAt(rs.getTimestamp("menuCreatedAt").toLocalDateTime());
        menu.setUpdatedAt(rs.getTimestamp("menuUpdatedAt").toLocalDateTime());
        return menu;
    }
}
