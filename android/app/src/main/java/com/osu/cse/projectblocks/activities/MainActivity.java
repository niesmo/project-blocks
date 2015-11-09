package com.osu.cse.projectblocks.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.osu.cse.projectblocks.CustomAdapter;
import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.data.FoodRepository;
import com.osu.cse.projectblocks.models.Food;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener{

    ListView list;
    CustomAdapter adapter;
    public  MainActivity CustomListView = null;
    public List<Food> CustomListViewValuesArr = new ArrayList<>();
    public double totalprice=0;
    //TextView mTextView_price;
    TextView mTextView_money;
    TextView mTextView_block;
    FoodRepository mFoodRepository;
    Button mMaximum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CustomListView = this;



        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        // Resources res =getResources();
        list= (ListView)findViewById( R.id.list );  // List defined in XML ( See Below )
        mTextView_money=(TextView)findViewById(R.id.price);
        mTextView_block=(TextView)findViewById(R.id.block);
        mMaximum=(Button)findViewById(R.id.maximum);


        /**************** Create Custom Adapter *********/
        adapter=new CustomAdapter(CustomListView, CustomListViewValuesArr);
        list.setAdapter(adapter);

        mMaximum.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                Intent toresult=new Intent(MainActivity.this, Result.class);

                toresult.putExtra("totalprice",String.valueOf(totalprice));
                startActivity(toresult);
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
                i = new Intent(MainActivity.this, MapsActivity.class);
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

    /****** Function to set data in ArrayList *************/
    public void setListData()
    {
        CustomListViewValuesArr= FoodRepository.getAllFoods();
    }


    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {

        Food tempValues = ( Food ) CustomListViewValuesArr.get(mPosition);


        // SHOW ALERT

        Toast.makeText(CustomListView, tempValues.getName() + " FoodPrice:" + tempValues.getPrice(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int pos=list.getPositionForView(buttonView);
        int numblock=0;
        DecimalFormat df = new DecimalFormat("0.00");

        if(pos!= ListView.INVALID_POSITION)
        {
            Food d=CustomListViewValuesArr.get(pos);
            d.setIsSelected(isChecked);
            // Toast.makeText(this,"yes", Toast.LENGTH_SHORT).show();
            if(isChecked)
                totalprice += d.getPrice();
            else
                totalprice -= d.getPrice();

            mTextView_money.setText("$"+df.format(totalprice));
            if(totalprice%5>0)
                numblock=(int)totalprice/5+1;
            else
                numblock=(int)totalprice/5;

            mTextView_block.setText(Integer.toString(numblock));

        }
    }




}
