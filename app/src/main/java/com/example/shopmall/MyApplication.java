package com.example.shopmall;


import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.framework.manager.CaCheManager;
import com.example.framework.manager.ConnectManager;
import com.example.framework.manager.CrashHandler;
import com.example.framework.manager.UserManager;
import com.example.shopmall.activity.MainActivity;
import com.example.framework.manager.StepManager;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        ConnectManager.getInstance().init(this);
        //初始化异常
        CrashHandler.getInstance(this).init();

        StepManager.getInstance().init(this);
        //初始化缓存管理类
        CaCheManager.getInstance(this).init(this);
        //点击通知跳转MainActivity
        Intent intent = new Intent(this, MainActivity.class);

        StepManager.getInstance().setActivityIntent(intent);

        UserManager.getInstance().init(this);

        ConnectManager.getInstance().init(this);
        //初始化异常
        CrashHandler.getInstance(this).init();
        //初始化缓存管理类


    }

    public static Context getContext() {
        return context;
    }
}
