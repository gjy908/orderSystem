package org.example.dao;

import org.example.bean.Ingredient;
import org.example.utils.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IngredientDao {

    public List<Ingredient> getIngredientsByRestaurantId(Long restaurantId) {

        List<Ingredient> ingredients = new ArrayList<>();
        String sql = "SELECT * FROM ingredients WHERE restaurant_id = ?";


        try (ResultSet rs = DBUtil.executeQuery(sql, restaurantId)) {
            while (rs.next()) {
                Ingredient ingredient = new Ingredient();
                ingredient.setIngredientId(rs.getLong("ingredient_id"));
                ingredient.setRestaurantId(rs.getLong("restaurant_id"));
                ingredient.setName(rs.getString("name"));
                ingredient.setQuantity(rs.getInt("quantity"));
                ingredient.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                ingredient.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());
                ingredients.add(ingredient);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return ingredients;
    }

   public boolean updateIngredientQuantity(Long ingredientId, int quantity) {
        String sql = "UPDATE ingredients SET quantity = ? WHERE ingredient_id = ?";
        return DBUtil.executeUpdate(sql, quantity, ingredientId) > 0;
    }

}
