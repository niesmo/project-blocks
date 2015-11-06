package com.osu.cse.projectblocks;

/**
 * Created by Yiran on 11/5/2015.
 */
public class Food {
    private String name;
    private double price;
    private String image;
    private String nutrition;

    public Food (String n, double p, String i, String nu){
        this.name = n;
        this.price = p;
        this.image = i;
        this.nutrition = nu;
    }

    public String getName(){
        return this.name;
    }
    public double getPrice() {
        return this.price;
    }
    public String getImage() {
        return this.image;
    }
    public String getNutrition() {
        return this.nutrition;
    }
}
