package com.bogdevich.cafe.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable{

    private static final Long serialVersionUID = 1L;

    protected int id;

    protected Entity() {
    }

    public Entity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
