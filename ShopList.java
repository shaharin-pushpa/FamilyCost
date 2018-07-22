package com.example.kowshick.bazarcost;

import java.io.Serializable;

public class ShopList implements Serializable {
    private int id;
    private String name;

    public ShopList(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public ShopList(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
