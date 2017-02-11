package com.bignerdranch.android.criminalintent.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by megan.wotring on 1/31/17.
 */

public class CrimeDatabase extends SQLiteOpenHelper {
    static final String TAG = "CriminalIntent";
    static final String DATABASENAME = "crime.db";
    static final int CURRENTVERSION = 1;

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate making tables in a sec");
        db.execSQL("CREATE TABLE Crime ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "UUID TEXT NOT NULL,"
                + "TITLE TEXT NOT NULL,"
                + "TIMESTAMP REAL NOT NULL,"
                + "SOLVED REAL NOT NULL,"
                + "UNIQUE (UUID) "
                + "ON CONFLICT REPLACE)");
        Log.d(TAG, "Created Tables, mofo");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int notold) {
        if(old != notold){
            db.execSQL("DROP TABLE crime");
            onCreate(db);
        }

    }

    public CrimeDatabase(Context context) {
        super(context, DATABASENAME, null, CURRENTVERSION);
    }

}
