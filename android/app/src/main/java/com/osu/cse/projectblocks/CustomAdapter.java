package com.osu.cse.projectblocks;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.osu.cse.projectblocks.activities.MainActivity;
import com.osu.cse.projectblocks.models.Food;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by shirly on 11/5/15.
 */
public class CustomAdapter extends BaseAdapter implements View.OnClickListener {

    private Activity activity;
    private List data;
    private static LayoutInflater inflater=null;
    Food tempValues=null;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;



    public CustomAdapter(Activity a, List d) {
        activity = a;
        data = d;

        mRequestQueue = Volley.newRequestQueue(activity);
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mImageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache<String, Bitmap>(100);

            @Override
            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }


        });
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
        //
        //public ImageView image;
        NetworkImageView avatar ;
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
            //holder.image=(ImageView)vi.findViewById(R.id.image);
            holder.avatar=(NetworkImageView)vi.findViewById(R.id.image);
            holder.checkbox=(CheckBox)vi.findViewById(R.id.checkBox);


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
            Log.v("POSITION", Integer.toString(position));

            /************  Set Model values in Holder elements ***********/


            holder.foodname.setText(tempValues.getName());
            holder.foodpri.setText("$" + tempValues.getPrice());

            holder.avatar.setImageUrl(tempValues.getImageUrl(),mImageLoader);


            holder.checkbox.setOnClickListener(new OnItemClickListener(position));
            holder.checkbox.setChecked(tempValues.isSelected());

          


            /******** Set Item Click Listner for LayoutInflater for each row *******/
           // vi.setOnClickListener(new OnItemClickListener(position));

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

    private Bitmap getImageBitmap(String url) {
        Bitmap bm = null;
        try {
            Log.v("IMAGE URL",url);
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
            return bm;
        } catch (Exception e) {
            Log.e("IMAGE WRONG", "Error getting bitmap");
            return null;
        }

    }
}
