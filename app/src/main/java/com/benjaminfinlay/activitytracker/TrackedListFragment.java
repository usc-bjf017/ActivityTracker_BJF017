package com.benjaminfinlay.activitytracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Stack;

public class TrackedListFragment extends ListFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.tracked_title);
        Stack<Tracked> mTracked = TrackedManager.get(getActivity()).getAllTracked();

        TrackedAdapter adapter = new TrackedAdapter(mTracked);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Tracked a = ((TrackedAdapter)getListAdapter()).getItem(position);

        // Start CrimePagerActivity with this crime
        Intent i = new Intent(getActivity(), TrackedPagerActivity.class);
        i.putExtra(TrackedFragment.EXTRA_TRACKED_ID, a.getId());
        startActivity(i);
    }

    private class TrackedAdapter extends ArrayAdapter<Tracked> {
        public TrackedAdapter(Stack<Tracked> tracked) {
            super(getActivity(), 0, tracked);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater()
                        .inflate(R.layout.list_item_tracked, null);
            }
            // Configure the view for this Crime
            Tracked a = getItem(position);

            // Title Input
            TextView titleTextView = (TextView)convertView.findViewById(R.id.tracked_list_item_titleTextView);
            titleTextView.setText(a.getTitle());

            // Place Input
            TextView placeTextView = (TextView)convertView.findViewById(R.id.tracked_list_item_placeTextView);
            placeTextView.setText(a.getPlace());

            // Date Input
            TextView dateTextView = (TextView)convertView.findViewById(R.id.tracked_list_item_dateTextView);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            dateTextView.setText(formatter.format(a.getDate()));

            return convertView;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        ((TrackedAdapter)getListAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_tracked_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_activity:
                Tracked tracked = new Tracked();
                TrackedManager.get(getActivity()).addTracked(tracked);
                Intent i = new Intent(getActivity(), TrackedPagerActivity.class);
                i.putExtra(TrackedFragment.EXTRA_TRACKED_ID, tracked.getId());
                startActivityForResult(i, 0);
                return true;
            case R.id.menu_help:
                // TODO add the help menu
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
