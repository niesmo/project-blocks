package com.osu.cse.projectblocks.activities.food.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.data.Repository;
import com.osu.cse.projectblocks.models.Food;

import java.util.List;

/**
 * Created by niesmo on 11/9/2015.
 */
public class FoodMenuAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Food> foods;

    public FoodMenuAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return Repository.getAllCategories().size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        String cat = Repository.getAllCategories().get(groupPosition);
        return Repository.getAllFoodInCategory(cat).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return Repository.getAllCategories().get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        String cat = Repository.getAllCategories().get(groupPosition);
        return Repository.getAllFoodInCategory(cat).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String category = (String) getGroup(groupPosition);

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.activity_category_header, null);
        }

        TextView categoryTextView = (TextView) convertView.findViewById(R.id.category_header);
        categoryTextView.setText(category);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Food food = (Food) getChild(groupPosition,childPosition);

        if(convertView == null){
            LayoutInflater inf = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.activity_food_menu_item, null);
        }

        TextView foodMenuItem = (TextView) convertView.findViewById(R.id.food_menu_item);
        foodMenuItem.setText(food.getName());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
