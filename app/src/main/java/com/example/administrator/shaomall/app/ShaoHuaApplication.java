package com.example.administrator.shaomall.app;

import android.app.Application;
import android.content.Context;

import com.example.administrator.shaomall.cache.CacheManager;
import com.example.commen.ACache;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.shaomall.framework.manager.NetConnetMannager;
import com.shaomall.framework.manager.UserInfoManager;

public class ShaoHuaApplication extends Application {
    public static Context context;
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
		context = this.getApplicationContext();
        ACache aCache = ACache.get(this);
        NetConnetMannager.getInstance().init(this);
        CacheManager.getInstance().init(this);
        UserInfoManager.getInstance().init(this, aCache);
    }
}
