package com.example.administrator.shaomall.app;

import android.content.Context;
import android.os.Handler;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.example.commen.ACache;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.shaomall.framework.manager.NetConnectManager;
import com.shaomall.framework.manager.UserInfoManager;

import cn.jpush.android.api.JPushInterface;


public class ShaoHuaApplication extends MultiDexApplication {
    public static Context context;//需要使用上下文的对象
    public static Handler handler;//需要使用的handler
    public static Thread mainThread;//提供主线程对象
    public static int mainThreadId;//提供主线程对象的id
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
		context = this.getApplicationContext();
		handler = new Handler();
		mainThread = Thread.currentThread();//实例化当前的Application的线程为主线程
        mainThreadId = android.os.Process.myTid();//获取当前线程的Id

        ACache aCache = ACache.get(this);
        NetConnectManager.getInstance().init(this);
        UserInfoManager.getInstance().init(this, aCache);

        // 初始化 JPush
        JPushInterface.init(this);
        //发布时关闭日志
        JPushInterface.setDebugMode(true);
}

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
