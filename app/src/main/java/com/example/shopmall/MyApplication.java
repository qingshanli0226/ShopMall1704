package com.example.shopmall;


import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.framework.manager.ConnectManager;
import com.example.framework.manager.CrashHandler;
import com.example.framework.manager.MessageManager;
import com.example.framework.manager.UserManager;
import com.example.shopmall.activity.MainActivity;
import com.example.framework.manager.StepManager;
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

        //点击通知跳转MainActivity
        Intent intent = new Intent(this, MainActivity.class);

        StepManager.getInstance().setActivityIntent(intent);

        UserManager.getInstance().init(this);

        ConnectManager.getInstance().init(this);
        //初始化异常
        CrashHandler.getInstance(this).init();

        //初始化消息数据库
        MessageManager.getMessageManager().init(this);
        StatConfig.setDebugEnable(true);
        // 基础统计API
        StatService.registerActivityLifecycleCallbacks(this);
    }

    public static Context getContext() {
        return context;
    }
}
