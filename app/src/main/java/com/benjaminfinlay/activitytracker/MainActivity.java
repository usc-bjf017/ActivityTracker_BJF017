package com.benjaminfinlay.activitytracker;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static List<TrackedActivity> activityList = new ArrayList<TrackedActivity>();
    public static Location startupLocation;
    private TrackedActivity currentActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startupLocation = GetLocation();
        GenerateTemp();
        setContentView(R.layout.main_activity);
    }

    private void GenerateTemp() {
        for(int i = 0; i < 20; i++) {
            TrackedActivity activity = new TrackedActivity(startupLocation);
            activity.setActivityTitle("Yep");
            activity.setActivityDetails("So this is something");
            activity.setActivityPlace("CandyLand");
            activityList.add(activity);
        }
    }

    private void OpenNewActivityPanel() {
        currentActivity = new TrackedActivity(GetLocation());
        ActivityFragment fragment = ActivityFragment.newInstance(currentActivity);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.add(R.id.fragment_list, fragment, "ACTIVITY_FRAGMENT").commit();
    }

    public void ActivityButton_Back(android.view.View v){
        Toast.makeText(this, "Back button pressed", Toast.LENGTH_SHORT).show();
    }

    public void ActivityButton_Date(android.view.View v){
        Toast.makeText(this, "Date button pressed", Toast.LENGTH_SHORT).show();
    }

    public void ActivityButton_Map(android.view.View v){
        Toast.makeText(this, "Map button pressed", Toast.LENGTH_SHORT).show();
    }

    public void ActivityButton_Photo(android.view.View v){
        Toast.makeText(this, "Photo button pressed", Toast.LENGTH_SHORT).show();
    }

    public void ActivityButton_Share(android.view.View v){
        Toast.makeText(this, "Share button pressed", Toast.LENGTH_SHORT).show();
    }

    public void ListButton_New(android.view.View v){
        Toast.makeText(this, "New button pressed", Toast.LENGTH_SHORT).show();
        OpenNewActivityPanel();
    }

    public void ListButton_Help(android.view.View v){
        Toast.makeText(this, "Help button pressed", Toast.LENGTH_SHORT).show();
    }

    public void ListButton_Item(int position) {
        Toast.makeText(this, "Click Item at position: " + position, Toast.LENGTH_SHORT).show();
    }

    public Location GetLocation() {
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
