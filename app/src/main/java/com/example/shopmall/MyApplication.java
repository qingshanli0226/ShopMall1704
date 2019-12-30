package com.example.shopmall;


import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.multidex.MultiDex;

import com.example.common.AppProcessUtil;
import com.example.framework.manager.ConnectManager;
import com.example.framework.manager.MessageManager;
import com.example.framework.manager.UserManager;
import com.example.framework.service.StepJobService;
import com.example.framework.service.StepLocalService;
import com.example.framework.service.StepRemoteService;
import com.example.shopmall.activity.MainActivity;
import com.example.framework.manager.StepManager;
import com.squareup.leakcanary.LeakCanary;
import com.tencent.stat.StatConfig;
import com.tencent.stat.StatService;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;


        ConnectManager.getInstance().init(this);
        //初始化异常
        CrashHandler.getInstance(this).init();
        JAnalyticsInterface.setDebugMode(true);
        JAnalyticsInterface.init(this);
        JPushInterface.init(this);

        if (AppProcessUtil.isAppProcess(this)) {

        StepManager.getInstance().init(getApplicationContext());

            //如果手机版本大于8.0开启前台服务
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                //Job保活,双进程保活
//               getContext().startForegroundService(new Intent(this,StepJobService.class));
//                getContext().startForegroundService(new Intent(this,StepLocalService.class));
//                getContext().startForegroundService(new Intent(this,StepRemoteService.class));

//                startService(new Intent(this, StepJobService.class));
                startService(new Intent(this,StepLocalService.class));
                startService(new Intent(this,StepRemoteService.class));
            }else{
//                startService(new Intent(this, StepJobService.class));
                startService(new Intent(this,StepLocalService.class));
                startService(new Intent(this,StepRemoteService.class));

            }
        }

        //点击通知跳转MainActivity
        Intent intent = new Intent(this, MainActivity.class);

        StepManager.getInstance().setActivityIntent(intent);

        UserManager.getInstance().init(this);


        //初始化消息数据库
        MessageManager.getInstance().init(this);


        StatConfig.setDebugEnable(true);
        // 基础统计API
        StatService.registerActivityLifecycleCallbacks(this);
        LeakCanary.install(this);

        MultiDex.install(this);
    }

    public static Context getContext() {
        return context;
    }
}
