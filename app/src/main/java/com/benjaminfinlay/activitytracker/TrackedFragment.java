package com.benjaminfinlay.activitytracker;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * Main fragment for viewing and editing information on Tracked Activity's
 */
public class TrackedFragment extends Fragment {

    public static final String EXTRA_TRACKED_ID = "tracked_id";
    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    private Tracked mTracked;
    private Button mDateButton;
    private ImageView mImageView;

    /**
     * Triggered when the fragment is created.
     * Gets the UUID of the Tracked Activity that needs to be opened and retrieves the data.
     * @param savedInstanceState App's compiled code and resources.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID trackedId = (UUID)getArguments().getSerializable(EXTRA_TRACKED_ID);
        mTracked = TrackedManager.get(getActivity()).getTracked(trackedId);
    }

    /**
     * Triggered when the Fragment view is created.
     * Handles button, text fields.. etc
     * Controlling saving, display and input of the Tracked Activity's information.
     * @param inflater To create view instance based on XML file.
     * @param parent Base view to create the view instance in.
     * @param savedInstanceState App's compiled code and resources.
     * @return Return the new view created.
     */
    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tracked, parent, false);

        // Sets up back button. To return to activity list
        if (NavUtils.getParentActivityName(getActivity()) != null) {
            getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Title field to display and control activity title
        EditText mTitleField = (EditText) v.findViewById(R.id.tracked_title);
        mTitleField.setText(mTracked.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                mTracked.setTitle(c.toString());
            }
            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                // This space intentionally left blank
            }
            public void afterTextChanged(Editable c) {
                TrackedManager.get(getActivity()).updateTracked(mTracked);
            }
        });

        // Place field to display and control activity place
        EditText mPlaceField = (EditText) v.findViewById(R.id.tracked_place);
        mPlaceField.setText(mTracked.getPlace());
        mPlaceField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                mTracked.setPlace(c.toString());
            }
            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                // This space intentionally left blank
            }
            public void afterTextChanged(Editable c) {
                TrackedManager.get(getActivity()).updateTracked(mTracked);
            }
        });

        // Date field to display and control activity date
        mDateButton = (Button)v.findViewById(R.id.tracked_date);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        mDateButton.setText(formatter.format(mTracked.getDate()));
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTracked.getDate());
                dialog.setTargetFragment(TrackedFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        // Details field to display and control activity details
        EditText mDetailsField = (EditText) v.findViewById(R.id.tracked_details);
        mDetailsField.setText(mTracked.getDetails());
        mDetailsField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(
                    CharSequence c, int start, int before, int count) {
                mTracked.setDetails(c.toString());
            }
            public void beforeTextChanged(
                    CharSequence c, int start, int count, int after) {
                // This space intentionally left blank
            }
            public void afterTextChanged(Editable c) {
                TrackedManager.get(getActivity()).updateTracked(mTracked);
            }
        });

        // Location field to display and control activity location
        TextView mLocationText = (TextView) v.findViewById(R.id.tracked_map_location);
        String newLocationString = "Latitude: " + mTracked.getLocation().getLatitude() + "\nLongitude: " + mTracked.getLocation().getLongitude();
        mLocationText.setText(newLocationString);

        // Map button to take the user to the Map View of the tracked activity's location
        Button mMapButton = (Button) v.findViewById(R.id.tracked_map);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Convert the location to a geo string
                String posToString = "geo:" + mTracked.getLocation().getLatitude() + "," + mTracked.getLocation().getLongitude();
                // Create a Uri from an intent string. Use the result to create an Intent.
                Uri gmmIntentUri = Uri.parse(posToString);
                // Create an Intent from gmmIntentUri. Set the action to ACTION_VIEW
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                // Make the Intent explicit by setting the Google Maps package
                mapIntent.setPackage("com.google.android.apps.maps");
                // Attempt to start an activity that can handle the Intent
                startActivity(mapIntent);
            }
        });

        // Photo button takes the user to take a new image that will be added to the Tracked Activity
        Button mPhotoButton = (Button) v.findViewById(R.id.tracked_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TrackedCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        // Get the reference to the Image view to display the captured photo
        mImageView = (ImageView)v.findViewById(R.id.tracked_imageView);

        // Share button takes the user to share some of the basic information of the Tracked Activity
        Button mShareButton = (Button) v.findViewById(R.id.tracked_share);
        mShareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, getTrackedActivityReport());
                i.putExtra(Intent.EXTRA_SUBJECT, "Tracked Activity");
                i = Intent.createChooser(i, "Send");
                startActivity(i);
            }
        });

        // Delete button deletes the tracked activity from the SQLite Database and returns the user to the Tracked Activity List UI.
        // Only visible after the activity has been viewed/created for the first time.
        Button mDeleteButton = (Button) v.findViewById(R.id.tracked_delete);
        if (mTracked.getTitle() == null || mTracked.getTitle().equals("")) {
            mDeleteButton.setVisibility(View.INVISIBLE);
        } else {
            mDeleteButton.setVisibility(View.VISIBLE);
        }
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TrackedManager.get(getActivity()).updateTracked(mTracked);
                UUID trackedId = (UUID)getArguments().getSerializable(EXTRA_TRACKED_ID);
                TrackedManager.get(getActivity()).removeTracked(trackedId);

                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
            }
        });

        // If camera is not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) || pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) || Camera.getNumberOfCameras() > 0;
        if (!hasACamera) {
            mPhotoButton.setEnabled(false);
        }

        return v;
    }

    /**
     * Used to create a new instance of the Tracked Fragment view.
     * @param trackedId UUID of the tracked activity that need to be viewed in Tracked Fragment.
     * @return Returns the new Tracked Fragment to be viewed.
     */
    public static TrackedFragment newInstance(UUID trackedId) {
        Bundle args = new Bundle();
        // Adds the activity id to the Fragment that needs to be viewed and edited when it opens.
        args.putSerializable(EXTRA_TRACKED_ID, trackedId);
        TrackedFragment fragment = new TrackedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Gets the basic report of the Tracked Activity to be shared. (Share Button)
     * Messenger, Email, Tweet etc...
     * @return Return the string of the report.
     */
    private String getTrackedActivityReport() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return    "Activity: " + mTracked.getTitle() + "\n"
                + "Date: " + formatter.format(mTracked.getDate()) + "\n"
                + "Place: " + mTracked.getPlace() + "\n"
                + "Details: " + mTracked.getDetails() + "\n"
                + "Location: " + mTracked.getLocation().getLatitude() + " " + mTracked.getLocation().getLongitude();
    }

    /**
     * Gets the results of the button popups to be added to the Tracked Activity information.
     * @param requestCode Type of data found (Date or Photo)
     * @param resultCode The result the user has selected.
     * @param data The data gotten.
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // OK button in data popup
        if (resultCode != Activity.RESULT_OK) return;
        // Date popup data
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTracked.setDate(date);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            mDateButton.setText(formatter.format(mTracked.getDate()));
            TrackedManager.get(getActivity()).updateTracked(mTracked);
        } else if (requestCode == REQUEST_PHOTO) // Photo data
        {
            // Take the new photos filepath and add it to the SQLite Database and show image in current Tracked Activity.
            String filename = data.getStringExtra(TrackedCameraFragment.EXTRA_PHOTO_FILENAME);
            if (filename != null) {
                String path = getActivity().getFileStreamPath(filename).getAbsolutePath();
                mTracked.setImagePath(path);
                showPhoto();
            }
        }
    }

    /**
     * Menu bar options selected
     * @param item Button on menu bar selected
     * @return Return if the button press has been handled
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Home button to take the user back to the List UI
            case android.R.id.home:
                TrackedManager.get(getActivity()).updateTracked(mTracked);
                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                TrackedManager.get(getActivity()).updateTracked(mTracked);
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Display the photo from the Tracked Activity in the Fragment Image View
     */
    private void showPhoto() {
        TrackedManager.get(getActivity()).updateTracked(mTracked);
        // (Re)set the image button's image based on our photo
        String p = mTracked.getImagePath();
        BitmapDrawable b = null;
        if (p != null) {
            b = PictureUtils.getScaledDrawable(getActivity(), p);
            mImageView.setImageDrawable(b);
        }
    }

    /**
     * Triggered as soon as the fragment starts to prepare the image to be viewed.
     */
    @Override
    public void onStart() {
        super.onStart();
        showPhoto();
    }

    /**
     * Triggered when fragment stops to recycle image data effectively.
     */
    @Override
    public void onStop() {
        super.onStop();
        PictureUtils.cleanImageView(mImageView);
    }
}
