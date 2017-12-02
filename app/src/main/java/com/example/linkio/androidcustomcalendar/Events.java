package com.example.linkio.androidcustomcalendar;

public class Events {

    //private variables
    int _id;
    String mEventName;
    String mEventDate;

    // Empty constructor
    public Events(){

    }
    // constructor
    public Events(int id, String name, String mEventDate){
        this._id = id;
        this.mEventName = name;
        this.mEventDate = mEventDate;
    }

    // constructor
    public Events(String name, String mEventDate){
        this.mEventName = name;
        this.mEventDate = mEventDate;
    }
    // getting ID
    public int getID(){
        return this._id;
    }

    // setting id
    public void setID(int id){
        this._id = id;
    }

    // getting name
    public String getName(){
        return this.mEventName;
    }

    // setting name
    public void setName(String name){
        this.mEventName = name;
    }

    // getting event date
    public String getEventDate(){
        return this.mEventDate;
    }

    // setting event date
    public void setEventDate(String phone_number){
        this.mEventDate = phone_number;
    }
}