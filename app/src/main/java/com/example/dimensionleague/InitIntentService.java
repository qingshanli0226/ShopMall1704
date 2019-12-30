package com.example.dimensionleague;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.common.utils.SPUtil;
import com.example.framework.manager.NetConnectManager;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

import cn.jiguang.analytics.android.api.JAnalyticsInterface;
import cn.jpush.android.api.JPushInterface;
import me.jessyan.autosize.AutoSizeConfig;

public class InitIntentService extends IntentService {
    private static final String ACTION_INIT = "INIT";

    public InitIntentService() {
        super("InitIntentService");
    }

    public static void start(Context context){
        Intent intent = new Intent(context, InitIntentService.class);
        intent.setAction(ACTION_INIT);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent!=null&&intent.getAction().equals(ACTION_INIT)){
            //TODO 网络
            NetConnectManager.getInstance().init(getApplicationContext());
            //TODO 异常捕获
            // CrashHandler.getInstance().initErrorHandler(applicationContext);
            JAnalyticsInterface.initCrashHandler(getApplicationContext());
            JPushInterface.setDebugMode(true);
            JPushInterface.init(getApplicationContext());
            AutoSizeConfig.getInstance().setCustomFragment(false).setUseDeviceSize(true);
            ZXingLibrary.initDisplayOpinion(getApplicationContext());
            //初始化缓存
            CacheManager.getInstance();
        }
    }
}
