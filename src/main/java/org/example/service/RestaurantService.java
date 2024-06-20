package org.example.service;

import org.example.bean.Restaurant;
import org.example.dao.RestaurantDao;

import java.util.List;

public class RestaurantService {
    private final RestaurantDao restaurantDao = new RestaurantDao();

    public List<Restaurant> getAllRestaurants() {
        return restaurantDao.getAllRestaurants();
    }
}
