package org.example.dao;

import org.example.bean.Ingredient;
import org.example.bean.Menu;
import org.example.utils.DBUtil;
import org.example.vo.MenuWithIngredient;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MenuDao {
    public List<Menu> getAllMenusByRestaurantId(Long restaurantId) {

        String sql = "select * from menu where restaurant_id = ? order by commend desc;";
        ResultSet resultSet = DBUtil.executeQuery(sql, restaurantId);
        List<Menu> menuList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Menu menu = new Menu();
                menu.setMenuId(resultSet.getLong("menu_id"));
                menu.setRestaurantId(resultSet.getLong("restaurant_id"));
                menu.setName(resultSet.getString("name"));
                menu.setDescription(resultSet.getString("description"));
                menu.setPrice(resultSet.getBigDecimal("price"));
                menu.setImageUrl(resultSet.getString("image_url"));
                menu.setStatus(resultSet.getByte("status"));
                menu.setCommend(resultSet.getByte("commend"));
                menu.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                menu.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                menuList.add(menu);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return menuList;
    }


    public Menu getMenuById(long menuId) {
        Menu menu = null;

        String menuQuery = "SELECT * FROM menu WHERE menu_id = ?";

        try (Connection conn = DBUtil.getConnection();
             PreparedStatement menuStmt = conn.prepareStatement(menuQuery)) {

            menuStmt.setLong(1, menuId);
            ResultSet menuRs = menuStmt.executeQuery();
            if (menuRs.next()) {
                menu = new Menu(
                        menuRs.getLong("menu_id"),
                        menuRs.getLong("restaurant_id"),
                        menuRs.getString("name"),
                        menuRs.getString("description"),
                        menuRs.getBigDecimal("price"),
                        menuRs.getString("image_url"),
                        menuRs.getByte("status"),
                        menuRs.getByte("commend"),
                        menuRs.getTimestamp("created_at").toLocalDateTime(),
                        menuRs.getTimestamp("updated_at").toLocalDateTime()
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return menu;
    }


    public List<Menu> getMenuListByRestaurantId(Long restaurantId) {

        String sql = "select * from menu where restaurant_id = ?;";
        ResultSet resultSet = DBUtil.executeQuery(sql, restaurantId);
        List<Menu> menuList = new ArrayList<>();

        try {
            while (resultSet.next()) {
                Menu menu = new Menu();
                menu.setMenuId(resultSet.getLong("menu_id"));
                menu.setRestaurantId(resultSet.getLong("restaurant_id"));
                menu.setName(resultSet.getString("name"));
                menu.setDescription(resultSet.getString("description"));
                menu.setPrice(resultSet.getBigDecimal("price"));
                menu.setImageUrl(resultSet.getString("image_url"));
                menu.setStatus(resultSet.getByte("status"));
                menu.setCommend(resultSet.getByte("commend"));
                menu.setCreatedAt(resultSet.getTimestamp("created_at").toLocalDateTime());
                menu.setUpdatedAt(resultSet.getTimestamp("updated_at").toLocalDateTime());
                menuList.add(menu);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return menuList;
    }




    public List<Ingredient> getIngredientListByMenuId(Long menuId) {
        String sql = "select * from ingredient where menu_id = ?;";
        ResultSet resultSet = DBUtil.executeQuery(sql, menuId);
        List<Ingredient> ingredientList = new ArrayList<>();

        return null;
    }

    public List<MenuWithIngredient> getMenuWithIngredientListByRestaurantId(Long restaurantId) {
        List<Menu> menuList = getMenuListByRestaurantId(restaurantId);
        List<MenuWithIngredient> menuWithIngredientList = new ArrayList<>();
        for (Menu menu : menuList) {

        }
        return null;
    }


    public boolean updateMenuStatus(Long mId,Byte status) {
        String sql = "update menu set status = ?  where menu_id = ?;";
        int result = DBUtil.executeUpdate(sql, status, mId);
        return result > 0;
    }


    public static void main(String[] args) {
        MenuDao menuDao = new MenuDao();
        List<Menu> menuList = menuDao.getAllMenusByRestaurantId(6L);
        System.out.println(menuList);
    }
}
