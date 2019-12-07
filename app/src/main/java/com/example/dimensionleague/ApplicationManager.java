package com.example.dimensionleague;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import com.example.framework.manager.NetConnectManager;
import com.example.point.StepPointManager;
import com.example.point.service.StepService;


/**
 * author:李浩帆
 */
public class ApplicationManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetConnectManager.getInstance().init(this);
        StepPointManager.getInstance(this).init();



    }
}
