package com.example.framework.manager;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;
//activity管理类
public class AppActivityManager {

    //使用链表,增删快
    private static List<Activity> activityList = new LinkedList<>();
    //添加activity
    public static void addActivity(Activity activity) {
        if (!activityList.contains(activity))
            activityList.add(activity);
    }
    //移除activity
    public static void removeActivity(Activity activity) {
        activityList.remove(activity);
    }

    //关闭全部activity
    public static void finishActivity() {
        for (Activity activity : activityList) {
            activity.finish();
        }
    }
}
