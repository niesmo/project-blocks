package com.osu.cse.projectblocks;

/**
 * Created by shirly on 11/5/15.
 */
public class Layoutdata {
    private String FoodName="";
    private String FoodImage="";
    private double FoodPrice;


    private boolean isSelected;

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
    public String getFoodName() {
        return this.FoodName;
    }

    public String getFoodImage() {
        return this.FoodImage;
    }

    public double getFoodPrice() {
        return this.FoodPrice;
    }


    public void setFoodName(String foodName) {
        this.FoodName = foodName;
    }

    public void setFoodImage(String foodImage) {
        this.FoodImage = foodImage;
    }

    public void setFoodPrice(double foodPrice) {
        this.FoodPrice = foodPrice;
    }





}

