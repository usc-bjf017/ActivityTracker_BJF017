package com.benjaminfinlay.activitytracker;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {
    private final Activity context;
    private final List<TrackedActivity> activities;

    public CustomAdapter(Activity context, List<TrackedActivity> activities) {
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

        titleInput.setText(activities.get(position).getActivityTitle());
        placeInput.setText(activities.get(position).getActivityPlace());

        Format formatter = new SimpleDateFormat("dd-MM-yyyy");
        dateInput.setText(formatter.format(activities.get(position).getActivityDate()));

        return itemView;
    }
}
