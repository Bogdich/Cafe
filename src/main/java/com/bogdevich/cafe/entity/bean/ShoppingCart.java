package com.bogdevich.cafe.entity.bean;

public class ShoppingCart {
    private int userID;
    private int dishID;
    private int quantity;

    public ShoppingCart() {
    }

    public ShoppingCart(int userID, int dishID, int quantity) {
        this.userID = userID;
        this.dishID = dishID;
        this.quantity = quantity;
    }

    public int getUserID() {
        return userID;
    }

    public int getDishID() {
        return dishID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUserID(int userID) {
        this.userID = userID;
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
        if (!(o instanceof ShoppingCart)) return false;

        ShoppingCart that = (ShoppingCart) o;

        if (getUserID() != that.getUserID()) return false;
        if (getDishID() != that.getDishID()) return false;
        return getQuantity() == that.getQuantity();
    }

    @Override
    public int hashCode() {
        int result = getUserID();
        result = 31 * result + getDishID();
        result = 31 * result + getQuantity();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("ShoppingCart{");
        sb.append("userID=").append(userID);
        sb.append(", dishID=").append(dishID);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
