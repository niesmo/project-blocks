package com.osu.cse.projectblocks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Result extends AppCompatActivity {
    //Need to get from another activity
    double totalmoney;
    int block;
    int len;
    double money;
    ArrayList<Food> allfood;
    ArrayList<String> result;

    ListView listview;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        totalmoney = 6.75;
        block = (int) totalmoney/5; block++;
        money = (double) block*2 - totalmoney;
        allfood = GetFood.getInfo();
        len = allfood.size();
        helper(0.0, new ArrayList<String>());

        listview = (ListView)findViewById(R.id.Result_listView);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, result);
        listview.setAdapter(adapter);
    }

    private void helper(double sum, ArrayList<String> list){
        if(sum == money){
            StringBuffer sb = new StringBuffer();
            for(int i=0; i<list.size(); i++){
                sb.append(list.get(i)+" ");
            }
            result.add(sb.toString());
            return;
        }
        for(int i=0; i<len; i++){
            if(allfood.get(i).getPrice()+sum <= money){
                list.add(allfood.get(i).getName());
                helper(allfood.get(i).getPrice() + sum, list);
                list.remove(list.size()-1);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
