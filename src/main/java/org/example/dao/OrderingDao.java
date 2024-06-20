package org.example.dao;

import org.example.bean.Order;
import org.example.temp.OrderDetails;
import org.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderingDao {

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
                order.setStatus(resultSet.getByte("status"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return order;
    }


    //    创建订单
    public boolean createOrder(Long uId, Long tId, Long rId) {
        String sql = "INSERT INTO orders (user_id, table_id, restaurant_id) VALUES (?, ?, ?)";
        return DBUtil.executeUpdate(sql, uId, tId, rId) > 0;
    }


    public boolean updateOrderDetails(Long orderItemId, Byte status) {
        String sql = "UPDATE order_items SET status = ? WHERE order_item_id = ?";
        return DBUtil.executeUpdate(sql, status, orderItemId) > 0;
    }


    // 查询restaurant_id为x的所有订单的OrderDetails信息列表
    public List<OrderDetails> findOrderDetailsByRestaurantIdAndStatus(long restaurantId) throws SQLException {
        List<OrderDetails> orderDetailsList = new ArrayList<>();

        // 步骤一：查询restaurant_id为x且status=0的订单信息
        String orderSql = "SELECT o.order_id, o.table_id, o.restaurant_id, o.user_id, o.status, o.created_at, o.updated_at " +
                "FROM orders o " +
                "WHERE o.restaurant_id = ? AND o.status = ? " +
                "ORDER BY o.created_at DESC";

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
            orderStmt.setLong(1, restaurantId);
            orderStmt.setByte(2, (byte) 0);  // 这里保留status=0的筛选条件
            try (ResultSet orderRs = orderStmt.executeQuery()) {
                while (orderRs.next()) {
                    OrderDetails orderDetails = new OrderDetails();
                    OrderDetails.Order order = mapOrder(orderRs);
                    orderDetails.setOrder(order);

                    // 执行步骤二查询
                    orderItemStmt.setLong(1, order.getOrderId());
                    try (ResultSet orderItemRs = orderItemStmt.executeQuery()) {
                        List<OrderDetails.OrderItemDetails> orderItems = new ArrayList<>();
                        while (orderItemRs.next()) {
                            OrderDetails.OrderItemDetails orderItemDetails = new OrderDetails.OrderItemDetails();
                            OrderDetails.OrderItem orderItem = mapOrderItem(orderItemRs);

                            // 执行步骤三查询
                            menuStmt.setLong(1, orderItem.getMenuId());
                            try (ResultSet menuRs = menuStmt.executeQuery()) {
                                if (menuRs.next()) {
                                    OrderDetails.Menu menu = mapMenu(menuRs);
                                    orderItemDetails.setOrderItem(orderItem);
                                    orderItemDetails.setMenu(menu);
                                }
                            }

                            orderItems.add(orderItemDetails);
                        }
                        orderDetails.setOrderItems(orderItems);
                    }

                    orderDetailsList.add(orderDetails);
                }
            }
        }

        return orderDetailsList;
    }


    // 将查询结果映射为Order对象
    private OrderDetails.Order mapOrder(ResultSet rs) throws SQLException {
        OrderDetails.Order order = new OrderDetails.Order();
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
    private OrderDetails.OrderItem mapOrderItem(ResultSet rs) throws SQLException {
        OrderDetails.OrderItem orderItem = new OrderDetails.OrderItem();
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
    private OrderDetails.Menu mapMenu(ResultSet rs) throws SQLException {
        OrderDetails.Menu menu = new OrderDetails.Menu();
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


    public List<Order> getOrderingByRestaurantId(Long RestaurantId) {
        List<Order> orderList = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE status = 0 AND restaurant_id = ? ORDER BY created_at DESC";
        ResultSet resultSet = DBUtil.executeQuery(sql, RestaurantId);
        try {
            while (resultSet.next()) {
                Order order = new Order();
                order.setOrderId(resultSet.getLong("order_id"));
                order.setTableId(resultSet.getLong("table_id"));
                order.setRestaurantId(resultSet.getLong("restaurant_id"));
                order.setUserId(resultSet.getLong("user_id"));
                order.setStatus(resultSet.getByte("status"));
                order.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                order.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                orderList.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderList;

    }




    public static void main(String[] args) {

    }


}
