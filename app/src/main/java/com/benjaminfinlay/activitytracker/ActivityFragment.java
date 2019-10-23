package com.benjaminfinlay.activitytracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    private static final String TITLE = "Unknown";
    private static final String PLACE = "Unknown";
    private static final String DETAILS = "Unknown";
    private static final String DATE = "Unknown";
    private static final String LATITUDE = "Unknown";
    private static final String LONGITUDE = "Unknown";

    public ActivityFragment() {
        // Required empty public constructor
    }

    public static ActivityFragment newInstance(TrackedActivity trackedActivity) {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        if (trackedActivity.getActivityTitle() != null) {
            args.putString(TITLE, trackedActivity.getActivityTitle());
        }
        if (trackedActivity.getActivityPlace() != null) {
            args.putString(PLACE, trackedActivity.getActivityPlace());
        }
        if (trackedActivity.getActivityDetails() != null) {
            args.putString(DETAILS, trackedActivity.getActivityDetails());
        }
        if (trackedActivity.getActivityDate() != null) {
            Format formatter = new SimpleDateFormat("dd-MM-yyyy");
            args.putString(DATE, formatter.format(trackedActivity.getActivityDate()));
        }
        if (trackedActivity.getActivityLocation() != null) {
            args.putString(LATITUDE, String.valueOf(trackedActivity.getActivityLocation().getLatitude()));
            args.putString(LONGITUDE, String.valueOf(trackedActivity.getActivityLocation().getLongitude()));
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_activity, container, false);

        TextView text_date = (TextView) view.findViewById(R.id.button_date);
        text_date.setText(DATE);
        TextView text_location = (TextView) view.findViewById(R.id.text_map_location);
        text_location.setText("Latitude: " + LATITUDE + " Longitude: " + LONGITUDE);

        return view;
    }
}
