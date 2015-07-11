package com.project.charmander.picturies.model;

import android.graphics.Bitmap;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Jenny on 22.06.2015.
 */
public class Picture {

    private UUID mImageId;
    private Bitmap mImage;
    private String mName;
    private String mDescription;
    private User mCreator;
    private Date mCreated;

    private double mLatitude;
    private double mLongitude;

    private Roadtrip mBericht;


    //Constructor
    //TODO: evtl. mehrere für z.B. wenn man Titel auch gleich mitangibt, oder das Foto direkt einem Bericht zuordnet

    public Picture(UUID imageId, String title, User creator, Date created, double latitude, double longitude, Bitmap image, String description) {
        mImageId = imageId;
        mName = title;
        if(mName==null) mName="";
        mCreator = creator;
        mCreated = created;
        mLatitude = latitude;
        mLongitude = longitude;
        mImage = image;
        mDescription = description;
        if(description==null) mDescription="";
    }

    //Getter & Setter
    //TODO: Defaults überarbeiten


    public UUID getImageId() {
        return mImageId;
    }

    public void setImageId(UUID imageId) {
        mImageId = imageId;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
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

    public Roadtrip getBericht() {
        return mBericht;
    }

    public void setBericht(Roadtrip bericht) {
        mBericht = bericht;
    }

    public String generateJson(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yyyy HH:mm:ss z");
        String strDate = sdf.format(mCreated);

        String json = "{\"title\":\"" + mName + "\","
                + "\"creator\":\"" + mCreator.getUserId() + "\","
                + "\"created\":\"" + strDate + "\","
                + "\"latitude\": "+ mLatitude +","
                + "\"longitude\": "+mLongitude +","
                + "\"_attachments\":{ \""
                + mImageId.toString()+".png\":{"
                + "\"content_type\":\"application/png\","
                + "\"data\":\"" + encodeBitmapToBase64(mImage) + "\"}},"
                + "\"description\":\"" + mDescription + "\""
                + "}";

        return json;
    }

    public static String encodeBitmapToBase64(Bitmap image){
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 0, stream);
        byte[] b = stream.toByteArray();
        String encodedImage = Base64.encodeToString(b, Base64.NO_WRAP);

        return encodedImage;
    }

    @Override
    public String toString() {
        return "Picture{" +
                "mImageId=" + mImageId +
                ", mImage=" + mImage +
                ", mName='" + mName + '\'' +
                ", mDescription='" + mDescription + '\'' +
                ", mCreator=" + mCreator +
                ", mCreated=" + mCreated +
                ", mLatitude=" + mLatitude +
                ", mLongitude=" + mLongitude +
                ", mBericht=" + mBericht +
                '}';
    }

    //TODO: Methoden um etwas zu der Liste dazu zu adden oder zu löschen
}
