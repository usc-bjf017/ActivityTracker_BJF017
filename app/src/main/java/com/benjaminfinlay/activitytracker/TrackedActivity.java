package com.benjaminfinlay.activitytracker;

import android.location.Location;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class TrackedActivity {
    private UUID activityID;
    private String activityTitle;
    private String activityPlace;
    private Date activityDate;
    private String activityDetails;
    private Location activityLocation;

    public TrackedActivity(Location currentLocation) {
        activityID = UUID.randomUUID();
        activityDate = Calendar.getInstance().getTime();
        activityLocation = currentLocation;
    }

    //--------------------------------------------------
    // GETTERS
    //--------------------------------------------------
    public UUID getActivityID() {
        return activityID;
    }

    public String getActivityTitle() {
        return activityTitle;
    }

    public String getActivityPlace() {
        return activityPlace;
    }

    public Date getActivityDate() {
        return activityDate;
    }

    public String getActivityDetails() {
        return activityDetails;
    }

    public Location getActivityLocation() {
        return activityLocation;
    }

    //--------------------------------------------------
    // SETTERS
    //--------------------------------------------------
    public void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    public void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    public void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    public void setActivityDetails(String activityDetails) {
        this.activityDetails = activityDetails;
    }

    public void setActivityLocation(Location activityLocation) {
        this.activityLocation = activityLocation;
    }
}
