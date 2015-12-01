package com.osu.cse.projectblocks.activities.food.menu;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.activities.MainActivity;
import com.osu.cse.projectblocks.activities.MapsActivity;
import com.osu.cse.projectblocks.activities.cafeteria.list.CafeteriaListActivity;
import com.osu.cse.projectblocks.activities.preference.PreferenceActivity;
import com.osu.cse.projectblocks.data.DataApi;
import com.osu.cse.projectblocks.data.Repository;
import com.osu.cse.projectblocks.data.OrchestrateDataParser;
import com.osu.cse.projectblocks.models.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class FoodMenuActivity extends AppCompatActivity {
    private static final String TAG = FoodMenuActivity.class.getName();
    private DataApi db;
    private FoodMenuAdapter foodMenuAdapter;
    private ExpandableListView foodMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        // Setting up the db (NOTE this a Singleton class)
        db = DataApi.getInstance();

        // setting up the Orchestrate data parser
        final OrchestrateDataParser<Food> foodParser = new OrchestrateDataParser();


        db.getAllFoods(this, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Food> list;
                    list = foodParser.parseArray(response, Food.class);

                    // setting the foods for other activities
                    Repository.cacheFoods(list);

                    // Work with connecting the adapter and showing the items
                    foodMenu = (ExpandableListView) findViewById(R.id.foods_menu_by_category);

                    foodMenuAdapter = new FoodMenuAdapter(FoodMenuActivity.this);

                    foodMenu.setAdapter(foodMenuAdapter);

                    // setting on click listeners for menu food item
                    foodMenu.setOnChildClickListener(menuFoodItemClicked);

                    // setting on click listeners for menu food category
                    foodMenu.setOnGroupClickListener(menuFoodCategoryClicked);


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Intent i;
        switch (item.getItemId()) {
            // home page menu item
            case R.id.home_menu_item:
                i = new Intent(FoodMenuActivity.this, MainActivity.class);
                startActivity(i);
                break;

            // Find food menu item
            case R.id.find_food:
                return true;

            // find cafeteria menu item
            case R.id.find_cafe:
                i = new Intent(FoodMenuActivity.this, CafeteriaListActivity.class);
                startActivity(i);
                break;

            // navigate to the nearest cafeteria menu item
            case R.id.nearest_cafe:
                LocationManager locationManager = (LocationManager) getSystemService(FoodMenuActivity.this.LOCATION_SERVICE);

                @SuppressWarnings("ResourceType")
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                }
                if (location == null){
                    return true;
                }
                i = new Intent(FoodMenuActivity.this, MapsActivity.class);
                startActivity(i);
                break;

            // preference menu item
            case R.id.setting_preference:
                i = new Intent(FoodMenuActivity.this, PreferenceActivity.class);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private ExpandableListView.OnChildClickListener menuFoodItemClicked = new ExpandableListView.OnChildClickListener() {
        @Override
        public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
            String cat = Repository.getAllCategories().get(groupPosition);
            Food food = Repository.getAllFoodInCategory(cat).get(childPosition);

            Toast.makeText(FoodMenuActivity.this, food.getName() + " -> " + food.getPrice(), Toast.LENGTH_SHORT).show();

            return false;
        }
    };

    private ExpandableListView.OnGroupClickListener menuFoodCategoryClicked = new ExpandableListView.OnGroupClickListener() {
        @Override
        public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//            String cat = Repository.getAllCategories().get(groupPosition);
//            Toast.makeText(FoodMenuActivity.this, cat , Toast.LENGTH_SHORT).show();
            return false;
        }
    };
}
