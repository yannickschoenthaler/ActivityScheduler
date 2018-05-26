package com.example.yannick.activityscheduler.model;

import android.graphics.Bitmap;
import android.widget.ImageButton;

import java.io.Serializable;
import java.util.ArrayList;

public class Card implements Serializable{
    private String title;
    private boolean activated;
    private Bitmap picture;
    private ArrayList<CustomActivity> activities = new ArrayList<>();

    public Card(){

    }

    public Card(String title, boolean isActivated, Bitmap picture){
        this.title = title;
        this.activated = isActivated;
        this.picture = picture;
    }

    @Override
    public String toString() {
        return title +" " +activated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public Bitmap getPicture() {
        return picture;
    }

    public void setPicture(Bitmap picture) {
        this.picture = picture;
    }

    public ArrayList<CustomActivity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<CustomActivity> activities) {
        this.activities = activities;
    }

    public void addActivity(CustomActivity activity) {
        this.activities.add(activity);
    }
}