package org.example.service;

import org.example.bean.Ingredient;
import org.example.dao.IngredientDao;

import java.util.List;

public class IngredientService {
    private IngredientDao ingredientDao = new IngredientDao();

    public List<Ingredient> getIngredientsByRestaurantId(Long restaurantId) {
        return ingredientDao.getIngredientsByRestaurantId(restaurantId);
    }
    public  boolean updateIngredientQuantity(Long ingredientId, int quantity) {
        return ingredientDao.updateIngredientQuantity(ingredientId, quantity);
    }
}
