package com.example.shopmall;

import android.app.Application;
import android.content.Context;

import com.example.framework.manager.ConnectManager;
import com.example.framework.manager.CrashHandler;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;
        //初始化网络连接管理类

        StepManager.getInstance().init(getContext());
        ConnectManager.getInstance().init(this);
        //初始化异常
        CrashHandler.getInstance(this).init();
        //初始化缓存管理类
        CaCheManager.getInstance().init(this);

    }

    public static Context getContext() {
        return context;
    }
}
