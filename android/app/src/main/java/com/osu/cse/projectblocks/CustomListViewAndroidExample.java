package com.osu.cse.projectblocks;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CustomListViewAndroidExample extends AppCompatActivity {

    ListView list;
    CustomAdapter adapter;
    public  CustomListViewAndroidExample CustomListView = null;
    public  ArrayList<Layoutdata> CustomListViewValuesArr = new ArrayList<Layoutdata>();
    public int totalprice=0;
    TextView mTextView_price;
    CheckBox mCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_view_android_example);

        CustomListView = this;

        /******** Take some data in Arraylist ( CustomListViewValuesArr ) ***********/
        setListData();

        Resources res =getResources();
        list= ( ListView )findViewById( R.id.list );  // List defined in XML ( See Below )
        mTextView_price=(TextView)findViewById(R.id.price);
        mCheckBox=(CheckBox)findViewById(R.id.checkBox);

        /**************** Create Custom Adapter *********/
        adapter=new CustomAdapter( CustomListView, CustomListViewValuesArr,res);
        list.setAdapter(adapter);

       // addListenerOnChkIos();

    }

    /****** Function to set data in ArrayList *************/
    public void setListData()
    {

        for (int i = 0; i < 11; i++) {

            final Layoutdata sched = new Layoutdata();

            /******* Firstly take data in model object ******/
            sched.setFoodName("Food "+i);
            sched.setFoodImage("image" + i);
            sched.setFoodPrice(Integer.toString(i));

            /******** Take Model Object in ArrayList **********/
            CustomListViewValuesArr.add(sched);
        }

    }

public void add()
{
    if(mCheckBox.isChecked()) {
        
    }

}


    /*****************  This function used by adapter ****************/
    public void onItemClick(int mPosition)
    {

        Layoutdata tempValues = ( Layoutdata ) CustomListViewValuesArr.get(mPosition);


        totalprice += Integer.parseInt(mTextView_price.toString());
        mTextView_price.setText(Integer.toString(totalprice));
        //addListenerOnChkIos();
        // SHOW ALERT

        Toast.makeText(CustomListView,"" + tempValues.getFoodName()+ " Image:"+tempValues.getFoodImage()
            +"FoodPrice:"+tempValues.getFoodPrice(), Toast.LENGTH_LONG).show();
    }


}



