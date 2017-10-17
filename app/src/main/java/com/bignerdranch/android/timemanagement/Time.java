package com.bignerdranch.android.timemanagement;

import android.location.Location;
import android.widget.Spinner;
import android.widget.TimePicker;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Mitchell Hayward on 21/9/17.
 */

public class Time {

    // Generate Elements that the Application will contain

    private UUID mId;
    private String mTitle;
    private Date mDate;
    private int mSpinnerId;
    private int mHour;
    private int mMinute;

    private UUID newId;

    private String mName;
    private String mIdentifier;
    private String mEmail;
    private int mSettingsSpinner;
    private String mSettingsComment;

    private Double mLat;
    private Double mLong;

    private TimePicker mTimePicker;

    private String fullAddress;

    // Generate a constructor

    public Time() {
        this(UUID.randomUUID());
        //mId = UUID.randomUUID();
        //mDate = new Date();

        // Generate a custom UUID from string in order to activate the Settings Fragment

        newId = UUID.fromString("00002415-0000-1000-8000-00805F9B34FB");
    }

    public Time(UUID id){
        mId = id;
        mDate = new Date();
    }

    public UUID getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(Date mDate) {
        this.mDate = mDate;
    }

    public void setSpinnerId(int id){
        this.mSpinnerId = id;
    }

    public int getSpinnerId(){
        return mSpinnerId;
    }

    public void setHour(int hour){
        mHour = hour;
    }

    public int getHour(){
        return mHour;
    }

    public void setMin(int min){
        mMinute = min;
    }

    public int getMin(){
        return mMinute;
    }

    public void setName(String mName){
        this.mName = mName;
    }

    public String getName(){
        return mName;
    }

    public UUID getNewId() {
        return newId;
    }

    public void setNewId(UUID newId) {
        this.newId = newId;
    }

    public void setIdentifier(String id){
        this.mIdentifier = id;
    }

    public String getIdentifier(){
        return mIdentifier;
    }

    public void setEmail(String email){
        this.mEmail = email;
    }

    public String getEmail(){
        return mEmail;
    }

    public void setSettingsSpinner(int spinner){
        this.mSettingsSpinner = spinner;
    }

    public int getSettingsSpinner(){
        return mSettingsSpinner;
    }

    public void setSettingsComment(String comment){
        this.mSettingsComment = comment;
    }

    public String getSettingsComment(){
        return mSettingsComment;
    }

    public void setLat(Double lat){
        this.mLat = lat;
    }

    public Double getLat(){
        return mLat;
    }

    public void setLong(Double lon){
        this.mLong = lon;
    }

    public Double getLong(){
        return mLong;
    }

    public String getPhotoFilename(){
        return "IMG_" + getId().toString() + ".jpg";
    }

    public void setFullAddress(String address){
        this.fullAddress = address;
    }

    public String getFullAddress(){
        return fullAddress;
    }

}
