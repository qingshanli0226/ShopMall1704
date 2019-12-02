package com.shaomall.framework.manager;

import android.app.Activity;

import java.util.LinkedList;

public class ActivityInstanceManager {
    private static LinkedList<Activity> activitiesList = new LinkedList<>();


    //添加Activity
    public static void addActivity(Activity instance) {
        //        if (!activitiesList.contains(instance))
        activitiesList.add(instance);
    }

    //从链表中删除
    public static void removeActivity(Activity instance) {
        if (activitiesList.contains(instance)) {
            instance.finish();
            activitiesList.remove(instance);
        }
    }

    //关闭应用中所有的打开activity
    public static void finishAllActivity() {
        for (int i = activitiesList.size() - 1; i >= 0; i--) {
            removeActivity(activitiesList.get(i));
        }
    }
}
