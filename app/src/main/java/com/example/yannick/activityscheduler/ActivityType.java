package com.example.yannick.activityscheduler;

import java.util.HashMap;

/**
 * Created by Jonas on 01.06.2018.
 */

public enum ActivityType {
    //Bluetooth = 0, Airplane mode = 1, Ringtone = 2 and Wi-Fi = 3
    BLUETOOTH(0, R.drawable.ic_bluetooth_gray_24dp, R.id.fab_add_bluetooth, R.id.ch_bluetooth),
    AIRPLANE(1, R.drawable.ic_airplanemode_active_gray_24dp, R.id.fab_add_airplane, R.id.ch_airplane),
    RINGTONE(2, R.drawable.ic_volume_up_gray_24dp, R.id.fab_add_ringtone, R.id.ch_ringtone),
    WIFI(3, R.drawable.ic_network_wifi_gray_24dp, R.id.fab_add_wifi, R.id.ch_wifi);

    private static HashMap<Integer, ActivityType> type_ids = new HashMap<>();
    private static HashMap<Integer, ActivityType> fab_ids = new HashMap<>();
    private static HashMap<Integer, ActivityType> chip_ids = new HashMap<>();

    private int id;
    private int iconId;
    private int fabId;
    private int chipId;

    static {
        for (ActivityType activityType : ActivityType.values()) {
            type_ids.put(activityType.id, activityType);
            fab_ids.put(activityType.fabId, activityType);
            chip_ids.put(activityType.chipId, activityType);
        }
    }

    ActivityType(int id, int iconId, int fabId, int chipId) {
        this.id = id;
        this.iconId = iconId;
        this.fabId = fabId;
        this.chipId = chipId;
    }

    public static ActivityType valueOfId(int id) {
        return type_ids.get(id);
    }

    public static ActivityType valueOfFabId(int fabId) {
        return fab_ids.get(fabId);
    }

    public static ActivityType valueOfChipId(int chipId) {
        return chip_ids.get(chipId);
    }

    public int getId() {
        return id;
    }

    public int getIconId() {
        return iconId;
    }

    public int getFabId() {
        return fabId;
    }

    public int getChipId() {
        return chipId;
    }
}
