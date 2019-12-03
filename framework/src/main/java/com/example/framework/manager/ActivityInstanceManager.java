package com.example.framework.manager;

import android.app.Activity;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.List;

/**
 * author:李浩帆
 */
public class ActivityInstanceManager {

    /**
     * 单例模式
     */
    //TODO 私有化对象
    private static ActivityInstanceManager instanceManager;

    //TODO 私有化构造
    private ActivityInstanceManager() {}

    public static ActivityInstanceManager getInstance(){
        if(instanceManager==null){
            instanceManager = new ActivityInstanceManager();
        }
        return instanceManager;
    }

    //TODO 使用LinkedList对Activity 进行增加删除操作
    private static List<Activity> activityList = new LinkedList<>();

    //TODO 添加Activity
    public static void addActivity(Activity instance){
        activityList.add(instance);
    }

    //TODO 删除Activity
    public static void removeActivity(Activity instance){
        if(activityList.contains(instance)){
            activityList.remove(instance);
        }
    }

    //TODO 关闭应用中所有的Activity
    public static void finishAllActivity(){
        for (Activity activity : activityList) {
            activity.finish();
        }
    }

    //TODO 吐司
    public static void toast(Activity instance,String msg){
        Toast.makeText(instance, msg, Toast.LENGTH_SHORT).show();
    }
}
