package com.example.dimensionleague;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Build;
import android.widget.Toast;

import com.example.framework.manager.NetConnectManager;
import com.example.point.StepIsSupport;
import com.example.point.StepPointManager;


/**
 * author:李浩帆
 */
public class ApplicationManager extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetConnectManager.getInstance().init(this);


       //支持计步的话就开启服务-否则就什么也不做
        if (new StepIsSupport().isSupportStepCountSensor(this)) {
            StepPointManager.getInstance(this).init();
        } else {

        }
    }


}
