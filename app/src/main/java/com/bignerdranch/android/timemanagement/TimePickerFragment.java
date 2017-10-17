package com.bignerdranch.android.timemanagement;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by mitchellhayward on 9/10/17.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private static final String ARG_HOUR = "hour";
    private static final String ARG_MIN = "min";

    public static final String EXTRA_HOUR = "com.bignerdranch.android.timemanagement.hour";
    public static final String EXTRA_MIN = "com.bignerdranch.android.timemanagement.min";

    private TimePicker mTimePicker;

    private Time mTime;

    public static TimePickerFragment newTimePicker(int hourOfDay, int minute) {
        Bundle newArgs = new Bundle();
        newArgs.putSerializable(ARG_HOUR, hourOfDay);
        newArgs.putSerializable(ARG_MIN, minute);
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setArguments(newArgs);
        return newFragment;

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        
        // Use the current time as the default values for the picker

        final Calendar c = Calendar.getInstance();
        final int hour = c.get(Calendar.HOUR_OF_DAY);
        final int minute = c.get(Calendar.MINUTE);

        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_duration, null);

        mTimePicker = (TimePicker)v.findViewById(R.id.duration_picker);
        mTimePicker.setIs24HourView(true);

        return new TimePickerDialog(getActivity(), this, hour, minute, true);

    }

    @Override
    public void onTimeSet(TimePicker view, final int hourOfDay, int minute) {

        Button mTimeButton = (Button)getActivity().findViewById(R.id.duration_button);

        //mTimeButton.setText(String.valueOf(hourOfDay) + " HOURS " + String.valueOf(minute) + " MINUTES");

        sendResult(Activity.RESULT_OK, hourOfDay, minute);

    }

    private void sendResult(int resultCode, int hourOfDay, int minute){

        if (getTargetFragment() == null) {
            return;
        }

        Intent intent = new Intent();
        intent.putExtra(EXTRA_HOUR, hourOfDay);
        intent.putExtra(EXTRA_MIN, minute);

        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, intent);

    }

}
