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

public class TrackedListActivity extends SingleFragmentActivity {
    public static Location startupLocation;

    //SQLiteDatabaseHelper db = new SQLiteDatabaseHelper(this);

    @Override
    protected Fragment createFragment() {
        startupLocation = getLocation();

        return new TrackedListFragment();
    }

    private Location getLocation() {
        // Get LocationManager object
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Create a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Get the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET}, 10);
                return null;
            }
        }

        Location myLocation = locationManager.getLastKnownLocation(provider);
        if (myLocation != null) {
            return myLocation;
        } else {
            return null;
        }

        //myLatitude = myLocation.getLatitude();
        //myLongitude = myLocation.getLongitude();
    }
}
