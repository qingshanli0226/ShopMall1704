package com.example.administrator.shaomall.app;

import android.app.Application;
import android.content.Context;

import com.example.administrator.shaomall.cache.CacheManager;
import com.example.commen.ACache;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.shaomall.framework.manager.NetConnetMannager;
import com.shaomall.framework.manager.UserInfoManager;

import cn.jpush.android.api.JPushInterface;


public class ShaoHuaApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
		context = this.getApplicationContext();
        ACache aCache = ACache.get(this);
        NetConnetMannager.getInstance().init(this);
        UserInfoManager.getInstance().init(this, aCache);

        // 初始化 JPush
        JPushInterface.init(this);
        //发布时关闭日志
        JPushInterface.setDebugMode(true);
}

}
