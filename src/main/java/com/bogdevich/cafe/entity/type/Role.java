package com.bogdevich.cafe.entity.type;

public enum Role {
    ADMIN(1),
    CUSTOMER(2),
    GUEST(3);

    private int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Role getRoleById(int id) {
        switch (id) {
            case 1:
                return ADMIN;
            case 2:
                return CUSTOMER;
            case 3:
                return GUEST;
            default:
                return GUEST;
        }
    }
}
