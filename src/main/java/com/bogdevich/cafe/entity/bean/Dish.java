package com.bogdevich.cafe.entity.bean;

import com.bogdevich.cafe.entity.Entity;
import com.bogdevich.cafe.entity.type.Category;

import java.math.BigDecimal;

public class Dish extends Entity {
    private String name;
    private BigDecimal price;
    private int weight;
    private String description;
    private String picture;
    private Category category;

    public Dish() {
    }

    public Dish(String name, BigDecimal price, int weight, String description, String picture, Category category) {
        this.name = name;
        this.price = price;
        this.weight = weight;
        this.description = description;
        this.picture = picture;
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Dish)) return false;

        Dish dish = (Dish) o;

        if (weight != dish.weight) return false;
        if (name != null ? !name.equals(dish.name) : dish.name != null) return false;
        if (price != null ? !price.equals(dish.price) : dish.price != null) return false;
        if (description != null ? !description.equals(dish.description) : dish.description != null) return false;
        if (picture != null ? !picture.equals(dish.picture) : dish.picture != null) return false;
        return category == dish.category;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + weight;
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (picture != null ? picture.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Dish{");
        sb.append("name='").append(name).append('\'');
        sb.append(", price=").append(price);
        sb.append(", weight=").append(weight);
        sb.append(", description='").append(description).append('\'');
        sb.append(", picture='").append(picture).append('\'');
        sb.append(", category=").append(category);
        sb.append('}');
        return sb.toString();
    }
}