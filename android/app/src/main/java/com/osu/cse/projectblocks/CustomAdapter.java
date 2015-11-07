package com.osu.cse.projectblocks;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by shirly on 11/5/15.
 */
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private ArrayList data;
    private static LayoutInflater inflater=null;
   // public Resources res;
    Food tempValues=null;

   // int totalmoney=0;


    public CustomAdapter(Activity a, ArrayList d) {
        activity = a;
        data = d;



        inflater=(LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount(){
        if(data.size()<0)
            return 1;
        return data.size();
    }

    public Object getItem(int position)
    {
        return position;
    }

    public long getItemId(int position)
    {
        return position;
    }

    public static class ViewHolder{
        public TextView foodname;
        public TextView foodpri;
        //public TextView textWide;
        public ImageView image;
        public CheckBox checkbox;


    }

    /****** Depends upon data size called for each row , Create each ListView row *****/
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi = convertView;
        ViewHolder holder;

        if(convertView==null){

            /****** Inflate tabitem.xml file for each row ( Defined below ) *******/
            vi = inflater.inflate(R.layout.layout, null);

            /****** View Holder Object to contain tabitem.xml file elements ******/

            holder = new ViewHolder();
            holder.foodname = (TextView) vi.findViewById(R.id.foodname);
            holder.foodpri=(TextView)vi.findViewById(R.id.foodpri);
            holder.image=(ImageView)vi.findViewById(R.id.image);
            holder.checkbox=(CheckBox)vi.findViewById(R.id.checkBox);
            holder.checkbox.setOnCheckedChangeListener((MainActivity)activity);

            /************  Set holder with LayoutInflater ************/
            vi.setTag( holder );
        }
        else
            holder=(ViewHolder)vi.getTag();

        if(data.size()<=0)
        {
            holder.foodname.setText("No Data");

        }
        else
        {
            /***** Get each Model object from Arraylist ********/
            tempValues=null;
            tempValues = ( Food ) data.get( position );

            /************  Set Model values in Holder elements ***********/


            holder.foodname.setText(tempValues.getFoodname());
            holder.foodpri.setText("$" + tempValues.getFoodprice());
           // holder.image.setImageResource(res.getIdentifier("com.androidexample.customlistview:drawable/"+tempValues.getFoodImage(),null,null));
           // holder.checkbox.setChecked(tempValues.isSelected());
            //holder.checkbox.setTag(tempValues);



            /******** Set Item Click Listner for LayoutInflater for each row *******/
            vi.setOnClickListener(new OnItemClickListener(position));

        }
        return vi;
    }



    public void onClick(View v) {
        Log.v("CustomAdapter", "=====Row button clicked=====");
    }



    /********* Called when Item click in ListView ************/
    private class OnItemClickListener  implements View.OnClickListener{
        private int mPosition;

        OnItemClickListener(int position){
            mPosition = position;
        }


        @Override
        public void onClick(View arg0) {

            MainActivity sct = (MainActivity)activity;



            /****  Call  onItemClick Method inside CustomListViewAndroidExample Class ( See Below )****/

            sct.onItemClick(mPosition);
        }
    }
}
