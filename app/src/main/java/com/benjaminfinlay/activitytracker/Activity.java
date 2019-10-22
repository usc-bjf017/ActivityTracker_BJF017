package com.benjaminfinlay.activitytracker;

import android.location.Location;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class Activity {
    private UUID activityID;
    private String activityTitle;
    private Date activityDate;
    private String activityDetails;
    private Location activityLocation;

    public Activity(Location currentLocation) {
        activityID = UUID.randomUUID();
        activityDate = Calendar.getInstance().getTime();
        activityLocation = currentLocation;
    }
}
