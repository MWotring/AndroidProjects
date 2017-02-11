package com.bignerdranch.android.criminalintent.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bignerdranch.android.criminalintent.Crime;

/**
 * Created by megan.wotring on 1/23/17.
 */

public class CrimeProvider extends ContentProvider {
    static final String TAG = "CriminalIntent";
    private CrimeDatabase db;

    @Override
    public boolean onCreate() {
        Log.d(TAG, "!!!!!!!!!!!!!Prrovider Database onCreate");

        db = new CrimeDatabase(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        db.getWritableDatabase().insert("Crime", null, contentValues);

        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
