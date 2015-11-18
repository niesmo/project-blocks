package com.osu.cse.projectblocks.models;

import android.database.Cursor;

import com.osu.cse.projectblocks.activities.preference.PreferenceContract;

/**
 * Created by niesmo on 11/17/2015.
 */
public class Preference {
    private long id;
    private String subject;
    private String type;
    private Boolean active;

    public Preference(Cursor cursor) {
        id = cursor.getLong(cursor.getColumnIndex(PreferenceContract.PreferenceEntry._ID));
        subject = cursor.getString(cursor.getColumnIndex(PreferenceContract.PreferenceEntry.COLUMN_NAME_SUBJECT));
        type = cursor.getString(cursor.getColumnIndex(PreferenceContract.PreferenceEntry.COLUMN_NAME_TYPE));
        active = cursor.getInt(cursor.getColumnIndex(PreferenceContract.PreferenceEntry.COLUMN_NAME_ACTIVE)) == 1 ? true:false;
    }

    public Preference(String subject, String type, Boolean active) {
        this.subject = subject;
        this.type = type;
        this.active = active;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return subject;
    }
}
