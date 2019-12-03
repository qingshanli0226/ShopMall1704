package com.example.dimensionleague;

import android.app.Application;

import com.example.framework.manager.NetConnectManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetConnectManager.getInstance().init(this);
    }
}
