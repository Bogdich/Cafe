package com.bogdevich.cafe.entity.type;

public enum Category {
    TWISTER(1),
    BURGER(2),
    PANCAKE(3),
    DRINK(4),
    OTHER(5);

    private int id;

    Category(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static Category defineCategory(int id) {
        for (Category category :
                Category.values()) {
            if (category.id == id) {
                return category;
            }
        }
        return null;
    }
}
