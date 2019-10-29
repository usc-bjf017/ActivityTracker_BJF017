package com.benjaminfinlay.activitytracker;

import android.content.Context;

import java.util.ArrayList;
import java.util.Stack;
import java.util.UUID;

public class TrackedManager {

    private Stack<Tracked> mTracked;

    private static TrackedManager sTrackedManager;
    private Context mAppContext;

    private TrackedManager(Context appContext) {
        mAppContext = appContext;
        mTracked = new Stack<Tracked>();

        for (int i = 0; i < 3; i++) {
            Tracked a = new Tracked();
            a.setTitle("Tracked #" + i);
            mTracked.add(a);
        }
    }

    public static TrackedManager get(Context c) {
        if (sTrackedManager == null) {
            sTrackedManager = new TrackedManager(c.getApplicationContext());
        }
        return sTrackedManager;
    }

    public void addTracked(Tracked a) {
        mTracked.add(a);
    }

    public void removeTracked(Tracked a) {
        mTracked.remove(a);
    }

    public Stack<Tracked> getAllTracked() {
        return mTracked;
    }

    public Tracked getTracked(UUID id) {
        for (Tracked a : mTracked) {
            if (a.getId().equals(id))
                return a;
        }
        return null;
    }

    public void removeTracked(UUID id) {
        for (Tracked a : mTracked) {
            if (a.getId().equals(id))
                mTracked.remove(a);
        }
    }
}
