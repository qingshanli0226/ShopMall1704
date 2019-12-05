package com.example.shopmall;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.example.common.ConnectManager;
import com.example.common.CrashHandler;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        //初始化缓存管理类
        CaCheManager.getInstance(this).init(this);

        //初始化网络连接管理类
        ConnectManager.getInstance().init(this);
        //初始化异常
        CrashHandler.getInstance(this).init();

        StepManager.getInstance().init(this);
    }

    public static Context getContext() {
        return context;
    }
}
