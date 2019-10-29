package com.benjaminfinlay.activitytracker;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
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
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class TrackedFragment extends Fragment {
    private static final String TAG = "TrackedFragment";
    public static final String EXTRA_TRACKED_ID = "tracked_id";

    private static final String DIALOG_DATE = "date";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_PHOTO = 1;

    private Tracked mTracked;
    private EditText mTitleField;
    private EditText mPlaceField;
    private Button mDateButton;
    private EditText mDetailsField;
    private TextView mLocationText;
    private Button mMapButton;
    private Button mPhotoButton;
    private Button mShareButton;
    private Button mDeleteButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID trackedId = (UUID)getArguments().getSerializable(EXTRA_TRACKED_ID);
        mTracked = TrackedManager.get(getActivity()).getTracked(trackedId);
    }

    private void updateDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        mDateButton.setText(formatter.format(mTracked.getDate()));
    }

    @TargetApi(11)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tracked, parent, false);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            if (NavUtils.getParentActivityName(getActivity()) != null) {
                getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        mTitleField = (EditText)v.findViewById(R.id.tracked_title);
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
            // This one too
            }
        });

        mPlaceField = (EditText)v.findViewById(R.id.tracked_place);
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
                // This one too
            }
        });

        mDateButton = (Button)v.findViewById(R.id.tracked_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                FragmentManager fm = getActivity()
                        .getSupportFragmentManager();
                DatePickerFragment dialog = DatePickerFragment
                        .newInstance(mTracked.getDate());
                dialog.setTargetFragment(TrackedFragment.this, REQUEST_DATE);
                dialog.show(fm, DIALOG_DATE);
            }
        });

        mDetailsField = (EditText)v.findViewById(R.id.tracked_details);
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
                // This one too
            }
        });

        mLocationText = (TextView) v.findViewById(R.id.tracked_map_location);
        String newLocationString = "Latitude: " + mTracked.getLocation().getLatitude() + "\nLongitude: " + mTracked.getLocation().getLongitude();
        mLocationText.setText(newLocationString);

        mMapButton = (Button)v.findViewById(R.id.tracked_map);
        mMapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent i = new Intent(getActivity(), TrackedCameraActivity.class);
                //startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        mPhotoButton = (Button)v.findViewById(R.id.tracked_imageButton);
        mPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), TrackedCameraActivity.class);
                startActivityForResult(i, REQUEST_PHOTO);
            }
        });

        mShareButton = (Button)v.findViewById(R.id.tracked_share);
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

        mDeleteButton = (Button)v.findViewById(R.id.tracked_delete);
        if (mTracked.getTitle() == null) {
            mDeleteButton.setVisibility(View.INVISIBLE);
        } else {
            mDeleteButton.setVisibility(View.VISIBLE);
        }
        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UUID trackedId = (UUID)getArguments().getSerializable(EXTRA_TRACKED_ID);
                TrackedManager.get(getActivity()).removeTracked(trackedId);

                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
            }
        });

        // If camera is not available, disable camera functionality
        PackageManager pm = getActivity().getPackageManager();
        boolean hasACamera = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA) ||
                pm.hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT) ||
                (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD &&
                        Camera.getNumberOfCameras() > 0);
        if (!hasACamera) {
            mPhotoButton.setEnabled(false);
        }

        return v;
    }

    public static TrackedFragment newInstance(UUID trackedId) {
        Bundle args = new Bundle();
        args.putSerializable(EXTRA_TRACKED_ID, trackedId);
        TrackedFragment fragment = new TrackedFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private String getTrackedActivityReport() {

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return    "Activity: " + mTracked.getTitle() + "\n"
                + "Date: " + formatter.format(mTracked.getDate()) + "\n"
                + "Place: " + mTracked.getPlace() + "\n"
                + "Details: " + mTracked.getDetails() + "\n"
                + "Location: " + mTracked.getLocation().getLatitude() + " " + mTracked.getLocation().getLongitude();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) return;
        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTracked.setDate(date);
            updateDate();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                if (mTracked.getTitle() == null) {
                    UUID trackedId = (UUID)getArguments().getSerializable(EXTRA_TRACKED_ID);
                    TrackedManager.get(getActivity()).removeTracked(trackedId);
                }

                if (NavUtils.getParentActivityName(getActivity()) != null) {
                    NavUtils.navigateUpFromSameTask(getActivity());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
