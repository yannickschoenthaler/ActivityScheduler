package com.example.yannick.activityscheduler.model;

import com.example.yannick.activityscheduler.ActivityType;

import java.io.Serializable;
import java.util.Calendar;

import static com.example.yannick.activityscheduler.ActivityType.BLUETOOTH;

public class CustomActivity implements Serializable {
    private boolean isExpanded = true;
    private Calendar time;
    private ActivityType type = BLUETOOTH;

    public CustomActivity(ActivityType type, Calendar time) {
        this.type = type;
        this.time = time;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

}
