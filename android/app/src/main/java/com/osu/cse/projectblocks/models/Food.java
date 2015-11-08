package com.osu.cse.projectblocks.models;

/**
 * Created by Yiran on 11/5/2015.
 * Edited by Nima on 11/8/2015.
 */
public class Food extends OrchestrateObject{
    private String name;
    private double price;
    private String category;
    private String imageUrl;
    private boolean isSelected;
    private Nutrition nutrition;
    private String cafeteriaId;

    public Food(String key) {
        super(key);
    }

    public Food(String name, double price) {
        // setting the key to ?
        super("");

        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public Nutrition getNutrition() {
        return nutrition;
    }

    public void setNutrition(Nutrition nutrition) {
        this.nutrition = nutrition;
    }

    public String getCafeteriaId() {
        return cafeteriaId;
    }

    public void setCafeteriaId(String cafeteriaId) {
        this.cafeteriaId = cafeteriaId;
    }
}
