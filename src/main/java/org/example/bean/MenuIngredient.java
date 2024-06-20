package org.example.bean;

import java.time.LocalDateTime;

public class MenuIngredient {
    private Long menuIngredientId;
    private Long menuId;
    private Long ingredientId;
    private Integer quantity;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MenuIngredient() {
    }

    public MenuIngredient(Long menuIngredientId, Long menuId, Long ingredientId, Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.menuIngredientId = menuIngredientId;
        this.menuId = menuId;
        this.ingredientId = ingredientId;
        this.quantity = quantity;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getMenuIngredientId() {
        return menuIngredientId;
    }

    public void setMenuIngredientId(Long menuIngredientId) {
        this.menuIngredientId = menuIngredientId;
    }

    public Long getMenuId() {
        return menuId;
    }

    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}