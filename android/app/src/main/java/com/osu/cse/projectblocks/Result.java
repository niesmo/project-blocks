package com.osu.cse.projectblocks;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Result extends AppCompatActivity {
    //Need to get from another activity
    double totalmoney ;
    int block;
    int len;
    double money;
    ArrayList<Food> allfood;
    ArrayList<String> result = new ArrayList<>();

    ListView listview;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
//totalmoney=7.5;


        totalmoney= Double.parseDouble(getIntent().getStringExtra("totalprice"));
        block = (int) totalmoney/5; block++;
        money = (double) block*5 - totalmoney;
        allfood = GetFood.getInfo();
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
            if((allfood.get(i).getFoodprice() + sum) <= money){
                list.add(allfood.get(i).getFoodname());
                combination(i, allfood.get(i).getFoodprice() + sum, list);
                list.remove(list.size()-1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.find_food:
                return true;
            case R.id.find_cafe:
                Intent i = new Intent(Result.this, MapsActivity.class);
                startActivity(i);
                break;
            case R.id.setting_preference:
                return true;
            case R.id.history:
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
