package com.benjaminfinlay.activitytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

public class SQLiteDatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "TrackedDB";
    private static final String TABLE_NAME = "TrackedActivitys";
    private static final String TRACKED_ID = "id ";
    private static final String TRACKED_UUID = "uuid";
    private static final String TRACKED_TITLE = "title";
    private static final String TRACKED_PLACE = "place";
    private static final String TRACKED_DATE = "date";
    private static final String TRACKED_DETAILS = "details";
    private static final String TRACKED_LATITUDE =  "latitude";
    private static final String TRACKED_LONGITUDE = "longitude";
    private static final String TRACKED_IMAGE = "image";

    private Context viewContext;

    private static final String[] COLUMNS = {
            TRACKED_ID,
            TRACKED_UUID,
            TRACKED_TITLE,
            TRACKED_PLACE,
            TRACKED_DATE,
            TRACKED_DETAILS,
            TRACKED_LATITUDE,
            TRACKED_LONGITUDE,
            TRACKED_IMAGE};

    public SQLiteDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        viewContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATION_TABLE = "CREATE TABLE TrackedActivitys ( "
                + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "uuid TEXT, "
                + "title TEXT, "
                + "place TEXT, "
                + "date INTEGER, "
                + "details TEXT, "
                + "latitude REAL, "
                + "longitude REAL, "
                + "image TEXT )";

        db.execSQL(CREATION_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // you can implement here migration process
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        this.onCreate(db);
    }

    public void removeTracked(Tracked trackedActivity) {
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "id = ?", new String[] { String.valueOf(trackedActivity.getId()) });
        db.close();
    }

    public Tracked getTracked(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, // a. table
                COLUMNS, // b. column names
                " id = ?", // c. selections
                new String[] { String.valueOf(id) }, // d. selections args
                null, // e. group by
                null, // f. having
                null, // g. order by
                null); // h. limit

        if (cursor != null)
            cursor.moveToFirst();

        Tracked trackedActivity = new Tracked();
        trackedActivity.setId(Integer.parseInt(cursor.getString(0)));
        trackedActivity.setUUID(UUID.fromString(cursor.getString(1)));
        trackedActivity.setTitle(cursor.getString(2));
        trackedActivity.setPlace(cursor.getString(3));
        Date newDate = new Date();
        newDate.setTime(cursor.getLong(4));
        trackedActivity.setDate(newDate);
        trackedActivity.setDetails(cursor.getString(5));
        trackedActivity.setLocation(cursor.getFloat(6), cursor.getFloat(7));
        trackedActivity.setImagePath(cursor.getString(8));

        return trackedActivity;
    }

    public List<Tracked> allTrackedActivity() {
        List<Tracked> tracked = new LinkedList<Tracked>();

        SQLiteDatabase db = this.getWritableDatabase();

        String query = "SELECT  * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        Tracked trackedActivity = null;


        if (cursor.moveToFirst()) {
            do {
                trackedActivity = new Tracked();
                trackedActivity.setId(Integer.parseInt(cursor.getString(0)));
                trackedActivity.setUUID(UUID.fromString(cursor.getString(1)));
                trackedActivity.setTitle(cursor.getString(2));
                trackedActivity.setPlace(cursor.getString(3));
                Date newDate = new Date();
                newDate.setTime(cursor.getLong(4));
                trackedActivity.setDate(newDate);
                trackedActivity.setDetails(cursor.getString(5));
                trackedActivity.setLocation(cursor.getFloat(6), cursor.getFloat(7));
                trackedActivity.setImagePath(cursor.getString(8));
                tracked.add(trackedActivity);
            } while (cursor.moveToNext());
        }

        return tracked;
    }

    public void addTracked(Tracked trackedActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TRACKED_UUID, trackedActivity.getUUID().toString());
        values.put(TRACKED_TITLE, trackedActivity.getTitle());
        values.put(TRACKED_PLACE, trackedActivity.getPlace());
        values.put(TRACKED_DATE, trackedActivity.getDate().getTime());
        values.put(TRACKED_DETAILS, trackedActivity.getDetails());
        values.put(TRACKED_LATITUDE, trackedActivity.getLocation().getLatitude());
        values.put(TRACKED_LONGITUDE, trackedActivity.getLocation().getLongitude());
        values.put(TRACKED_IMAGE, trackedActivity.getImagePath());
        // insert
        db.insert(TABLE_NAME,null, values);
        db.close();
    }

    public int updateTracked(Tracked trackedActivity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TRACKED_UUID, trackedActivity.getUUID().toString());
        values.put(TRACKED_TITLE, trackedActivity.getTitle());
        values.put(TRACKED_PLACE, trackedActivity.getPlace());
        values.put(TRACKED_DATE, trackedActivity.getDate().getTime());
        values.put(TRACKED_DETAILS, trackedActivity.getDetails());
        values.put(TRACKED_LATITUDE, trackedActivity.getLocation().getLatitude());
        values.put(TRACKED_LONGITUDE, trackedActivity.getLocation().getLongitude());
        values.put(TRACKED_IMAGE, trackedActivity.getImagePath());

        int i = db.update(TABLE_NAME, // table
                values, // column/value
                "id = ?", // selections
                new String[] { String.valueOf(trackedActivity.getId()) });

        db.close();

        return i;
    }
}
