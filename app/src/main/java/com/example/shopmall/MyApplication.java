package com.example.shopmall;


import android.app.Application;
import android.content.Context;
import android.content.Intent;

import com.example.framework.manager.ConnectManager;
import com.example.framework.manager.CrashHandler;
import com.example.shopmall.activity.MainActivity;
import com.example.step.StepManager;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //初始化网络连接管理类

        ConnectManager.getInstance().init(this);
        //初始化异常
        CrashHandler.getInstance(this).init();

        StepManager.getInstance().init(this);
        //点击通知跳转MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        StepManager.getInstance().setActivityIntent(intent);



    }

    public static Context getContext() {
        return context;
    }
}
