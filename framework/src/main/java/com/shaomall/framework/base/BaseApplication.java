package com.shaomall.framework.base;

import android.app.Application;

import com.example.commen.Constants;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class BaseApplication extends Application {
    private static BaseApplication instance;
    private RefWatcher mRefWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        //内存泄漏检测
        mRefWatcher = Constants.DEBUG ? LeakCanary.install(this) : RefWatcher.DISABLED;
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    public static RefWatcher getRefWatcher() {
        return getInstance().mRefWatcher;
    }
}
