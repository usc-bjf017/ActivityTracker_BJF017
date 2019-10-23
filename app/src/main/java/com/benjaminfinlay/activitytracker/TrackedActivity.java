package com.benjaminfinlay.activitytracker;

import android.location.Location;
import android.os.Parcelable;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

class TrackedActivity {
    private UUID activityID;
    private String activityTitle;
    private String activityPlace;
    private Date activityDate;
    private String activityDetails;
    private Location activityLocation;

    TrackedActivity(Location currentLocation) {
        activityID = UUID.randomUUID();
        activityDate = Calendar.getInstance().getTime();
        activityLocation = currentLocation;
    }

    //--------------------------------------------------
    // GETTERS
    //--------------------------------------------------
    UUID getActivityID() {
        return activityID;
    }

    String getActivityTitle() {
        return activityTitle;
    }

    String getActivityPlace() {
        return activityPlace;
    }

    Date getActivityDate() {
        return activityDate;
    }

    String getActivityDetails() {
        return activityDetails;
    }

    Location getActivityLocation() {
        return activityLocation;
    }

    //--------------------------------------------------
    // SETTERS
    //--------------------------------------------------
    void setActivityTitle(String activityTitle) {
        this.activityTitle = activityTitle;
    }

    void setActivityPlace(String activityPlace) {
        this.activityPlace = activityPlace;
    }

    void setActivityDate(Date activityDate) {
        this.activityDate = activityDate;
    }

    void setActivityDetails(String activityDetails) { this.activityDetails = activityDetails; }

    void setActivityLocation(Location activityLocation) { this.activityLocation = activityLocation; }
}
