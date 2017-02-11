package com.bignerdranch.android.criminalintent;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by megan.wotring on 12/29/16.
 */

public class CrimeLab {
    private static CrimeLab sCrimeLab;
    private List<Crime> mCrimes;

    private CrimeLab(Context context) {
        mCrimes = new ArrayList<>();

        for(int i = 0; i< 100; i++){
            Crime crime = new Crime();
            crime.setTitle("Crime #" + i);
            crime.setSolved(i % 2 == 0); //every other one
            ContentValues content = new ContentValues();
            content.put("UUID", crime.getId().toString());
            content.put("TITLE", crime.getTitle());
            content.put("TIMESTAMP", crime.getDate().getTime());
            content.put("SOLVED", crime.isSolved());
            mCrimes.add(crime);
            ContentResolver resolver = context.getContentResolver();
            resolver.insert(, content);
        }
    }

    public static CrimeLab get(Context context){
        if (sCrimeLab == null) {
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }

    public List<Crime> getCrimes() {
        return mCrimes;
    }

    public Crime getCrime(UUID id){
        for (Crime crime : mCrimes){
            if (crime.getId().equals(id)){
                return crime;
            }
        }
        return null;
    }
}
