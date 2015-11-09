package com.osu.cse.projectblocks.data;

import com.osu.cse.projectblocks.models.Food;

import java.util.ArrayList;

/**
 * Created by Yiran on 11/5/2015.
 */
public class GetFood {



    private static ArrayList<Food> foodInfo = new ArrayList<>();
    public void setInfo(String FoodName,double FoodPrice)
    {
        foodInfo.add(new Food(FoodName, FoodPrice));
    }
    public static ArrayList<Food> getInfo(){
     //   ArrayList<Food> foodInfo = new ArrayList<>();

        //Baked Goods
        /*foodInfo.add(new Food("Blueberry Bagel", 2.0));
        foodInfo.add(new Food("Chocolate Chip Cookie", 0.85));
        foodInfo.add(new Food("Banana Nut Muffin", 2.0));
        //Wraps
        foodInfo.add(new Food("BBQ Chicken Wrap", 5.0));
        foodInfo.add(new Food("Double Egg & Cheese Wrap", 4.0));
        foodInfo.add(new Food("Vegetarian Chicken Wrap", 5.0));
        //Entrees
        foodInfo.add(new Food("Pepperoni Flatbread pizza", 5.5));
        foodInfo.add(new Food("Chicken Quesadilla", 5.5));
        //Sandwiches
        foodInfo.add(new Food("Ham Bagel Sandwich", 4.0));
        foodInfo.add(new Food("Italian Sub", 5.75));
        foodInfo.add(new Food("Oxley's Pretzel Club Sub", 6.50));
        //Hot Baverages
        foodInfo.add(new Food("Tall Caffe Americano", 2.25));
        foodInfo.add(new Food("Grande Coffee", 2.10));
        foodInfo.add(new Food("Venti Hot Chocolate", 3.45));
        //Frozen Baverages
        foodInfo.add(new Food("Grande Mocha Frappuccino", 3.95));
        foodInfo.add(new Food("Large Mango Smoothie", 4.50));
        //Grab & Go
        foodInfo.add(new Food("Original Sun Chips", 1.0));
*/
        return foodInfo;
    }
}
