package com.osu.cse.projectblocks.data;

import android.util.Log;

import com.osu.cse.projectblocks.models.Cafeteria;
import com.osu.cse.projectblocks.models.Food;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Yiran on 11/5/2015.
 */
public class Repository {
    private static final String TAG = Repository.class.getName();

    private static List<Food> _foodsCached;
    private static List<Cafeteria> _cafeteriasCached;
    private static List<String> _categoriesCached;
    private static HashMap<String, List<Food>> _foodByCategoryCached;

    public static void cacheFoods(List<Food> foods){
        // instantiate the new map for food by category variable
        _foodByCategoryCached = new HashMap<>();
        _categoriesCached = new ArrayList<>();

        // store the foods in a temp variables
        _foodsCached = foods;

        // Also cache the categories
        for(Iterator<Food> i = _foodsCached.iterator(); i.hasNext();){
            final Food f = i.next();

            // if this is the first food in this category
            if(!_categoriesCached.contains(f.getCategory())){

                // cache the category
                _categoriesCached.add(f.getCategory());


                // add the category and an array containing this food to the food by category map
                _foodByCategoryCached.put(f.getCategory(), new ArrayList<Food>() {{
                    add(f);
                }});
            }

            // if the category is already there, just add the food to the map
            else{
                _foodByCategoryCached.get(f.getCategory()).add(f);
            }
        }
    }
    public static List<Food> getAllFoods(){
        return _foodsCached;
    }
    public static List<String> getAllCategories(){
        return _categoriesCached;
    }
    public static List<Food> getAllFoodInCategory(String category){
        return _foodByCategoryCached.get(category);
    }

    public static void cacheCafeterias(List<Cafeteria> cafeterias) {
        _cafeteriasCached = cafeterias;
    }
}
