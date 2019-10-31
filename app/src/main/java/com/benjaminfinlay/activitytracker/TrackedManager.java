package com.benjaminfinlay.activitytracker;

import android.content.Context;
import java.util.List;
import java.util.Stack;
import java.util.UUID;

/**
 * Handles interactions that need access to the SQLiteDatabaseHelper and Tracked items.
 */
class TrackedManager {

    private static TrackedManager sTrackedManager;
    private SQLiteDatabaseHelper db;

    /**
     * Constructor for Tracked manager to handle interactions that need access to the SQLiteDatabaseHelper and Tracked items.
     * @param appContext Context to create the SQLite database reference.
     */
    private TrackedManager(Context appContext) {
        db = new SQLiteDatabaseHelper(appContext);
    }

    /**
     * To get references to tracked manager from wherever needed.
     * @param c Context to get tracked manager from.
     * @return Return the tracked manager in current context.
     */
    static TrackedManager get(Context c) {
        if (sTrackedManager == null) {
            sTrackedManager = new TrackedManager(c.getApplicationContext());
        }
        return sTrackedManager;
    }

    /**
     * Add tracked item to SQLite Database.
     * @param a Tracked to add to Database.
     */
    void addTracked(Tracked a) {
        db.addTracked(a);
    }

    /**
     * Retrieve all Tracked items from SQLite Database as a Stack.
     * @return Return Stack of tracked activity's.
     */
    Stack<Tracked> getAllTracked() {
        List<Tracked> trackedList =  db.allTrackedActivity();
        Stack<Tracked> trackedStack = new Stack<Tracked>();
        trackedStack.addAll(trackedList);
        return trackedStack;
    }

    /**
     * Get a Tracked Activity of a given UUID.
     * @param id UUID of the Tracked Activity to get.
     * @return Return the found activity.
     */
    Tracked getTracked(UUID id) {
        List<Tracked> trackedList =  db.allTrackedActivity();
        for (Tracked a : trackedList) {
            if (a.getUUID().equals(id)) {
                return a;
            }
        }
        return null;
    }

    /**
     * Remove a Tracked Activity from SQLite Database of given UUID.
     * @param id UUID of Tracked to remove.
     */
    void removeTracked(UUID id) {
        List<Tracked> trackedList =  db.allTrackedActivity();
        for (Tracked a : trackedList) {
            if (a.getUUID().equals(id)) {
                db.removeTracked(a);
            }
        }
    }

    /**
     * Update a Tracked Activity from the SQLite to the given information.
     * @param trackedActivity Tracked Activity to update Tracked from.
     */
    void updateTracked(Tracked trackedActivity) {

        if (trackedActivity.getTitle() == null || trackedActivity.getTitle().equals("")) {
            trackedActivity.setTitle("Tracked Activity");
        }

        db.updateTracked(trackedActivity);
    }
}
