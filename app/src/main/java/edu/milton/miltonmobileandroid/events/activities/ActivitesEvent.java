//Geoffrey Owens 2013. version 1.0.0. please don't change me.
package edu.milton.miltonmobileandroid.events.activities;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ActivitesEvent {
    private String eventTitle;		//name of the event
    private String eventDescription;//medium length description of the event
    private Date eventDate;			//date the event occurs on. can be obtained in YYYY-MM-DD format via toString()
    private String eventBeginTime;	//time of beginning of event. toString() returns time in {t 'hh:mm:ss'} format.
    private String eventEndTime;		//time of end of event


    public String getEventLocation() {
        return eventLocation;
    }


    private String eventLocation;

    //request constructor
    public ActivitesEvent(String eventTitle, String eventDescription, Date eventDate, String eventBeginTime, String eventEndTime, boolean boarders, boolean clI, boolean clII, boolean clIII, boolean clIV, boolean dayStudents, String eventCategory) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventBeginTime = eventBeginTime;
        this.eventEndTime = eventEndTime;
    }

    //light constructor, sets availability to all users.
    public ActivitesEvent(String eventTitle, String eventDescription, Date eventDate, String eventBeginTime, String eventEndTime) {
        this.eventTitle = eventTitle;
        this.eventDescription = eventDescription;
        this.eventDate = eventDate;
        this.eventBeginTime = eventBeginTime;
        this.eventEndTime = eventEndTime;
    }
    public String getEventTitle() {
        return eventTitle;
    }
    public String getEventDescription() {
        return eventDescription;
    }
    public String getEventBeginTime() {
        return eventBeginTime;
    }
    public String getEventEndTime() {
        return eventEndTime;
    }

    public ActivitesEvent() {
        eventTitle = "ActivitesEvent Name";
        eventDescription = "ActivitesEvent Description";
    }

    public ActivitesEvent(JSONObject jobj) {
        SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeParser = new SimpleDateFormat("HH:mm:ss");
        try {
            if (!jobj.isNull("eventName")) {
                eventTitle = jobj.getString("eventName");
            }
            if (!jobj.isNull("eventDescription")) {
                eventDescription = jobj.getString("eventDescription");
            }
            if (!jobj.isNull("eventLocation")) {
                eventLocation = jobj.getString("eventLocation");
            } else {
                eventLocation = "Unknown Location";
            }
            if (!jobj.isNull("date")) {
                eventDate = dateParser.parse(jobj.getString("date"));
            }
            if (!jobj.isNull("startTime")) {
                eventBeginTime = jobj.getString("startTime");
            }
            if (!jobj.isNull("endTime")) {
                eventEndTime = jobj.getString("endTime");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
