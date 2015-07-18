package com.project.charmander.picturies.model;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Jenny on 22.06.2015.
 */
public class Roadtrip {

    private UUID mRoadtripId;
    private String mName;
    private String mDescription;
    private User mCreator;

    private Date mCreated;
    private Date mStarted;
    private Date mEnd;

    private ArrayList<Picture> mPictures;
    private ArrayList<String> mPictureIds;


    //Constructor
    public Roadtrip(UUID roadtripId, String name, User creator, Date created, ArrayList<String> pictureIds) {
        mRoadtripId = roadtripId;
        mName = name;
        mCreator = creator;
        mCreated = created;
        mPictureIds = pictureIds;
    }

    public Roadtrip(UUID roadtripId, String name, ArrayList<Picture> pictures, User creator, Date created) {
        mRoadtripId = roadtripId;
        mName = name;
        mCreator = creator;
        mCreated = created;
        mPictures = pictures;

        mPictureIds = new ArrayList<String>();
        for(int i=0; i < pictures.size(); i++ ){
            mPictureIds.add(pictures.get(i).getImageId().toString());
        }
    }


    //Getter & Setter

    public UUID getRoadtripId() {
        return mRoadtripId;
    }

    public void setRoadtripId(UUID roadtripId) {
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

    public ArrayList<String> getPictureIds() {
        return mPictureIds;
    }

    public void setPictureIds(ArrayList<String> pictureIds) {
        mPictureIds = pictureIds;
    }

    public ArrayList<Picture> getPictures() {
        return mPictures;
    }

    public void setPictures(ArrayList<Picture> pictures) {
        mPictures = pictures;
    }

    public String generateJson() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss z");
        String strDate = sdf.format(mCreated);

        String json = "{\"title\":\"" + mName + "\","
                + "\"creator\":\"" + mCreator.getUserId() + "\","
                + "\"created\":\"" + strDate + "\","
                + "\"pictures\":[";

        for(int i=0; i < mPictureIds.size(); i++) {
            if(i==mPictureIds.size()-1){
                json = json +"{ \"id\":\"" + mPictureIds.get(i) + "\"}";
            }
            else {
                json = json +"{ \"id\":\"" + mPictureIds.get(i) + "\"},";
            }
        }

        json=  json +  "],"
                + "\"description\":\"" + mDescription + "\""
                + "}";

        return json;
    }


    //TODO: Methode die Bilder addet und löscht
}
