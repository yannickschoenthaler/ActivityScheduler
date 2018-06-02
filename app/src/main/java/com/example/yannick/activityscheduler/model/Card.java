package com.example.yannick.activityscheduler.model;

import android.graphics.Bitmap;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

public class Card implements Serializable {
    private String title;
    private boolean activated;
    private ArrayList<CustomActivity> activities = new ArrayList<>();

    public Card() {

    }

    public Card(String title, boolean isActivated) {
        this.title = title;
        this.activated = isActivated;
    }

    public static Card fromJSON(JSONObject object) throws JSONException {
        Card ret = new Card();

        ret.setActivated(object.getBoolean("activated"));
        ret.setTitle(object.getString("title"));

        JSONArray activityArray = object.getJSONArray("activities");

        for (int i = 0; i < activityArray.length(); i++) {
            JSONObject jsonObject = activityArray.getJSONObject(i);
            ret.addActivity(CustomActivity.fromJSON(jsonObject));
        }

        return ret;
    }

    public JSONObject toJSON() throws JSONException {
        JSONObject ret = new JSONObject();

        ret.put("activated", activated);
        ret.put("title", title);

        JSONArray activityArray = new JSONArray();
        for (CustomActivity customActivity : activities) {
            activityArray.put(customActivity.toJSON());
        }

        ret.put("activities", activityArray);

        return ret;
    }

    @Override
    public String toString() {
        return title + " " + activated;
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

    public ArrayList<CustomActivity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<CustomActivity> activities) {
        this.activities = activities;
    }

    public void addActivity(CustomActivity activity) {
        this.activities.add(activity);
    }

    public void removeActivity(CustomActivity selectedItem) {
        activities.remove(selectedItem);
    }
}