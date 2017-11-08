package com.bogdevich.cafe.entity.bean;

import com.bogdevich.cafe.entity.Entity;

import java.math.BigDecimal;
import java.util.Date;

public class Order extends Entity {
    private BigDecimal total;
    private Date orderTime;
    private int number;
    private String street;
    private String house;
    private int flat;
    private int userID;

    public Order() {
    }

    public Order(BigDecimal total, Date orderTime, int number, String street, String house, int flat, int userID) {
        this.total = total;
        this.orderTime = orderTime;
        this.number = number;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.userID = userID;
    }

    public Order(BigDecimal total, int number, String street, String house, int flat, int userID) {
        this.total = total;
        this.number = number;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.userID = userID;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public int getNumber() {
        return number;
    }

    public String getStreet() {
        return street;
    }

    public String getHouse() {
        return house;
    }

    public int getFlat() {
        return flat;
    }

    public int getUserID() {
        return userID;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (number != order.number) return false;
        if (flat != order.flat) return false;
        if (userID != order.userID) return false;
        if (total != null ? !total.equals(order.total) : order.total != null) return false;
        if (orderTime != null ? !orderTime.equals(order.orderTime) : order.orderTime != null) return false;
        if (street != null ? !street.equals(order.street) : order.street != null) return false;
        return house != null ? house.equals(order.house) : order.house == null;
    }

    @Override
    public int hashCode() {
        int result = total != null ? total.hashCode() : 0;
        result = 31 * result + (orderTime != null ? orderTime.hashCode() : 0);
        result = 31 * result + number;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + flat;
        result = 31 * result + userID;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("total=").append(total);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", number=").append(number);
        sb.append(", street='").append(street).append('\'');
        sb.append(", house='").append(house).append('\'');
        sb.append(", flat=").append(flat);
        sb.append(", userID=").append(userID);
        sb.append('}');
        return sb.toString();
    }
}
