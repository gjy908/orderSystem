package org.example.dao;

import org.example.bean.Revenue;
import org.example.utils.DBUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TongjiDao {

    public List<Revenue> getAllRevenueByRestaurantId(Long RestaurantId) {

        String sql = "SELECT * FROM revenue WHERE restaurant_id = ? and  is_deleted = 0 order by update_time desc;";


        /**
         *    private Long id;
         *     private Long restaurantId;
         *     private LocalDate date;
         *     private Double amount;
         *     private LocalDateTime createTime;
         *     private LocalDateTime updateTime;
         *     private Boolean isDeleted;
         */

        ResultSet resultSet = DBUtil.executeQuery(sql, RestaurantId);
        List<Revenue> revenueList = new ArrayList<>();
        try(ResultSet rs = DBUtil.executeQuery(sql, RestaurantId)){
            while (rs.next()) {
                Revenue revenue = new Revenue();
                revenue.setId(rs.getLong("id"));
                revenue.setRestaurantId(rs.getLong("restaurant_id"));
                revenue.setDate(rs.getDate("date").toLocalDate());
                revenue.setAmount(rs.getDouble("amount"));
                revenue.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
                revenue.setUpdateTime(rs.getTimestamp("update_time").toLocalDateTime());
                revenue.setIsDeleted(rs.getBoolean("is_deleted"));
                revenueList.add(revenue);
            }

        } catch (SQLException e) {
                throw new RuntimeException(e);
        }


        return revenueList;

        }

    public static void main(String[] args) {
        TongjiDao tongjiDao = new TongjiDao();
        List<Revenue> revenueList = tongjiDao.getAllRevenueByRestaurantId(1L);
        for (Revenue revenue : revenueList) {
            System.out.println(revenue);
        }
    }

}
