package com.example.dimensionleague;

import android.app.Application;

import com.example.common.utils.SPUtil;
import com.example.framework.manager.ErrorHandler;
import com.example.framework.manager.NetConnectManager;

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
//        ErrorHandler.getInstance().initErrorHandler(applicationContext);
    }
}
