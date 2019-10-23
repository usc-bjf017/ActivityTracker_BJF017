package com.benjaminfinlay.activitytracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;

public class CustomAdapter extends ArrayAdapter {
    private final Activity context;
    private final TrackedActivity[] activities;

    public CustomAdapter(Activity context, TrackedActivity[] activities) {
        super(context, R.layout.list_item, activities);

        this.context = context;
        this.activities = activities;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View itemView = inflater.inflate(R.layout.list_item, null,true);

        TextView titleInput = (TextView) itemView.findViewById(R.id.itemTextTitle);
        TextView placeInput = (TextView) itemView.findViewById(R.id.itemTextPlace);
        TextView dateInput = (TextView) itemView.findViewById(R.id.itemTextDate);

        titleInput.setText(activities[position].getActivityTitle());
        placeInput.setText(activities[position].getActivityPlace());

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        dateInput.setText(formatter.format(activities[position].getActivityDate()));

        return itemView;
    }
}
