package com.example.shopmall;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

public class AppActivityManager {

    private static List<Activity> activityList = new LinkedList<>();

    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity))
            activityList.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    public static void finishActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
