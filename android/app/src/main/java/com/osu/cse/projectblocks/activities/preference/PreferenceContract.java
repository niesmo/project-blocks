package com.osu.cse.projectblocks.activities.preference;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by niesmo on 11/17/2015.
 */
public class PreferenceContract {
    private PreferenceContract(){}

    // inner class that defines the table content
    public static abstract class PreferenceEntry implements BaseColumns {
        public static final String TABLE_NAME = "preference";
        public static final String COLUMN_NAME_TYPE = "type";
        public static final String COLUMN_NAME_SUBJECT = "subject";
        public static final String COLUMN_NAME_ACTIVE = "active";
    }

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PreferenceEntry.TABLE_NAME + " (" +
                    PreferenceEntry._ID + " INTEGER PRIMARY KEY," +
                    PreferenceEntry.COLUMN_NAME_TYPE + TEXT_TYPE + COMMA_SEP +
                    PreferenceEntry.COLUMN_NAME_SUBJECT + TEXT_TYPE + COMMA_SEP +
                    PreferenceEntry.COLUMN_NAME_ACTIVE + INTEGER_TYPE +
            " )";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PreferenceEntry.TABLE_NAME;
}
