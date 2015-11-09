package com.osu.cse.projectblocks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.data.DataApi;
import com.osu.cse.projectblocks.data.GetFood;
import com.osu.cse.projectblocks.data.OrchestrateDataParser;
import com.osu.cse.projectblocks.models.Food;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;


public class FoodMenuActivity extends AppCompatActivity {
    private DataApi repository;
    private GetFood mGetFood;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        // Setting up the repository (NOTE this a Singleton class)
        repository = DataApi.getInstance();
        mGetFood=new GetFood();

        // setting up the Orchestrate data parser
        final OrchestrateDataParser<Food> foodParser = new OrchestrateDataParser();

        final TextView mTextView = (TextView) findViewById(R.id.response);

        repository.getFoods(this, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    List<Food> list;
                    list = foodParser.parseArray(response, Food.class);

                    String names = "";
                    for (Iterator<Food> i = list.iterator(); i.hasNext(); ) {
                        Food f = i.next();
                        names += f.getName() + "\n";
                        mGetFood.setInfo(f.getName(),f.getPrice());

                    }

                    mTextView.setText(names);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText(error.toString());
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
                i = new Intent(FoodMenuActivity.this, MapsActivity.class);
                startActivity(i);
                break;

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
