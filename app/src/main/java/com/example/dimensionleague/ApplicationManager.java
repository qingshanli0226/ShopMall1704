package com.example.dimensionleague;

import android.app.Application;

import com.example.framework.manager.NetConnectManager;

/**
 * author:李浩帆
 */
public class ApplicationManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetConnectManager.getInstance().init(this);
    }
}
