package com.benjaminfinlay.activitytracker;

import android.location.Location;

import java.util.Date;
import java.util.UUID;

public class Tracked {

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private String mPlace;
    private String mDetails;
    private Location mLocation;

    public Tracked() {
        // Generate unique identifier
        mId = UUID.randomUUID();
        mDate = new Date();
        mLocation = TrackedListActivity.startupLocation;
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date date) {
        this.mDate = date;
    }

    public String getPlace() { return mPlace; }

    public void setPlace(String place) { this.mPlace = place; }

    public String getDetails() { return mDetails; }

    public void setDetails(String details) { this.mDetails = details; }

    public Location getLocation() { return mLocation; }
}
