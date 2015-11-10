package com.osu.cse.projectblocks.activities.cafeteria.list;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.models.Cafeteria;


import java.util.List;


/**
 * Created by niesmo on 11/9/2015.
 */
public class CafeteriaListAdapter extends ArrayAdapter<Cafeteria>{
    List<Cafeteria> cafeterias;

    public CafeteriaListAdapter(Context context, int resource, List<Cafeteria> cafeterias) {
        super(context, 0, cafeterias);

        this.cafeterias = cafeterias;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Cafeteria cafeteria = this.cafeterias.get(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.activity_cafeteria_list_item, parent, false);
        }

        TextView cafeteriaName = (TextView) convertView.findViewById(R.id.cafeteria_list_item);

        cafeteriaName.setText(cafeteria.getName());

        return convertView;
    }
}
