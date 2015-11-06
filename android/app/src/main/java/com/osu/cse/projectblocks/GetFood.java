package com.osu.cse.projectblocks;

import java.util.ArrayList;

/**
 * Created by Yiran on 11/5/2015.
 */
public class GetFood {
    public static ArrayList<Food> getInfo(){
        ArrayList<Food> foodInfo = new ArrayList<>();

        //Baked Goods
        foodInfo.add(new Food("Blueberry Bagel", 2.0, null, null));
        foodInfo.add(new Food("Chocolate Chip Cookie", 0.85, null, null));
        foodInfo.add(new Food("Banana Nut Muffin", 2.0, null, null));
        //Wraps
        foodInfo.add(new Food("BBQ Chicken Wrap", 5.0, null, null));
        foodInfo.add(new Food("Double Egg & Cheese Wrap", 4.0, null, null));
        foodInfo.add(new Food("Vegetarian Chicken Wrap", 5.0, null, null));
        //Entrees
        foodInfo.add(new Food("Pepperoni Flatbread pizza", 5.5, null, null));
        foodInfo.add(new Food("Chicken Quesadilla", 5.5, null, null));
        //Sandwiches
        foodInfo.add(new Food("Ham Bagel Sandwich", 4.0, null, null));
        foodInfo.add(new Food("Italian Sub", 5.75, null, null));
        foodInfo.add(new Food("Oxley's Pretzel Club Sub", 6.50, null, null));
        //Hot Baverages
        foodInfo.add(new Food("Tall Caffe Americano", 2.25, null, null));
        foodInfo.add(new Food("Grande Coffee", 2.10, null, null));
        foodInfo.add(new Food("Venti Hot Chocolate", 3.45, null, null));
        //Frozen Baverages
        foodInfo.add(new Food("Grande Mocha Frappuccino", 3.95, null, null));
        foodInfo.add(new Food("Large Mango Smoothie", 4.50, null, null));
        //Grab & Go
        foodInfo.add(new Food("Original Sun Chips", 1.0, null, null));

        return foodInfo;
    }
}
