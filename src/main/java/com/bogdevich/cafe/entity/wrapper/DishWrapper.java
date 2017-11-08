package com.bogdevich.cafe.entity.wrapper;

import com.bogdevich.cafe.entity.bean.Dish;

public class DishWrapper {

    private Dish dish;
    private Integer quantity;

    public DishWrapper() {
    }

    public DishWrapper(Dish dish, Integer quantity) {
        this.dish = dish;
        this.quantity = quantity;
    }

    public Dish getDish() {
        return dish;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DishWrapper)) return false;

        DishWrapper that = (DishWrapper) o;

        if (dish != null ? !dish.equals(that.dish) : that.dish != null) return false;
        return quantity != null ? quantity.equals(that.quantity) : that.quantity == null;
    }

    @Override
    public int hashCode() {
        int result = dish != null ? dish.hashCode() : 0;
        result = 31 * result + (quantity != null ? quantity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("DishWrapper{");
        sb.append("dish=").append(dish);
        sb.append(", quantity=").append(quantity);
        sb.append('}');
        return sb.toString();
    }
}
