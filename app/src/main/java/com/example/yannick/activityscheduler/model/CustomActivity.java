package com.example.yannick.activityscheduler.model;

import com.example.yannick.activityscheduler.ActivityType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import static com.example.yannick.activityscheduler.ActivityType.BLUETOOTH;

public class CustomActivity implements Serializable {
    private boolean isExpanded = true;
    private ActivityType type = BLUETOOTH;

    public CustomActivity() {
    }

    public CustomActivity(ActivityType type) {
        this.type = type;
    }

    public ActivityType getType() {
        return type;
    }

    public void setType(ActivityType type) {
        this.type = type;
    }

    public void setExpanded(boolean isExpanded) {
        this.isExpanded = isExpanded;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public static CustomActivity fromJSON(JSONObject object) throws JSONException{
        CustomActivity ret = new CustomActivity();

        ret.setExpanded(object.getBoolean("expanded"));
        ret.setType(ActivityType.valueOfId(object.getInt("type")));

        return ret;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject ret = new JSONObject();

        ret.put("expanded", isExpanded);
        ret.put("type", type.getId());

        return ret;
    }
}
