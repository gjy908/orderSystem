package org.example.vo;

import java.math.BigDecimal;
import java.sql.Date;

public class StatisticsAmountDTO {
    private BigDecimal dailyRevenue;
    private Date orderDate;

    public StatisticsAmountDTO() {
    }

    public StatisticsAmountDTO(BigDecimal dailyRevenue, Date orderDate) {
        this.dailyRevenue = dailyRevenue;
        this.orderDate = orderDate;
    }

    public BigDecimal getDailyRevenue() {
        return dailyRevenue;
    }

    public void setDailyRevenue(BigDecimal dailyRevenue) {
        this.dailyRevenue = dailyRevenue;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
}

