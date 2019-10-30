package com.benjaminfinlay.activitytracker;

public class Photo {
    private String mFilename;

    /** Create a Photo representing an existing file on disk */
    public Photo(String filename) {
        mFilename = filename;
    }

    public String getFilename() {
        return mFilename;
    }
}
