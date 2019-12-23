package com.example.dimensionleague;

import android.app.Application;
import com.example.common.utils.SPUtil;
import com.example.framework.manager.NetConnectManager;
import com.example.point.StepIsSupport;
import com.example.point.stepmanager.StepPointManager;

import com.squareup.leakcanary.LeakCanary;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

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
//        ErrorHandler.getInstance().initErrorHandler(applicationContext);
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        //支持计步的话就查找历史记录-否则就什么也不做
        if (NetConnectManager.getInstance().isNetConnectStatus() && new StepIsSupport().isSupportStepCountSensor(this)) {
            StepPointManager.getInstance(this).init();
        }

        ZXingLibrary.initDisplayOpinion(this);
    }
}
