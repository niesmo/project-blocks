package com.osu.cse.projectblocks.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.data.DataApi;
import com.osu.cse.projectblocks.models.Person;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FoodMenuActivity extends AppCompatActivity {
    private DataApi repository;
    private final Gson gson = new Gson();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_menu);

        // Setting up the repository
        repository = DataApi.getInstance();

        final TextView mTextView = (TextView) findViewById(R.id.response);

        repository.getCafeterias(this, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response){
                try {
                    String firstPerson = response.getJSONArray("results")
                            .getJSONObject(0)
                            .getJSONObject("value")
                            .toString();


                    Person p = gson.fromJson(firstPerson, Person.class);
                    mTextView.setText(p.getName());
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
