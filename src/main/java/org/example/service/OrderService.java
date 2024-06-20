package org.example.service;

import org.example.dao.OrderItemDao;
import org.example.utils.DBUtil;

public class OrderService {
    private final OrderItemDao orderItemDao = new OrderItemDao();

    public void addOrderItem(Long uId, Long menuId, Long tId, Long rId, Integer quantity) {
        orderItemDao.createOrderItem(uId, menuId, tId, rId, quantity);
    }


    public boolean checkoutOrder(Long orderId) {

        String sql="update orders set status=1 where order_id = ? ;";
        int i = DBUtil.executeUpdate(sql, orderId);
        return i>0;
    }

    public static void main(String[] args) {
        OrderService orderService = new OrderService();
        orderService.addOrderItem(50L, 1L, 1L, 7L, 2);
    }
}
