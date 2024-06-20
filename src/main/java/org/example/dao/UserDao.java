package org.example.dao;

import org.example.bean.User;
import org.example.utils.DBUtil;

import java.sql.ResultSet;

public class UserDao {
//    登录
    public User login(String email, String password) {


        String sql = "select * from users where email = ? and password = ?";

        ResultSet resultSet = DBUtil.executeQuery(sql, email, password);
        if(resultSet == null) {
            return null;
        }
        User user = null;
        try {
            if(resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getLong("user_id"));
                user.setRestaurantId(resultSet.getLong("restaurant_id"));
                user.setName(resultSet.getString("name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
                user.setRole(resultSet.getByte("role"));
                user.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                user.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, null, resultSet);
        }
        return user;

    }

    public static void main(String[] args) {
        UserDao userDao = new UserDao();

        User user = userDao.login("zhangsan@example.com", "password123");
        System.out.println(user);
    }





}
