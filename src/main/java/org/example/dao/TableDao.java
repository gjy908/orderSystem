package org.example.dao;

import org.example.bean.Table;
import org.example.utils.DBUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TableDao {


//    通过restaurantId获取所有的桌子
    public List<Table> getAllTablesByRestaurantId(Long restaurantId) {
        String sql = "select * from tables where restaurant_id = ?";
        ResultSet resultSet = DBUtil.executeQuery(sql, restaurantId);
        if(resultSet == null) {
            return null;
        }
        List<Table> tableList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Table table = new Table();
                table.setTableId(resultSet.getLong("table_id"));
                table.setRestaurantId(resultSet.getLong("restaurant_id"));
                table.setTableNumber(resultSet.getString("table_number"));
                table.setStatus(resultSet.getByte("status"));
                table.setQrCode(resultSet.getString("qr_code"));
                table.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                table.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                tableList.add(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DBUtil.close(null, null, resultSet);
        }
        return tableList;
    }

//    更新桌子状态
  public boolean  updateTableStatus(Long tableId, Byte status) {
        String sql = "update tables set status = ? where table_id = ?";
      int i = DBUtil.executeUpdate(sql, status, tableId);
        return i > 0;
  }


}
