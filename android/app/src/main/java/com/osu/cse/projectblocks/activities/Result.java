package com.osu.cse.projectblocks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.data.FoodRepository;
import com.osu.cse.projectblocks.models.Food;

import java.util.ArrayList;
import java.util.List;

public class Result extends AppCompatActivity {
    //Need to get from another activity
    double totalmoney ;
    int block;
    int len;
    double money;
    List<Food> allfood;
    List<String> result = new ArrayList<>();

    ListView listview;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);


        totalmoney= Double.parseDouble(getIntent().getStringExtra("totalprice"));
        block = (int) totalmoney/5; block++;
        money = (double) block*5 - totalmoney;
        allfood = FoodRepository.getAllFoods();
        len = allfood.size();
        combination(0, 0.0, new ArrayList<String>());

        listview = (ListView)findViewById(R.id.Result_listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
        listview.setAdapter(adapter);
    }

    private void combination(int start, double sum, ArrayList<String> list){
        if((money-sum) < 0.85){
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<list.size(); i++){
                sb.append("\n" + list.get(i));
            }
            sb.append("=$");
            sb.append(totalmoney+sum);
            result.add(sb.substring(1).toString());
            return;
        }
        for(int i=start; i<len; i++){
            if((allfood.get(i).getPrice() + sum) <= money){
                list.add(allfood.get(i).getName());
                combination(i, allfood.get(i).getPrice() + sum, list);
                list.remove(list.size()-1);
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
                i = new Intent(Result.this, MainActivity.class);
                startActivity(i);
                break;

            // Find food menu item
            case R.id.find_food:
                i = new Intent(Result.this, FoodMenuActivity.class);
                startActivity(i);
                break;

            // find cafeteria menu item
            case R.id.find_cafe:
                i = new Intent(Result.this, MapsActivity.class);
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
