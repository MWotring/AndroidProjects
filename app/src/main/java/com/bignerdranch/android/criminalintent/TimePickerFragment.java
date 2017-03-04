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
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by megan.wotring on 1/12/17.
 */

public class TimePickerFragment extends DialogFragment {
    private static final String TAG = "CriminalIntent";
    private static final String ARG_TIME = "time";
    public static final String EXTRA_HOUR = "com.bignerdranch.android.criminalintent.hour";
    public static final String EXTRA_MINUTE = "com.bignerdranch.android.criminalintent.minute";
    private TimePicker mTimePicker;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        Date date = (Date) getArguments().getSerializable(ARG_TIME);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        Log.d(TAG, "TimePickerFragment - onCreateDialog");
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time, null);
        mTimePicker = (TimePicker) v.findViewById(R.id.dialog_time_time_picker);
         //no init method for TimePicker

        return new AlertDialog.Builder(getActivity()).setView(v).setTitle(R.string.time_picker_title).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int hour = mTimePicker.getHour();
                int minute = mTimePicker.getMinute();
                Log.d(TAG, "TimePickerFragment - The time is " + hour + ":" + minute);

                sendResult(Activity.RESULT_OK, hour, minute);
            }
        }).create();
    }

    public static TimePickerFragment newInstance(Date time){
        Bundle args = new Bundle();
        args.putSerializable(ARG_TIME, time);
        Log.d(TAG, "TimePickerFragment - newInstance time " + time);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void sendResult(int resultCode, int hour, int minute){
        if (getTargetFragment() == null){
            return;
        }
        Log.d(TAG, "TimePickerFragment - sendResult fcn " + hour + ':' + minute);
        Intent intent = new Intent();
        intent.putExtra(EXTRA_HOUR, hour);
        intent.putExtra(EXTRA_MINUTE, minute);
        Log.d(TAG, "TimePickerFragment - resultCode to send " + resultCode + " requ code " + getTargetRequestCode());
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);
    }
}
