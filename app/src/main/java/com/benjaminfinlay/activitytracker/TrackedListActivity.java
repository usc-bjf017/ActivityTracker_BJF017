package com.benjaminfinlay.activitytracker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;

/**
 * Launches the List Fragment to view a list of Tracked Activity's
 * Gets the current location when opened. To be used for new Activity's
 */
public class TrackedListActivity extends SingleFragmentActivity {

    public static Location startupLocation;

    /**
     * Launches the List Fragment to view a list of Tracked Activity's
     * Gets the current location when opened. To be used for new Activity's
     * @return The new Fragment created.
     */
    @Override
    protected Fragment createFragment() {
        // Gets the current location on app launch for when new activity's are created.
        startupLocation = getLocation();

        return new TrackedListFragment();
    }

    /**
     * Gets the current location on app launch for when new activity's are created.
     * @return The current location of the device.
     */
    private Location getLocation() {
        // New location manager to find location.
        LocationManager foundLocation = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Gets new criteria to get provider
        Criteria criteria = new Criteria();

        // Get the name of the needed provider as a String.
        String provider = foundLocation.getBestProvider(criteria, true);

        // Checks if the phone has given permission for getting the location.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Checks if current android build supports this type of location get.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                // If the phone has not given permission request permission.
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                return null;
            }
        }

        // Gets the last known location of the phone from the provider.
        Location myLocation = foundLocation.getLastKnownLocation(provider);

        // Only returns the location if a location is found.
        if (myLocation != null) {
            return myLocation;
        } else {
            return null;
        }
    }
}
