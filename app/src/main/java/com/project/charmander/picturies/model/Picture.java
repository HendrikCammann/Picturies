package com.project.charmander.picturies.model;

import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jenny on 22.06.2015.
 */
public class Picture {

    private int mImageId;
    private Bitmap mImage;
    private String mLocalPath;
    private String mName;
    private String mDescription;
    private User mCreator;
    private Date mCreated;

    private double mLatitude;
    private double mLongitude;

    //TODO: überlegen, ob das sinnvoll ist
    ArrayList<Roadtrip> mUsedInList;


    //Constructor
    //TODO: evtl. mehrere für z.B. wenn man Titel auch gleich mitangibt, oder das Foto direkt einem Bericht zuordnet

    public Picture(int imageId,String localPath, User creator, Date created, double latitude, double longitude) {
        mImageId = imageId;
        mLocalPath = localPath;
        mCreator = creator;
        mCreated = created;
        mLatitude = latitude;
        mLongitude = longitude;
    }

    //Getter & Setter
    //TODO: Defaults überarbeiten


    public int getImageId() {
        return mImageId;
    }

    public void setImageId(int imageId) {
        mImageId = imageId;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    public String getLocalPath() {
        return mLocalPath;
    }

    public void setLocalPath(String localPath) {
        mLocalPath = localPath;
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

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        mLongitude = longitude;
    }

    public ArrayList<Roadtrip> getUsedInList() {
        return mUsedInList;
    }

    public void setUsedInList(ArrayList<Roadtrip> usedInList) {
        mUsedInList = usedInList;
    }

    //TODO: Methoden um etwas zu der Liste dazu zu adden oder zu löschen
}
