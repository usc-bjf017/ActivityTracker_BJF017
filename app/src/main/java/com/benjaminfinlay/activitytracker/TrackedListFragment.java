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
import java.util.Stack;

/**
 * Controls a list of Tracked Activity's to be viewed and clicked on.
 * Controls menu bar on the list page.
 */
public class TrackedListFragment extends ListFragment {

    /**
     * Triggered when the Fragment is created
     * @param savedInstanceState App's compiled code and resources.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        getActivity().setTitle(R.string.tracked_title);
        Stack<Tracked> mTracked = TrackedManager.get(getActivity()).getAllTracked();

        TrackedAdapter adapter = new TrackedAdapter(mTracked);
        setListAdapter(adapter);
    }

    /**
     * Triggers when a Tracked Activity is clicked on the list. Opening the Tracked Activity in
     * a larger view with the remaining information.
     * @param l List that the list item is in.
     * @param v Current view the list is in.
     * @param position Position of list item in list. To be used to get the Tracked Activity at position in data base.
     * @param id Id of list item.
     */
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Tracked a = ((TrackedAdapter)getListAdapter()).getItem(position);

        // Start CrimePagerActivity with this crime
        Intent i = new Intent(getActivity(), TrackedPagerActivity.class);
        if (a != null) {
            i.putExtra(TrackedFragment.EXTRA_TRACKED_ID, a.getUUID());
        }
        startActivity(i);
    }

    /**
     * Custom adapter to list custom list items.
     * Card view displaying:
     * - Title
     * - Place
     * - Date
     */
    private class TrackedAdapter extends ArrayAdapter<Tracked> {

        /**
         * Constructor for custom adapter
         * @param tracked Stack of tracked activity's to be added to the list.
         */
        TrackedAdapter(Stack<Tracked> tracked) {
            super(getActivity(), 0, tracked);
        }

        /**
         * Creates or gets an old view for each of the list items.
         * @param position Position of new item in list in Stack/List.
         * @param convertView Converts an old view to be used on the list again.
         * @param parent The view that the list is a child of.
         * @return Returns the new list items view.
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // If we weren't given a view, inflate one
            if (convertView == null) {
                convertView = getActivity().getLayoutInflater().inflate(R.layout.list_item_tracked, null);
            }
            // Configure the view for this tracked activity
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

    /**
     * Runs every time the List Fragment is opened or resumed. E.g. if the screen is locked then reopened.
     */
    @Override
    public void onResume() {
        super.onResume();
        ((TrackedAdapter)getListAdapter()).notifyDataSetChanged();
    }

    /**
     * Triggered to create the menu bar at the top of the page
     * @param menu Menu
     * @param inflater Used to inflate the menu XML.
     */
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_tracked_list, menu);
    }

    /**
     * Triggered when an item on the menu bar is clicked.
     * @param item Menu item that was clicked
     * @return Returns true or false if the click was handled.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Button used to create a new activity and open Tracked Pager view with new activity
            case R.id.menu_activity:
                Tracked tracked = new Tracked();
                TrackedManager.get(getActivity()).addTracked(tracked);
                Intent i = new Intent(getActivity(), TrackedPagerActivity.class);
                i.putExtra(TrackedFragment.EXTRA_TRACKED_ID, tracked.getUUID());
                startActivityForResult(i, 0);
                return true;
            // Sends the user to the help page, launching the TrackedWebActivity and Fragment.
            case R.id.menu_help:
                Intent a = new Intent(getActivity(), TrackedWebActivity.class);
                startActivityForResult(a, 0);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
