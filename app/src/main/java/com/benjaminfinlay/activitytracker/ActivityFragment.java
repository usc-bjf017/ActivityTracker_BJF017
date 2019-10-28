package com.benjaminfinlay.activitytracker;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityFragment extends Fragment {
    private static TrackedActivity currentTrackedActivity;

    private static final int REQUEST_DATE = 0;

    private TextView dateButton;

    public ActivityFragment() {
        // Required empty public constructor
    }

    public static ActivityFragment newInstance(TrackedActivity trackedActivity) {
        ActivityFragment fragment = new ActivityFragment();
        if (trackedActivity != null) {
            currentTrackedActivity = trackedActivity;
        }
        return fragment;
    }

    private void updateDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        dateButton.setText(formatter.format(currentTrackedActivity.getActivityDate()));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        dateButton = view.findViewById(R.id.button_date);
        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(currentTrackedActivity.getActivityDate());
                dialog.setTargetFragment(ActivityFragment.this, REQUEST_DATE);
                dialog.show(fragmentManager, "date");
            }
        });
        updateDate();

        TextView text_location = view.findViewById(R.id.text_map_location);
        text_location.setText("Latitude: " + currentTrackedActivity.getActivityLocation().getLatitude() +"\nLongitude: " + currentTrackedActivity.getActivityLocation().getLongitude());

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            currentTrackedActivity.setActivityDate(date);
            updateDate();
        }
    }
}
