package com.example.yannick.activityscheduler.model;

import com.example.yannick.activityscheduler.ActivityTypes;
import com.example.yannick.activityscheduler.R;

import java.io.Serializable;
import java.util.Calendar;

public class CustomActivity implements Serializable {
    private boolean isExpanded = false;
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

    public void setExpanded(boolean isExpanded){
        this.isExpanded = isExpanded;
    }

    public boolean isExpanded(){
        return isExpanded;
    }

    public int getIconResourceId() {
        switch (getType()) {
            //Bluetooth
            case ActivityTypes.BLUETOOTH: {
                return R.drawable.ic_bluetooth_gray_24dp;
            }
            //Airplane
            case ActivityTypes.AIRPLANE: {
                return R.drawable.ic_airplanemode_active_gray_24dp;
            }
            //Ringtone
            case ActivityTypes.RINGTONE: {
                return R.drawable.ic_volume_up_gray_24dp;
            }
            //WIFI
            case ActivityTypes.WIFI: {
                return R.drawable.ic_network_wifi_gray_24dp;
            }

            default: {
                return -1;
            }
        }
    }

}
