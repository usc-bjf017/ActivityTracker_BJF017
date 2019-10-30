package com.benjaminfinlay.activitytracker;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.view.Display;

import java.util.Date;
import java.util.UUID;

public class Tracked {

    private int mId;
    private UUID mUUID;
    private String mTitle;
    private Date mDate;
    private String mPlace;
    private String mDetails;
    private Location mLocation;
    private String mImagePath;

    public Tracked() {
        // Generate unique identifier
        mUUID = UUID.randomUUID();
        mDate = new Date();
        mLocation = TrackedListActivity.startupLocation;
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

    public UUID getUUID() {
        return mUUID;
    }

    public void setUUID(UUID id) {
        this.mUUID = id;
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

    public void setLocation(double latitude, double longitude) {
        Location newLocation = new Location("");
        newLocation.setLatitude(latitude);
        newLocation.setLongitude(longitude);
        mLocation = newLocation;
    }

    public String getImagePath() { return mImagePath; }

    public void setImagePath(String imagePath) { this.mImagePath = imagePath; }
}
