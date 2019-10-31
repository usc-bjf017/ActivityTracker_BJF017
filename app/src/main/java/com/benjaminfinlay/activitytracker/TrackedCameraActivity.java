package com.benjaminfinlay.activitytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Window;
import android.view.WindowManager;

/**
 * Runs the Camera Fragment view.
 */
public class TrackedCameraActivity extends SingleFragmentActivity {

    /**
     * Triggered when the Activity is used to open the Camera Fragment
     * @param savedInstanceState App's compiled code and resources.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates the new camera fragment.
     * @return Returns the new camera fragment.
     */
    @Override
    protected Fragment createFragment() {
        return new TrackedCameraFragment();
    }
}
