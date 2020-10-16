package com.example.ibds.Do;

import java.io.Serializable;

public class ProductDo implements Serializable {
    int imgProduct;
    String Name="";
    String Description="";
    String Amount="";
    Float rating;

    public ProductDo(int imgProduct, String name, String description, String amount, Float rating) {
        this.imgProduct = imgProduct;
        Name = name;
        Description = description;
        Amount = amount;
        this.rating = rating;
    }

    public int getImgProduct() {
        return imgProduct;
    }

    public String getName() {
        return Name;
    }

    public String getDescription() {
        return Description;
    }

    public String getAmount() {
        return Amount;
    }

    public Float getRating() {
        return rating;
    }
}
