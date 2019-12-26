package com.example.remindsteporgan.Util;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import com.example.remindsteporgan.R;

public class FrontService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("LQS: ", "service onCreate.....");
    }

    @Override
    public int onStartCommand(Intent  intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        Log.d("LQS:", "onStartCommand.........");

        //版本适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Notification.Builder builder = new Notification.Builder(this);
            builder.setSmallIcon(R.mipmap.tu1);
            builder.setContentTitle("live 1704");
            builder.setContentText("Test Front Service");

            startForeground(1, builder.build());//把后台服务进程编程前台服务进程
        } else {
            startForeground(1, new Notification());//把后台服务进程编程前台服务进程
        }
        //返回值的意思？START_NOT_STICKY 非粘性的，被系统杀死时，它不会再次启动应用.
        //START_STICKY:粘性的，应用被系统杀死时，如果资源充足，应用自动启动
        return START_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
