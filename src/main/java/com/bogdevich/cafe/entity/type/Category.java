package com.bogdevich.cafe.entity.type;

public enum Category {
    TWISTER(1, "twister"),
    BURGER(2, "burger"),
    PANCAKE(3, "pancake"),
    DRINK(4, "drink"),
    OTHER(5, "other");

    private static final String CATEGORY_BUNDLE_KEY_PART = "category.";

    private int id;
    private String key;

    Category(int id, String key) {
        this.id = id;
        this.key = Category.CATEGORY_BUNDLE_KEY_PART + key;
    }

    public int getId() {
        return this.id;
    }

    public String getKey() {
        return this.key;
    }

    public static Category defineCategory(String name) {
        for (Category category : Category.values()) {
            if (category.getKey().equals(name)) {
                return category;
            }
        }
        return null;
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
