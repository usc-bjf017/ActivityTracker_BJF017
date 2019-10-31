package com.benjaminfinlay.activitytracker;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

/**
 * Basic controller to open up a web browser
 * to view help page. "https://www.wikihow.com/Check-In-on-Facebook”"
 */
public class TrackedWebActivity extends SingleFragmentActivity {

    /**
     * Triggered when the Activity is opened.
     * Sets up for the Web Fragment to be opened.
     * @param savedInstanceState App's compiled code and resources.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Hide the status bar and other OS-level chrome
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
    }

    /**
     * Creates the new fragment to open web view.
     * @return Returns the new fragment created.
     */
    @Override
    protected Fragment createFragment() {
        return new TrackedWebFragment();
    }
}
