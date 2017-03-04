package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by megan.wotring on 1/10/17.
 */

public class DatePickerFragment extends DialogFragment {
    private static final String TAG = "CriminalIntent";
    private static final String ARG_DATE = "date";
    public static final String EXTRA_DATE ="com.bignerdranch.android.criminalintent.date";
    private DatePicker mDatePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){ //called by FragmentManager of host activity

        Date date = (Date) getArguments().getSerializable(ARG_DATE); //get date, then use Calendar to turn date into int format needed
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Log.d(TAG, "DatePickerFragment - onCreateDialog");

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date, null);
        mDatePicker = (DatePicker) v.findViewById(R.id.dialog_date_date_picker);
        mDatePicker.init(year, month, day, null); //use int date components to initialize new DatePicker, tell it what to show

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.date_picker_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int year = mDatePicker.getYear();
                int month = mDatePicker.getMonth();
                int day = mDatePicker.getDayOfMonth();
                Date date = new GregorianCalendar(year, month, day).getTime();
                sendResult(Activity.RESULT_OK, date); //send date entered to CrimeFragment

            }
        }).create();
    }

    public static DatePickerFragment newInstance(Date date){ //get Crime's date to DatePickerFragment by stashing it in Bundle
        Bundle args = new Bundle();
        args.putSerializable(ARG_DATE, date);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        Log.d(TAG, "DatePickerFragment - newInstance - bundle date " + date);
        return fragment;
    }

    private void sendResult(int resultCode, Date date){ //use intent to send date to CrimeFragment
        if (getTargetFragment() == null){
            return;
        }
        Log.d(TAG, "DatePickerFragment - sendResult resultCode to send " + resultCode + " requ code " + getTargetRequestCode());
        Intent intent = new Intent();
        intent.putExtra(EXTRA_DATE, date);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
