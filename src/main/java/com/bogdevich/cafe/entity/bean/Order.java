package com.bogdevich.cafe.entity.bean;

import com.bogdevich.cafe.entity.Entity;

import java.math.BigDecimal;
import java.util.Date;

public class Order extends Entity {
    private BigDecimal total;
    private Date orderTime;
    private UserInfo userInfo;

    public Order() {
    }

    public Order(BigDecimal total, Date orderTime, UserInfo userInfo) {
        this.total = total;
        this.orderTime = orderTime;
        this.userInfo = userInfo;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Date getOrderTime() {
        return orderTime;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public void setOrderTime(Date orderTime) {
        this.orderTime = orderTime;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;

        Order order = (Order) o;

        if (getTotal() != null ? !getTotal().equals(order.getTotal()) : order.getTotal() != null) return false;
        if (getOrderTime() != null ? !getOrderTime().equals(order.getOrderTime()) : order.getOrderTime() != null)
            return false;
        return getUserInfo() != null ? getUserInfo().equals(order.getUserInfo()) : order.getUserInfo() == null;
    }

    @Override
    public int hashCode() {
        int result = getTotal() != null ? getTotal().hashCode() : 0;
        result = 31 * result + (getOrderTime() != null ? getOrderTime().hashCode() : 0);
        result = 31 * result + (getUserInfo() != null ? getUserInfo().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Order{");
        sb.append("total=").append(total);
        sb.append(", orderTime=").append(orderTime);
        sb.append(", userInfo=").append(userInfo);
        sb.append('}');
        return sb.toString();
    }
}
