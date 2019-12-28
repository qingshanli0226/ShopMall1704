package com.example.dimensionleague;

import android.app.Application;

import com.example.common.utils.SPUtil;
import com.example.framework.manager.NetConnectManager;

import com.tbruyelle.rxpermissions2.RxPermissions;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;
import me.jessyan.autosize.AutoSizeConfig;


/**
 * author:李浩帆
 */
public class MineApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //IntentService中进行初始化,提升app启动速度
        InitIntentService.start(this);
        //TODO SP存储工具类
        SPUtil.init(getApplicationContext());
    }
}
