package com.benjaminfinlay.activitytracker;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class TrackedWebFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tracked_web, container, false);

        WebView webView = (WebView) v.findViewById(R.id.tracked_webView);
        String url = "https://www.wikihow.com/Check-In-on-Facebook”";
        webView.loadUrl(url);

        return v;
    }
}
