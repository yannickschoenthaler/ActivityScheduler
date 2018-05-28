package com.example.yannick.activityscheduler.model;

import com.example.yannick.activityscheduler.ActivityTypes;

import java.io.Serializable;
import java.util.Calendar;

public class CustomActivity implements Serializable {
    private int type = ActivityTypes.BLUETOOTH; //Bluetooth = 0, Airplane mode = 1, Ringtone = 2 and Wi-Fi = 3
    private Calendar time;

    public CustomActivity(int type, Calendar time) {
        this.type = type;
        this.time = time;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }
}
