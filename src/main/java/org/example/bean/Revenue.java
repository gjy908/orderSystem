package org.example.bean;


import java.time.LocalDate;
import java.time.LocalDateTime;

public class Revenue {
    private Long id;
    private Long restaurantId;
    private LocalDate date;
    private Double amount;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Boolean isDeleted;

    public Revenue() {
    }

    public Revenue(Long id, Long restaurantId, LocalDate date, Double amount, LocalDateTime createTime, LocalDateTime updateTime, Boolean isDeleted) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.date = date;
        this.amount = amount;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.isDeleted = isDeleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    @Override
    public String toString() {
        return "Revenue{" +
                "id=" + id +
                ", restaurantId=" + restaurantId +
                ", date=" + date +
                ", amount=" + amount +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}