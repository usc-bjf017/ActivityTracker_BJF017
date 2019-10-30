package com.benjaminfinlay.activitytracker;

import android.content.Context;

import java.util.List;
import java.util.Stack;
import java.util.UUID;

public class TrackedManager {
    private static TrackedManager sTrackedManager;
    private SQLiteDatabaseHelper db;

    private TrackedManager(Context appContext) {
        db = new SQLiteDatabaseHelper(appContext);
    }

    public static TrackedManager get(Context c) {
        if (sTrackedManager == null) {
            sTrackedManager = new TrackedManager(c.getApplicationContext());
        }
        return sTrackedManager;
    }

    public void addTracked(Tracked a) {
        db.addTracked(a);
    }

    public void removeTracked(Tracked a) {
        db.removeTracked(a);
    }

    public Stack<Tracked> getAllTracked() {
        List<Tracked> trackedList =  db.allTrackedActivity();
        Stack<Tracked> trackedStack = new Stack<Tracked>();
        trackedStack.addAll(trackedList);
        return trackedStack;
    }

    public Tracked getTracked(UUID id) {
        List<Tracked> trackedList =  db.allTrackedActivity();
        for (Tracked a : trackedList) {
            if (a.getUUID().equals(id)) {
                return a;
            }
        }
        return null;
    }

    public void removeTracked(UUID id) {
        List<Tracked> trackedList =  db.allTrackedActivity();
        for (Tracked a : trackedList) {
            if (a.getUUID().equals(id)) {
                db.removeTracked(a);
            }
        }
    }

    public void updateTracked(Tracked trackedActivity) {
        db.updateTracked(trackedActivity);
    }
}
