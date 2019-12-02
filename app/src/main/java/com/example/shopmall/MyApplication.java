package com.example.shopmall;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;

import com.example.common.ConnectManager;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = this;

        ConnectManager.getInstance().init(this);
    }

    public static Context getContext() {
        return context;
    }
}
