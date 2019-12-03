package com.example.shopmall;

import android.app.Application;
import android.net.ConnectivityManager;

import com.example.common.ConnectManager;
import com.example.common.CrashHandler;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectManager.getInstance().init(this);
        CrashHandler.getInstance(this).init();
    }
}
