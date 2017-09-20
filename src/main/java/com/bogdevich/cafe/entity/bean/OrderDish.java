package com.bogdevich.cafe.entity.bean;

/**
 * Created by Grodno on 20.09.2017.
 */
public class OrderDish {
    private int orderID;
    private int dishID;
    private int quantity;

    public OrderDish() {
    }

    public OrderDish(int orderID, int dishID, int quantity) {
        this.orderID = orderID;
        this.dishID = dishID;
        this.quantity = quantity;
    }

    public int getOrderID() {
        return orderID;
    }

    public int getDishID() {
        return dishID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OrderDish)) return false;

        OrderDish orderDish = (OrderDish) o;

        if (getOrderID() != orderDish.getOrderID()) return false;
        if (getDishID() != orderDish.getDishID()) return false;
        return getQuantity() == orderDish.getQuantity();
    }

    @Override
    public int hashCode() {
        int result = getOrderID();
        result = 31 * result + getDishID();
        result = 31 * result + getQuantity();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OrderDish{");
        sb.append("orderID=").append(orderID);
        sb.append(", dishID=").append(dishID);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
