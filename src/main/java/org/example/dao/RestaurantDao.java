package org.example.dao;

import org.example.bean.Restaurant;
import org.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RestaurantDao {

    //    获取所有餐馆
    public List<Restaurant> getAllRestaurants() {

        String sql = "select * from restaurants order by rating desc, restaurant_id asc";
        ResultSet resultSet = DBUtil.executeQuery(sql);
        List<Restaurant> restaurantList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Restaurant restaurant = new Restaurant();
                restaurant.setRestaurantId(resultSet.getLong("restaurant_id"));
                restaurant.setName(resultSet.getString("name"));
                restaurant.setLocation(resultSet.getString("location"));
                restaurant.setPhone(resultSet.getString("phone"));
                restaurant.setImageUrl(resultSet.getString("image_url"));
                restaurant.setRating(resultSet.getInt("rating"));
                restaurant.setDescription(resultSet.getString("description"));
                restaurant.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                restaurant.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                restaurantList.add(restaurant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, null, resultSet);
        }
        return restaurantList;


    }


}
