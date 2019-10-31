package com.benjaminfinlay.activitytracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.DatePicker;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Fragment to view a date picker.
 */
public class DatePickerFragment extends DialogFragment {

    public static final String EXTRA_DATE = "date";
    private Date mDate;

    /**
     * Triggered on new instance of the fragment
     * Sets the date the dialog/popup needs to open to.
     * @param date Date from the Tracked Activity.
     * @return Returns the new instance of the date picker.
     */
    public static DatePickerFragment newInstance(Date date) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_DATE, date);
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Sends the new date once the user has finish changing the date.
     * @param resultCode The result code to be sent through. Caught by TrackedFragment.
     */
    private void sendResult(int resultCode) {
        if (getTargetFragment() == null)
            return;
        Intent i = new Intent();
        i.putExtra(EXTRA_DATE, mDate);
        getTargetFragment().onActivityResult(getTargetRequestCode(), resultCode, i);
    }

    /**
     * Triggered when the date popup/dialog is opened.
     * Opens the date picker to the Tracked Activity's current set date.
     * Sets up the OK button for when the user is finished.
     * @param savedInstanceState App's compiled code and resources.
     * @return
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        mDate = (Date)getArguments().getSerializable(EXTRA_DATE);
        // Create a Calendar to get the year, month, and day
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mDate);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.dialog_date, null);

        DatePicker datePicker = (DatePicker)v.findViewById(R.id.dialog_date_datePicker);
        datePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            public void onDateChanged(DatePicker view, int year, int month, int day) {
                // Translate year, month, day into a Date object using a calendar
                mDate = new GregorianCalendar(year, month, day).getTime();
                // Update argument to preserve selected value on rotation
                getArguments().putSerializable(EXTRA_DATE, mDate);
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.date_picker_title)
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                sendResult(Activity.RESULT_OK);
                            }
                        })
                .create();
    }
}
