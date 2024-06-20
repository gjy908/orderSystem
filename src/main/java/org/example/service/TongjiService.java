package org.example.service;

import org.example.bean.Revenue;
import org.example.dao.TongjiDao;

import java.util.List;

public class TongjiService {

    private final TongjiDao tongjiDao = new TongjiDao();

    //    统计营业额
    public List<Revenue> getAllRevenueByRestaurantId(Long RestaurantId) {
        List<Revenue> allRevenueByRestaurantId = tongjiDao.getAllRevenueByRestaurantId(RestaurantId);
        return allRevenueByRestaurantId;

    }


}
