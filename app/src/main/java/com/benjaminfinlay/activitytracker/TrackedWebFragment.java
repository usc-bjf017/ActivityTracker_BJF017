package com.benjaminfinlay.activitytracker;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

/**
 * Basic controller to open up a web browser fragment
 * to view help page. "https://www.wikihow.com/Check-In-on-Facebook”"
 */
public class TrackedWebFragment extends Fragment {

    /**
     * Triggered when the fragment is created.
     * Sets up the basic web browser to go to "https://www.wikihow.com/Check-In-on-Facebook”"
     * @param inflater To create view instance based on XML file.
     * @param container Base view to create the view instance in.
     * @param savedInstanceState App's compiled code and resources.
     * @return The new view.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tracked_web, container, false);

        // Find web view in inflated view to open url.
        WebView webView = (WebView) v.findViewById(R.id.tracked_webView);
        String url = "https://www.wikihow.com/Check-In-on-Facebook”";
        webView.loadUrl(url);

        return v;
    }
}
