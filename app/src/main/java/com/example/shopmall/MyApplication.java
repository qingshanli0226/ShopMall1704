package com.example.shopmall;

import android.app.Application;
import android.net.ConnectivityManager;

import com.example.common.ConnectManager;

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ConnectManager.getInstance().init(this);
    }
}
