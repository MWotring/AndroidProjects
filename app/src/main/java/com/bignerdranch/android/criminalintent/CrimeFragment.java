package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import static com.bignerdranch.android.criminalintent.TimePickerFragment.EXTRA_HOUR;
import static com.bignerdranch.android.criminalintent.TimePickerFragment.EXTRA_MINUTE;

/**
 * Created by megan.wotring on 1/10/17.
 */

public class CrimeFragment extends Fragment {
    private static final String TAG = "CriminalIntent";
    private static final String ARG_CRIME_ID = "crime_id";
    private static final String DIALOG_DATE = "DialogDate"; //tag identifies DialogFragment in FragmentManager's list
    private static final String DIALOG_TIME = "DialogTime"; //used to put picker on screen
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private Crime mCrime;
    private EditText mTitleField;
    private Button mDateButton;
    private Button mTimeButton;
    private CheckBox mSolvedCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState); //parent runs same function!

        UUID crimeId = (UUID) getArguments().getSerializable(ARG_CRIME_ID);
        mCrime = CrimeLab.get(getActivity()).getCrime(crimeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        FragmentManager manager = getFragmentManager();
        View v = inflater.inflate(R.layout.fragment_crime, container, false);

        mTitleField = (EditText)v.findViewById(R.id.crime_title);
        mTitleField.setText(mCrime.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //blank
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCrime.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                //blank
            }
        });
        Log.d(TAG, "Crime Title created");

        mDateButton = (Button)v.findViewById(R.id.crime_date);
        mDateButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mCrime.getDate());
                dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE); //make CrimeFragment the target fragment of DatePickerFragment
                dialog.show(manager, DIALOG_DATE); //add DialogFragment to FragmentManager, put it on screen

            }
        });
        Log.d(TAG, "DatePicker created");
        updateDate();

        mTimeButton = (Button)v.findViewById(R.id.crime_time);
        mTimeButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                FragmentManager manager = getFragmentManager();
                TimePickerFragment timeDialog = TimePickerFragment.newInstance(mCrime.getDate());
                timeDialog.setTargetFragment(CrimeFragment.this, REQUEST_TIME);
                timeDialog.show(manager, DIALOG_TIME);
            }
        }
        );
        Log.d(TAG, "TimeDialog created");

        updateTime();

        mSolvedCheckBox = (CheckBox)v.findViewById(R.id.crime_solved);
        mSolvedCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mCrime.setSolved(isChecked);
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){ //ActivityManger calls after child activity dies
        if (resultCode != Activity.RESULT_OK){
            return;
        }
        Log.d(TAG, "######CF onActivityResult reqCode: " + requestCode + " result " + resultCode);
        if (requestCode == REQUEST_DATE){
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mCrime.setDate(date); //set new date from DatePicker in Crime
            updateDate();
        }
        if (requestCode == REQUEST_TIME){
            int hour =  data.getIntExtra(EXTRA_HOUR, 12);
            int minute = data.getIntExtra(EXTRA_MINUTE, 0);
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, hour);
            calendar.set(Calendar.MINUTE, minute);
            mCrime.getDate().setTime(calendar.getTimeInMillis());

            Log.d(TAG, "################# CF: The time to be set is " + hour + ":" + minute);
            updateTime();
        }
    }

    private void updateDate() {
        DateFormat df = new android.text.format.DateFormat();
        mDateButton.setText(df.format("EEE MMM dd, yyyy", mCrime.getDate()));
    }

    private void updateTime(){
        DateFormat df = new android.text.format.DateFormat();
        Log.d(TAG, "####update time from Date " + df.format("hh:mm a", mCrime.getDate()));
        mTimeButton.setText(df.format("HH:mm aa", mCrime.getDate()));

    }

    public static CrimeFragment newInstance(UUID crimeId){
        Bundle args = new Bundle();
        args.putSerializable(ARG_CRIME_ID, crimeId);

        CrimeFragment fragment = new CrimeFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
