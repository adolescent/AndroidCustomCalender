package com.example.linkio.androidcustomcalendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteOpenHelper {
    private static final String DATABASE_NAMES = "mycal";
    private static final int DATABASE_VERSION = 7;
    public Database(Context context) {
        super(context, DATABASE_NAMES, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE events ( _id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, "
                +"message TEXT NOT NULL, "
                + "reminder TEXT NOT NULL, "
                + "endT TEXT );");



        /*"CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                        + "NAME TEXT, "
                Create the DRINK table.
                        + "DESCRIPTION TEXT, "
                        + "IMAGE_RESOURCE_ID INTEGER);");*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

  /*  public addEvents(){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("message", event.getName()); // Events Name
        values.put("reminder", event.getEventDate()); // Events Phone

        // Inserting Row
        db.insert("events", null, values);
        db.close(); // Closing database connection

        Date dateToday = new Date();
        List<EventObjects> events = new ArrayList<>();
        String query = "select * from events";
        Cursor cursor = this.getDbConnection().rawQuery(query, null);
        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String message = cursor.getString(cursor.getColumnIndexOrThrow("message"));
                //String startDate = cursor.getString(cursor.getColumnIndexOrThrow("reminder"));
                //convert start date to date object
                Date reminderDate = convertStringToDate("7-12-2017");//startDate);
                if(reminderDate.after(dateToday) || reminderDate.equals(dateToday)){
                    events.add(new EventObjects(id, message, reminderDate));
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return events;
    }*/

    // Adding new pEvent
    void addEvent(Events pEvent) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("message", pEvent.getName()); // Events Name
        values.put("reminder", pEvent.getEventDate()); // Events Phone

        // Inserting Row
        db.insert("events", null, values);
        db.close(); // Closing database connection
    }


    // Deleting single pEvent
    public void deleteEvent(Events pEvent) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("events", "reminder = ?",
                new String[] { String.valueOf(pEvent.getEventDate()) });
        db.close();
    }

    // Getting All events
    public List<Events> getAllEvents() {
        List<Events> mEventsList = new ArrayList<Events>();
        // Select All Query
        String selectQuery = "SELECT * FROM events";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Events mEvent = new Events();
                mEvent.setID(Integer.parseInt(cursor.getString(0)));
                mEvent.setName(cursor.getString(1));
                mEvent.setEventDate(cursor.getString(2));
                // Adding mEvent to list
                mEventsList.add(mEvent);
            } while (cursor.moveToNext());
        }

        // return event list
        return mEventsList;
    }
}