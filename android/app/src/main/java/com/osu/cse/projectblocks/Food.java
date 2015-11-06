package com.osu.cse.projectblocks;

/**
 * Created by Yiran on 11/5/2015.
 */
public class Food {
    private String foodname;
    private double foodprice;
    private String foodimage;
    private boolean isSelected;
    private String foodnutrition;


    public boolean isSelected() {
        return isSelected;
    }


    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Food(String foodname, double foodprice, String foodimage, String foodnutrition) {
        this.foodname = foodname;
        this.foodprice = foodprice;
        this.foodimage = foodimage;
        this.foodnutrition = foodnutrition;
    }


    public String getFoodname() {
        return foodname;
    }

    public void setFoodname(String foodname) {
        this.foodname = foodname;
    }

    public double getFoodprice() {
        return foodprice;
    }

    public void setFoodprice(double foodprice) {
        this.foodprice = foodprice;
    }

    public String getFoodimage() {
        return foodimage;
    }

    public void setFoodimage(String foodimage) {
        this.foodimage = foodimage;
    }

    public String getFoodnutrition() {
        return foodnutrition;
    }

    public void setFoodnutrition(String foodnutrition) {
        this.foodnutrition = foodnutrition;
    }







}
