package org.example.bean;

import java.time.LocalDateTime;

public class Table {
    private Long tableId;
    private Long restaurantId;
    private String tableNumber;
    private String qrCode;
    private Byte status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Table() {
    }

    public Table(Long tableId, Long restaurantId, String tableNumber, String qrCode, Byte status, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.tableId = tableId;
        this.restaurantId = restaurantId;
        this.tableNumber = tableNumber;
        this.qrCode = qrCode;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Long getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Long restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
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
