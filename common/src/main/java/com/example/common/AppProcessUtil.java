package com.example.common;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.Iterator;
import java.util.List;

public class AppProcessUtil {


    /**
     * 获取运行的进程id
     * @return
     */
    public static int getProcessId() {

        return android.os.Process.myPid();
    }


    /**
     * 根据进程id获取进程名
     * @param context
     * @param pID
     * @return
     */
    public static String getProcessName(Context context, int pID) {
        if(context == null) {
            return null;
        }
        String processName = null;
        Context appContext = context.getApplicationContext();
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = appContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * 获取运行该方法的进程的进程名
     * @param context
     * @return
     */
    public static String getProcessName(Context context) {
        if(context == null) {
            return null;
        }
        int pID = getProcessId();
        String processName = null;
        Context appContext = context.getApplicationContext();
        ActivityManager am = (ActivityManager) appContext.getSystemService(Context.ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = appContext.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {

                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

    /**
     * 判断该进程是app进程还是远程service进程
     * @param c
     * @return
     */
    public static boolean isAppProcess(Context c) {
        if(c == null) {
            return false;
        }
        c = c.getApplicationContext();

        String processName = getProcessName(c);
        if (processName == null || !processName.equalsIgnoreCase(c.getPackageName())) {
            return false;
        }else {
            return true;
        }
    }


    /**
     * app是否在前台运行  后台运行指的是只有所属的service运行
     * 需要权限:android.permission.GET_TASKS
     * @param context
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public static boolean isAppRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if(tasks != null && !tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if(!topActivity.getPackageName().equalsIgnoreCase(context.getPackageName())) {
                return false;
            }else {
                return true;
            }
        }
        return false;

    }


    /**
     * 判断服务是否运行
     * @param context
     * @param serviceName
     * @return
     */
    public static boolean isServiceRunning(Context context, String serviceName) {

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> lists = am.getRunningServices(100);

        for (ActivityManager.RunningServiceInfo info : lists) {//判断服务
            if(info.service.getClassName().equals(serviceName)){
                Log.i("Service1进程", ""+info.service.getClassName());
                isRunning = true;
            }
        }


        return isRunning;
    }


    /**
     * 判断进程是否在运行
     * @param context
     * @param proessName
     * @return
     */
    public static boolean isProessRunning(Context context, String proessName) {

        boolean isRunning = false;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        List<ActivityManager.RunningAppProcessInfo> lists = am.getRunningAppProcesses();
        for(ActivityManager.RunningAppProcessInfo info : lists){
            if(info.processName.equals(proessName)){
                //Log.i("Service2进程", ""+info.processName);
                isRunning = true;
            }
        }

        return isRunning;
    }


}
