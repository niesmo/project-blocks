package com.osu.cse.projectblocks.activities.preference;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.osu.cse.projectblocks.models.Preference;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by niesmo on 11/17/2015.
 */
public class PreferenceDataSource {
    private SQLiteDatabase database;
    private PreferenceDbHelper dbHelper;
    private String[] allColumns = {
            PreferenceContract.PreferenceEntry._ID,
            PreferenceContract.PreferenceEntry.COLUMN_NAME_ACTIVE,
            PreferenceContract.PreferenceEntry.COLUMN_NAME_SUBJECT,
            PreferenceContract.PreferenceEntry.COLUMN_NAME_TYPE,
    };

    public PreferenceDataSource(Context context){
        dbHelper = new PreferenceDbHelper(context);
    }

    /**
     * This function will open the database with write access
     */
    public void open(){
        this.database = dbHelper.getWritableDatabase();
    }

    /**
     * This function simply closed the database
     */
    public void close(){
        this.database.close();
    }

    /**
     * This function will insert the passed preference to the database
     * http://www.vogella.com/tutorials/AndroidSQLite/article.html
     * @param p the Preference that is about to be created
     * @return the created preference (should be the same object and the one passed in)
     */
    public Preference createPreference(Preference p){
        ContentValues values = new ContentValues();

        // putting the values in the `values`
        values.put(PreferenceContract.PreferenceEntry.COLUMN_NAME_SUBJECT, p.getSubject());
        values.put(PreferenceContract.PreferenceEntry.COLUMN_NAME_TYPE, p.getType());
        values.put(PreferenceContract.PreferenceEntry.COLUMN_NAME_ACTIVE, p.getActive());


        // insert it to the database
        long insertId = database.insert(PreferenceContract.PreferenceEntry.TABLE_NAME, null, values);

        // get the isnerted item from the database
        Cursor cursor = database.query(PreferenceContract.PreferenceEntry.TABLE_NAME, allColumns, PreferenceContract.PreferenceEntry._ID + " = " + insertId, null, null, null, null );

        cursor.moveToFirst();

        // serialize the cursor to a Preference
        Preference insertedPreference = new Preference(cursor);

        cursor.close();
        return insertedPreference;
    }

    public List<Preference> getAllPreferences(){
        List<Preference> preferences = new ArrayList<>();

        // querying the database for all the preferences
        Cursor cursor = database.query(PreferenceContract.PreferenceEntry.TABLE_NAME, allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Preference p = new Preference(cursor);
            preferences.add(p);
            cursor.moveToNext();
        }

        cursor.close();
        return preferences;
    }
}
