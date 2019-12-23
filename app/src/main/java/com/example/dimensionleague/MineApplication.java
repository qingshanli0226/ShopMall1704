package com.example.dimensionleague;

import android.app.Application;

import com.example.common.utils.SPUtil;
import com.example.framework.manager.CrashHandler;
import com.example.framework.manager.NetConnectManager;
import com.example.point.StepIsSupport;
import com.example.point.stepmanager.StepPointManager;

import com.squareup.leakcanary.LeakCanary;


import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;

/**
 * author:李浩帆
 */
public class MineApplication extends Application {
    private Application applicationContext;
    @Override
    public void onCreate() {
        super.onCreate();

        applicationContext = this;
        //TODO 网络
        NetConnectManager.getInstance().init(applicationContext);
        //TODO SP存储工具类
        SPUtil.init(applicationContext);
        //TODO 异常捕获
        CrashHandler.getInstance().initErrorHandler(applicationContext);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        //支持计步的话就查找历史记录-否则就什么也不做
        if (NetConnectManager.getInstance().isNetConnectStatus() && new StepIsSupport().isSupportStepCountSensor(this)) {
            StepPointManager.getInstance(this).init();
        }
        JAnalyticsInterface.initCrashHandler(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        ZXingLibrary.initDisplayOpinion(this);
    }
}
