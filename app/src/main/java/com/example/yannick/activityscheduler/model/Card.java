package com.example.yannick.activityscheduler.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.time.Year;
import java.util.ArrayList;
import java.util.Calendar;

public class Card implements Serializable {
    private String title;
    private boolean activated, daily;
    private Calendar time;
    private ArrayList<CustomActivity> activities = new ArrayList<>();

    public Card() {
        time = Calendar.getInstance();
    }

    public Card(String title, boolean activated) {
        this.title = title;
        this.activated = activated;
        this.time = Calendar.getInstance();
    }

    public static Card fromJSON(JSONObject object) throws JSONException {
        Card ret = new Card();

        //TODO set time
        ret.setActivated(object.getBoolean("activated"));
        ret.setTitle(object.getString("title"));
        ret.setTime(object.getLong("time"));
        ret.setDaily(object.getBoolean("daily"));

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
        ret.put("time", time.getTimeInMillis());
        ret.put("daily", daily);

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

    public void addActivity(CustomActivity activity) {
        this.activities.add(activity);
    }

    public void removeActivity(CustomActivity selectedItem) {
        activities.remove(selectedItem);
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(long time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(time);
        this.time = calendar;
    }

    public void setTime(int hour, int minute) {
        time.set(Calendar.HOUR_OF_DAY, hour);
        time.set(Calendar.MINUTE, minute);
    }

    public void setDate(int year, int month, int day) {
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, month);
        time.set(Calendar.DAY_OF_MONTH, day);
    }

    public boolean isDaily() {
        return daily;
    }

    public void setDaily(boolean daily) {
        this.daily = daily;
    }

    public void toggleDaily(){
        daily = !daily;
    }
}