package com.benjaminfinlay.activitytracker;

import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListFragment extends Fragment {
    ListView activityList;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_list, container, false);
        TrackedActivity[] activities = GenerateTemp();
        activityList = view.findViewById(R.id.list_listview);
        CustomAdapter adapter = new CustomAdapter(getActivity(), activities);
        activityList.setAdapter(adapter);
        activityList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Click Item at position: " + position, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private TrackedActivity[]  GenerateTemp() {
        TrackedActivity[] activitys = new TrackedActivity[20];
        Location currentLocation = MainActivity.startupLocation;

        for(int i = 0; i < 20; i++) {
            TrackedActivity activity = new TrackedActivity(currentLocation);
            activity.setActivityTitle("Yep");
            activity.setActivityDetails("So this is something");
            activity.setActivityPlace("CandyLand");
            activitys[i] = activity;
        }

        return activitys;
    }
}
