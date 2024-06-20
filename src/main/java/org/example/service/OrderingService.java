package org.example.service;

import org.example.bean.Order;
import org.example.dao.OrderDetailsDao;
import org.example.dao.OrderingDao;
import org.example.temp.OrderDetails;

import java.sql.SQLException;
import java.util.List;

public class OrderingService {
    private final OrderDetailsDao orderDetailsDao = new OrderDetailsDao();
    private final OrderingDao orderDao = new OrderingDao();


    public OrderDetails getOrderDetails(Long uId) {
        try {
            return orderDetailsDao.findLatestOrderDetailsByUserIdAndStatus(uId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean updateOrderItemStatus(Long orderItemId) {
        return orderDao.updateOrderDetails(orderItemId, (byte) 2);
    }

    public List<OrderDetails> findOrderDetailsByRestaurantIdAndStatus(long restaurantId) {
        try {
            return orderDao.findOrderDetailsByRestaurantIdAndStatus(restaurantId);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Order> getOrderingByRestaurantId(Long restaurantId) {
        return orderDao.getOrderingByRestaurantId(restaurantId);
    }

    public static void main(String[] args) {
        OrderingService orderingService = new OrderingService();
        for (int i = 0; i < 1000; i++) {
            List<OrderDetails> orderDetailsList = orderingService.findOrderDetailsByRestaurantIdAndStatus(5L);
            for (OrderDetails orderDetails : orderDetailsList) {
                System.out.println(orderDetails.getOrder().getOrderId());
            }
        }
    }
}
