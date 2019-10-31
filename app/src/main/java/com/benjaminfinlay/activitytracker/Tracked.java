package com.benjaminfinlay.activitytracker;

import android.location.Location;
import java.util.Date;
import java.util.UUID;

/**
 * Tracked class to hold all the variables for the a Tracked Activity
 */
public class Tracked {

    private int mId;
    private UUID mUUID;
    private String mTitle;
    private Date mDate;
    private String mPlace;
    private String mDetails;
    private Location mLocation;
    private String mImagePath;

    /**
     * Constructor for Tracked.
     * Random UUID
     * Current Date
     * Current Location
     */
    Tracked() {
        mUUID = UUID.randomUUID(); // Generate a random UUID when Tracked is created.
        mDate = new Date(); // Sets the date to the current date.
        mLocation = TrackedListActivity.startupLocation; // Sets the location to the location of the device when the app opened.
    }

    @Override
    public String toString() {
        return mTitle;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        this.mId = id;
    }

    UUID getUUID() {
        return mUUID;
    }

    void setUUID(UUID id) {
        this.mUUID = id;
    }

    String getTitle() {
        return mTitle;
    }

    void setTitle(String title) {
        this.mTitle = title;
    }

    Date getDate() {
        return mDate;
    }

    void setDate(Date date) {
        this.mDate = date;
    }

    String getPlace() { return mPlace; }

    void setPlace(String place) { this.mPlace = place; }

    String getDetails() { return mDetails; }

    void setDetails(String details) { this.mDetails = details; }

    Location getLocation() { return mLocation; }

    void setLocation(double latitude, double longitude) {
        Location newLocation = new Location("");
        newLocation.setLatitude(latitude);
        newLocation.setLongitude(longitude);
        mLocation = newLocation;
    }

    String getImagePath() { return mImagePath; }

    void setImagePath(String imagePath) { this.mImagePath = imagePath; }
}
