package com.osu.cse.projectblocks.activities.cafeteria.list;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.activities.MainActivity;
import com.osu.cse.projectblocks.activities.food.menu.FoodMenuActivity;
import com.osu.cse.projectblocks.data.DataApi;
import com.osu.cse.projectblocks.data.OrchestrateDataParser;
import com.osu.cse.projectblocks.data.Repository;
import com.osu.cse.projectblocks.models.Cafeteria;
import com.osu.cse.projectblocks.models.Coordinates;
import com.osu.cse.projectblocks.models.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class CafeteriaListActivity extends AppCompatActivity {
    private static final String TAG = CafeteriaListActivity.class.getName();
    private DataApi db;
    private Cafeteria nearestCafeteria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cafeteria_list);

        // get all the cafeterias
        // Setting up the db
        db = DataApi.getInstance();

        // setting up the Orchestrate data parser
        final OrchestrateDataParser<Cafeteria> cafeteriaParser = new OrchestrateDataParser();
        final OrchestrateDataParser<Food> foodParser = new OrchestrateDataParser();

        db.getCafeterias(this, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Cafeteria> cafeterias = cafeteriaParser.parseArray(response, Cafeteria.class);

                    // cache all the cafeterias (These are pretty consistent)
                    Repository.cacheCafeterias(cafeterias);

                    //find the nearest Cafeteria
                    setNearestCafeteria(cafeterias);

                    // if we found the nearest cafeteria
                    if(nearestCafeteria != null){
                        // get all the foods for that cafeteria and cache them in the Repository
                        db.getFoodsInCafeteria(CafeteriaListActivity.this, nearestCafeteria.getKey(), new Response.Listener<JSONObject>(){

                            @Override
                            public void onResponse(JSONObject response) {
                                List<Food> foods = null;
                                try {
                                    foods = foodParser.parseArray(response, Food.class);

                                    Repository.cacheFoods(foods);

                                    // Make the Toast to let the user know that we selected the nearest cafeteria
                                    Toast.makeText(CafeteriaListActivity.this, nearestCafeteria.getName() + " was selected", Toast.LENGTH_SHORT).show();

                                    // reroute the user to the main activity
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error2) {
                                error2.printStackTrace();
                            }
                        });
                    }

                    // if we didnt find it, let the user pick
                    else{
                        Toast.makeText(CafeteriaListActivity.this, "We didnt find your location. Please select a cafeteria", Toast.LENGTH_SHORT).show();
                    }

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

    /**
     * This function will find the users location and will set the nearest cafeteria based on that
     * if it couldnt find the location of the user, it will set the nearestCafeteria to null
     * @param cafeterias
     */
    private void setNearestCafeteria(List<Cafeteria> cafeterias) {
        LocationManager locationManager = (LocationManager) getSystemService(CafeteriaListActivity.this.LOCATION_SERVICE);

        @SuppressWarnings("ResourceType")
        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        // neither of the location services worked :(
        if (location == null){
            this.nearestCafeteria = null;
            return;
        }

        double minDistance = (double) Integer.MAX_VALUE;


        for (Iterator<Cafeteria> i = cafeterias.iterator(); i.hasNext(); ) {
            Cafeteria cafeteria = i.next();
            Coordinates c = cafeteria.getLocation().getCoordinates();
            float[] results = new float[10];
            Location.distanceBetween(location.getLatitude(), location.getLongitude(), c.getLatitude(), c.getLongitude(), results);

            if(results[0] < minDistance){
                this.nearestCafeteria = cafeteria;
            }
        }
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
                i = new Intent(CafeteriaListActivity.this, MainActivity.class);
                startActivity(i);
                break;

            // Find food menu item
            case R.id.find_food:
                i = new Intent(CafeteriaListActivity.this, FoodMenuActivity.class);
                startActivity(i);
                return true;

            // find cafeteria menu item
            case R.id.find_cafe:
                return true;


            // preference menu item
            case R.id.setting_preference:
                return true;

            // history menu item
            case R.id.history:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
