package com.example.dimensionleague;

import android.app.Application;

import com.example.common.utils.SPUtil;
import com.example.framework.manager.NetConnectManager;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;


/**
 * author:李浩帆
 */
public class MineApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Application applicationContext = this;
        //TODO 网络
        NetConnectManager.getInstance().init(applicationContext);
        //TODO SP存储工具类
        SPUtil.init(applicationContext);
        //TODO 异常捕获
        // CrashHandler.getInstance().initErrorHandler(applicationContext);
        JAnalyticsInterface.initCrashHandler(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);

        ZXingLibrary.initDisplayOpinion(this);
    }
}
