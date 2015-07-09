package com.project.charmander.picturies.model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jenny on 22.06.2015.
 */
public class Roadtrip {

    private int mRoadtripId;
    private String mName;
    private String mDescription;
    private User mCreator;

    private Date mCreated;
    private Date mStarted;
    private Date mEnd;

    private ArrayList<Picture> mPictures;


    //Constructor
    public Roadtrip(int roadtripId, String name, User creator, Date created) {
        mRoadtripId = roadtripId;
        mName = name;
        mCreator = creator;
        mCreated = created;
    }

    //Getter & Setter

    public int getRoadtripId() {
        return mRoadtripId;
    }

    public void setRoadtripId(int roadtripId) {
        mRoadtripId = roadtripId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public User getCreator() {
        return mCreator;
    }

    public void setCreator(User creator) {
        mCreator = creator;
    }

    public Date getCreated() {
        return mCreated;
    }

    public void setCreated(Date created) {
        mCreated = created;
    }

    public Date getStarted() {
        return mStarted;
    }

    public void setStarted(Date started) {
        mStarted = started;
    }

    public Date getEnd() {
        return mEnd;
    }

    public void setEnd(Date end) {
        mEnd = end;
    }

    public ArrayList<Picture> getPictures() {
        return mPictures;
    }

    public void setPictures(ArrayList<Picture> pictures) {
        mPictures = pictures;
    }


    //TODO: Methode die Bilder addet und löscht
}
