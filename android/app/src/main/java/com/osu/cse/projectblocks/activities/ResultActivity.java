package com.osu.cse.projectblocks.activities;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.activities.cafeteria.list.CafeteriaListActivity;
import com.osu.cse.projectblocks.activities.food.menu.FoodMenuActivity;
import com.osu.cse.projectblocks.activities.preference.PreferenceActivity;
import com.osu.cse.projectblocks.data.Repository;
import com.osu.cse.projectblocks.models.Food;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    //Need to get from another activity
    double totalmoney ;
    int block;
    int len;
    double money;
    List<Food> allfood;
    DecimalFormat df = new DecimalFormat("0.00");
    ArrayList<HashMap<String, String>> result = new ArrayList<>();

    TextView textview;
    ListView listview;
    SimpleAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        totalmoney= Double.parseDouble(getIntent().getStringExtra("totalprice"));
        textview = (TextView)findViewById(R.id.result_text);
        block = Integer.parseInt(getIntent().getStringExtra("blocknum"));
        money = (double) block*5 - totalmoney;
        if(totalmoney%5==0 || money < 0.85){
            textview.setText("No More You Can Have!");
        }else{
            allfood = Repository.getAllFoods();
            //delete all the foods whose price are greater than the rest money
            for(int i=0; i<allfood.size(); i++){
                Food temp = allfood.get(i);
                if(temp.getPrice()>money) allfood.remove(i);
            }
            len = allfood.size();
            combination(0, 0.0, new ArrayList<String>());
            textview.setText("What Else You Can Have!");
            listview = (ListView)findViewById(R.id.Result_listView);
            adapter = new SimpleAdapter(this, result, R.layout.column_result, new String[] {"item", "price"}, new int[] {R.id.column_result, R.id.column_price});
            listview.setAdapter(adapter);
        }
    }

    private void combination(int start, double sum, ArrayList<String> list){
        if((money-sum) < 0.85){
            StringBuffer sb = new StringBuffer();
            HashMap<String, Integer> map = new HashMap<>();
            int l = list.size();
            for(int i=0; i<l; i++){
                if(map.containsKey(list.get(i))) map.put(list.get(i), map.get(list.get(i))+1);
                else map.put(list.get(i), 1);
            }
            for(int i=0; i<l; i++){
                if((i!=0) && (list.get(i)==list.get(i-1))) continue;
                sb.append("\n");
                sb.append(list.get(i));
                sb.append(" x ");
                sb.append(map.get(list.get(i)));
            }
            HashMap<String, String> map2 = new HashMap<>();
            map2.put("item", sb.substring(1).toString());
            map2.put("price", "$" + df.format(totalmoney + sum));
            result.add(map2);
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
                i = new Intent(ResultActivity.this, MainActivity.class);
                startActivity(i);
                break;

            // Find food menu item
            case R.id.find_food:
                i = new Intent(ResultActivity.this, FoodMenuActivity.class);
                startActivity(i);
                break;

            // find cafeteria menu item
            case R.id.find_cafe:
                i = new Intent(ResultActivity.this, CafeteriaListActivity.class);
                startActivity(i);
                break;

            // navigate to the nearest cafeteria menu item
            case R.id.nearest_cafe:
                LocationManager locationManager = (LocationManager) getSystemService(ResultActivity.this.LOCATION_SERVICE);

                @SuppressWarnings("ResourceType")
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                }
                if (location == null){
                    return true;
                }
                i = new Intent(ResultActivity.this, MapsActivity.class);
                startActivity(i);
                break;

            // preference menu item
            case R.id.setting_preference:
                i = new Intent(ResultActivity.this, PreferenceActivity.class);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}
