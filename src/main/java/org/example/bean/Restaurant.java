package org.example.bean;

import java.time.LocalDateTime;

public class Restaurant {
    private Long restaurantId;
    private String name;
    private String location;
    private String phone;
    private String imageUrl;
    private Integer rating;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Restaurant() {
    }

    public Restaurant(Long restaurantId, String name, String location, String phone, String imageUrl, Integer rating,String description, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.restaurantId = restaurantId;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.rating=rating;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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