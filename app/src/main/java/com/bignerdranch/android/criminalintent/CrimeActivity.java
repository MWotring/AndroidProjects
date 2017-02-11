package com.bignerdranch.android.criminalintent;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import java.util.UUID;

/**
 * Created by megan.wotring on 1/10/17.
 */

public class CrimeActivity extends SingleFragmentActivity {
    private static final String EXTRA_CRIME_ID = "com.bignerdranch.android.criminalintent.crime_id";
  //  @Override
 //   protected void onCreate(Bundle savedInstanceState){
//        super.onCreate(savedInstanceState);
 //       setContentView(R.layout.activity_crime);
//
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment fragment = fm.findFragmentById(R.id.fragment_container);

  //      if(fragment == null){
 //           fragment = new CrimeFragment();
 //           fm.beginTransaction().add(R.id.fragment_container, fragment).commit();
 //       }

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, CrimeActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected Fragment createFragment(){
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);
        return new CrimeFragment().newInstance(crimeId);
    }
}
