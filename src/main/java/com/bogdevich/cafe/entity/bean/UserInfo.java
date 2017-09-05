package com.bogdevich.cafe.entity.bean;

import com.bogdevich.cafe.entity.Entity;

public class UserInfo extends Entity {
    private int number;
    private String street;
    private String house;
    private int flat;
    private int userID;

    public UserInfo() {
    }

    public UserInfo(int number, String street, String house, int flat, int userID) {
        this.number = number;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.userID = userID;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public int getFlat() {
        return flat;
    }

    public void setFlat(int flat) {
        this.flat = flat;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserInfo userinfo = (UserInfo) o;

        if (number != userinfo.number) return false;
        if (flat != userinfo.flat) return false;
        if (userID != userinfo.userID) return false;
        if (street != null ? !street.equals(userinfo.street) : userinfo.street != null) return false;
        return house != null ? house.equals(userinfo.house) : userinfo.house == null;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (house != null ? house.hashCode() : 0);
        result = 31 * result + flat;
        result = 31 * result + userID;
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("UserInfo{");
        sb.append("number=").append(number);
        sb.append(", street='").append(street).append('\'');
        sb.append(", house='").append(house).append('\'');
        sb.append(", flat=").append(flat);
        sb.append(", userID=").append(userID);
        sb.append('}');
        return sb.toString();
    }
}
