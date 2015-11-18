package com.osu.cse.projectblocks.activities.preference;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.osu.cse.projectblocks.R;
import com.osu.cse.projectblocks.activities.MainActivity;
import com.osu.cse.projectblocks.activities.cafeteria.list.CafeteriaListActivity;
import com.osu.cse.projectblocks.activities.food.menu.FoodMenuActivity;
import com.osu.cse.projectblocks.models.Preference;

import java.util.List;

public class PreferenceActivity extends ListActivity {
    private PreferenceDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        // set the values for the drop down
        Spinner dropdown = (Spinner)findViewById(R.id.preferenceType);
        String[] items = new String[]{"Allergy", "Dislike", "Like"};
        ArrayAdapter<String> dropDownAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        dropdown.setAdapter(dropDownAdapter);

        // initialize the data source for preferences
        dataSource = new PreferenceDataSource(this);
        dataSource.open();

        List<Preference> preferenceList = dataSource.getAllPreferences();

        ArrayAdapter<Preference> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, preferenceList);
        setListAdapter(adapter);

    }

    public void onClick(View view){
        @SuppressWarnings("unchecked")
        ArrayAdapter<Preference> adapter = (ArrayAdapter<Preference>) getListAdapter();


        switch (view.getId()) {
            case R.id.addPreferenceButton:
                // get the info from the form
                EditText et = (EditText)findViewById(R.id.preferenceSubject);
                String pSubject = et.getText().toString();

                Spinner s = (Spinner)findViewById(R.id.preferenceType);
                String pType = s.getSelectedItem().toString();

                // validating the preference
                if(pSubject.trim().equals("") || pType.trim().equals("")){
                    // close the keyboard
                    View v = this.getCurrentFocus();
                    if (v != null) {
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    }

                    // make a toast with the error
                    Toast.makeText(getApplicationContext(), "Food is required", Toast.LENGTH_SHORT).show();
                    break;
                }

                // create the preference
                Preference p = new Preference(pSubject, pType, true);

                // save the new Preference to the database
                p = dataSource.createPreference(p);
                adapter.add(p);

                // clear the form
                et.setText("");

                // close the keyboard
                View v = this.getCurrentFocus();
                if (v != null) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

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
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
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
