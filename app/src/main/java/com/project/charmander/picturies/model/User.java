package com.project.charmander.picturies.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by Jenny on 22.06.2015.
 */
public class User {

    private String mUserId;
    private String mUsername;
    private String mEmail;
    private String mPassword;
    private Bitmap mImage;

    private ArrayList<User> mFriends;
    private ArrayList<Picture> mPictures;
    private ArrayList<Roadtrip> mRoadtrips;

    //Constructor
    public User(String id, String username, String email, String password) {
        mUserId = id;
        mUsername = username;
        mEmail = email;
        mPassword = password;

        mPictures = new  ArrayList<Picture>();
    }

    //Getter & Setter
    //TODO: Defaults überarbeiten

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String id) {
        mUserId = id;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public Bitmap getImage() {
        return mImage;
    }

    public void setImage(Bitmap image) {
        mImage = image;
    }

    //Getter & Setter für ArrayListen
    public ArrayList<User> getFriends() {
        return mFriends;
    }

    public void setFriends(ArrayList<User> friends) {
        mFriends = friends;
    }

    public ArrayList<Picture> getPictures() {
        return mPictures;
    }

    public void setPictures(ArrayList<Picture> pictures) {
        mPictures = pictures;
    }

    public ArrayList<Roadtrip> getRoadtrips() {
        return mRoadtrips;
    }

    public void setRoadtrips(ArrayList<Roadtrip> roadtrips) {
        mRoadtrips = roadtrips;
    }

    public void addPictureToList(Picture picture) {
        mPictures.add(picture);
    }

    public Picture getPicture(int position) {
        return mPictures.get(position);
    }
}
