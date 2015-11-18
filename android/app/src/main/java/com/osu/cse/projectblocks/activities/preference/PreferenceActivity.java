package com.osu.cse.projectblocks.activities.preference;

import android.app.ListActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.activities.MainActivity;
import com.osu.cse.projectblocks.activities.cafeteria.list.CafeteriaListActivity;
import com.osu.cse.projectblocks.activities.food.menu.FoodMenuActivity;
import com.osu.cse.projectblocks.models.Preference;

import java.util.List;
import java.util.Random;

public class PreferenceActivity extends ListActivity {
    private PreferenceDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // initialize the data source for preferences
        dataSource = new PreferenceDataSource(this);
        dataSource.open();

        List<Preference> preferenceList = dataSource.getAllPreferences();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        ArrayAdapter<Preference> adapter = new ArrayAdapter<Preference>(this,
                android.R.layout.simple_list_item_1, preferenceList);
        setListAdapter(adapter);

    }

    public void onClick(View view){
        @SuppressWarnings("unchecked")
        ArrayAdapter<Preference> adapter = (ArrayAdapter<Preference>) getListAdapter();

        // Mock preference
        Preference p = new Preference("Peanuts", "Alergy", true);

        switch (view.getId()) {
            case R.id.addPreference:
                int nextInt = new Random().nextInt(3);

                // save the new Preference to the database
                p = dataSource.createPreference(p);
                adapter.add(p);
                break;
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onPause() {
        dataSource.close();
        super.onPause();
    }

    @Override
    protected void onPostResume() {
        dataSource.open();
        super.onPostResume();
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
                i = new Intent(PreferenceActivity.this, MainActivity.class);
                startActivity(i);
                break;

            // Find food menu item
            case R.id.find_food:
                i = new Intent(PreferenceActivity.this, FoodMenuActivity.class);
                startActivity(i);
                break;

            // find cafeteria menu item
            case R.id.find_cafe:
                i = new Intent(PreferenceActivity.this, CafeteriaListActivity.class);
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
}
