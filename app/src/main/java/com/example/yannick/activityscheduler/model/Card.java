package com.example.yannick.activityscheduler.model;

import android.graphics.Bitmap;
import android.widget.ImageButton;

import java.io.Serializable;

public class Card implements Serializable{
    public String title;
    public boolean activated;
    public Bitmap picture;

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
}