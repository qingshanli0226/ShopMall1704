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

        StepManager.getInstance().init(getContext());
        ConnectManager.getInstance().init(this);
        CrashHandler.getInstance(this).init();

    }

    public static Context getContext() {
        return context;
    }
}
