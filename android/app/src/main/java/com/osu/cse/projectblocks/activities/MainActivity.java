package com.osu.cse.projectblocks.activities;

import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.osu.cse.projectblocks.CustomAdapter;
import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.activities.cafeteria.list.CafeteriaListActivity;
import com.osu.cse.projectblocks.activities.food.menu.FoodMenuActivity;
import com.osu.cse.projectblocks.activities.preference.PreferenceActivity;
import com.osu.cse.projectblocks.data.Repository;
import com.osu.cse.projectblocks.models.Cafeteria;
import com.osu.cse.projectblocks.models.Food;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView list;
    CustomAdapter adapter;
    public  MainActivity CustomListView = null;
    public List<Food> CustomListViewValuesArr = new ArrayList<>();
    public double totalprice=0;
    int numblock=0;
    TextView mTextView_money;
    TextView mTextView_block;
    Button mMaximum;
    private List<String> foodnamelist;
    private boolean flag = false;
    private double restmoney = 0;
    private List<Food> selectedFood = new ArrayList<>();
    private List<Food> showFood = new ArrayList<>();
    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**instance  */
        CustomListView = this;
        list= (ListView)findViewById( R.id.list );  // List defined in XML ( See Below )
        mTextView_money=(TextView)findViewById(R.id.price);
        mTextView_block=(TextView)findViewById(R.id.block);
        mMaximum=(Button)findViewById(R.id.maximum);

        //to avoid some httpconnect problems
        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        /****Create auto complete textview Adapter*****/
        final AutoCompleteTextView searchactv = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView1);
        ArrayAdapter<String> searchbaradapter= new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,foodnamelist);
        searchactv.setAdapter(searchbaradapter);

        //set on click listener in searchactv
        searchactv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
            list.setSelection(foodnamelist.indexOf(searchactv.getText().toString()));
            }
        });


        /**************** Create Custom Adapter *********/
        // Check whether we're recreating a previously destroyed instance
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            flag = savedInstanceState.getBoolean("LOCK");
            ArrayList<String> foodname = savedInstanceState.getStringArrayList("FOOD");
            numblock = savedInstanceState.getInt("NUM");
            totalprice = savedInstanceState.getDouble("Price");

            for(Iterator<Food> i = CustomListViewValuesArr.iterator(); i.hasNext();){
                Food food = i.next();
                if(foodname.contains(food.getName())){
                    food.setIsSelected(true);
                    selectedFood.add(food);
                }
            }
            if (flag){
                restmoney = numblock*5-totalprice;
                showFood = new ArrayList<Food>();
                for(Iterator<Food> i = selectedFood.iterator(); i.hasNext();){
                    showFood.add(i.next());
                }
                for(Iterator<Food> i = CustomListViewValuesArr.iterator(); i.hasNext();){
                    Food food = i.next();
                    if ((food.getPrice()<restmoney) && !showFood.contains(food)) showFood.add(food);
                }
                adapter=new CustomAdapter(CustomListView, showFood);
                list.setAdapter(adapter);
            }
            else{
                showFood = CustomListViewValuesArr;
                adapter=new CustomAdapter(CustomListView, showFood);
                list.setAdapter(adapter);
            }
            if(numblock<1) {
                mTextView_block.setText("");
            }
            else {
                mTextView_block.setText(Integer.toString(numblock) + "BLOCKS");
            }
            mTextView_money.setText("$" + df.format(totalprice));
        } else {
            showFood = CustomListViewValuesArr;
            adapter=new CustomAdapter(CustomListView, showFood);
            list.setAdapter(adapter);
        }

        mMaximum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent toresult=new Intent(MainActivity.this, ResultActivity.class);

                toresult.putExtra("totalprice",String.valueOf(totalprice));
                toresult.putExtra("blocknum",String.valueOf(numblock));
                startActivity(toresult);
            }
        });

        mTextView_block.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (flag){
                    Toast.makeText(MainActivity.this, "UNLOCK", Toast.LENGTH_SHORT).show();
                    flag = false;
                    showFood = CustomListViewValuesArr;
                    adapter=new CustomAdapter(CustomListView, showFood);
                    list.setAdapter(adapter);
                }
                else{
                    Toast.makeText(MainActivity.this, "LOCK", Toast.LENGTH_SHORT).show();
                    flag = true;
                    restmoney = numblock*5-totalprice;
                    showFood = new ArrayList<Food>();
                    for(Iterator<Food> i = selectedFood.iterator(); i.hasNext();){
                        showFood.add(i.next());
                    }
                    for(Iterator<Food> i = CustomListViewValuesArr.iterator(); i.hasNext();){
                        Food temp = i.next();
                        if ((temp.getPrice()<restmoney) && !showFood.contains(temp)) showFood.add(temp);
                    }
                    adapter=new CustomAdapter(CustomListView, showFood);
                    list.setAdapter(adapter);
                }
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
               return true;

            // Find food menu item
            case R.id.find_food:
                i = new Intent(MainActivity.this, FoodMenuActivity.class);
                startActivity(i);
                break;

            // find cafeteria menu item
            case R.id.find_cafe:
                i = new Intent(MainActivity.this, CafeteriaListActivity.class);
                startActivity(i);
                break;

            // navigate to the nearest cafeteria menu item
            case R.id.nearest_cafe:
                LocationManager locationManager = (LocationManager) getSystemService(MainActivity.this.LOCATION_SERVICE);

                @SuppressWarnings("ResourceType")
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location == null) {
                    location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                }
                if (location == null){
                    return true;
                }
                i = new Intent(MainActivity.this, MapsActivity.class);
                startActivity(i);
                break;

            // preference menu item
            case R.id.setting_preference:
                i = new Intent(MainActivity.this, PreferenceActivity.class);
                startActivity(i);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

    /****** Function to set data in ArrayList *************/
    public void setListData()
    {
        CustomListViewValuesArr= Repository.getAllFoods();
        foodnamelist=new ArrayList<>();
        for(Food temp:CustomListViewValuesArr)
        {
            foodnamelist.add(temp.getName());
            Log.v("FOODNAME",temp.getName());
        }

    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save the Flag and name of selected food
        savedInstanceState.putBoolean("LOCK", flag);
        ArrayList<String> foodname = new ArrayList<>();
        for(int i=0; i<selectedFood.size(); i++){
            foodname.add(selectedFood.get(i).getName());
        }
        savedInstanceState.putStringArrayList("FOOD",foodname);
        // Save the number of block and total money
        savedInstanceState.putInt("NUM", numblock);
        savedInstanceState.putDouble("Price", totalprice);
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }


    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {

        Food tempValues = ( Food ) showFood.get(mPosition);

        if(tempValues.isSelected()) {
            tempValues.setIsSelected(false);
            totalprice -= tempValues.getPrice();
            Log.v("PRICE",Double.toString(tempValues.getPrice()));
            Log.v("NAME",tempValues.getName());
            selectedFood.remove(tempValues);
            if(flag){
                restmoney = numblock*5-totalprice;
                showFood = new ArrayList<Food>();
                for(Iterator<Food> i = selectedFood.iterator(); i.hasNext();){
                    showFood.add(i.next());
                }
                for(Iterator<Food> i = CustomListViewValuesArr.iterator(); i.hasNext();){
                    Food temp = i.next();
                    if ((temp.getPrice()<restmoney) && !showFood.contains(temp)) showFood.add(temp);
                }
                adapter=new CustomAdapter(CustomListView, showFood);
                list.setAdapter(adapter);
            }
        }
        else
        {
            selectedFood.add(tempValues);
            tempValues.setIsSelected(true);
            totalprice += tempValues.getPrice();
            if(flag){
                restmoney = numblock*5-totalprice;
                showFood = new ArrayList<Food>();
                for(Iterator<Food> i = selectedFood.iterator(); i.hasNext();){
                    showFood.add(i.next());
                }
                for(Iterator<Food> i = CustomListViewValuesArr.iterator(); i.hasNext();){
                    Food temp = i.next();
                    if ((temp.getPrice()<restmoney) && !showFood.contains(temp)) showFood.add(temp);
                }
                adapter=new CustomAdapter(CustomListView, showFood);
                list.setAdapter(adapter);
            }
        }

        mTextView_money.setText("$" + df.format(totalprice));
        if(!flag){
            if(totalprice%5>0)
                numblock=(int)totalprice/5+1;
            else
                numblock=(int)totalprice/5;

            if(numblock<1)
            {
                mTextView_block.setText("");
            }
            else {
                mTextView_block.setText(Integer.toString(numblock) + "BLOCKS");
            }
        }else{
            mTextView_block.setText(Integer.toString(numblock) + "BLOCKS");
        }

        // SHOW ALERT
    }
}
