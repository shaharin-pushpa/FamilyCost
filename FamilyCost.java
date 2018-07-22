package com.example.kowshick.bazarcost;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FamilyCost implements Serializable {
    private int id;
    private String date;
    private String category;
    private String product;
    private String quantity;
    private double price;
    private static List<FamilyCost>costs=new ArrayList<>();

    public FamilyCost() {
    }

    public FamilyCost(String date, String category, String product, String quantity, double price) {
        this.date = date;
        this.category = category;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public FamilyCost(int id, String date, String category, String product, String quantity, double price) {
        this.id = id;
        this.date = date;
        this.category = category;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getCategory() {
        return category;
    }

    public String getProduct() {
        return product;
    }

    public String getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
    public static void addCostTOList(FamilyCost fc){
        costs.add(fc);
    }

    public static List<FamilyCost> getCostList(){
        return costs;
    }
}
