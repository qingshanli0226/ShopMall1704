package com.example.shopmall;


import android.app.Application;
import android.content.Context;
import android.content.Intent;


import com.example.common.ConnectManager;
import com.example.common.CrashHandler;
import com.example.shopmall.activity.MainActivity;
import com.example.step.StepManager;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;

        StepManager.getInstance().init(this);

        //点击通知跳转MainActivity
        Intent intent = new Intent(this, MainActivity.class);
        StepManager.getInstance().setActivityStack(intent);

        ConnectManager.getInstance().init(this);
        CrashHandler.getInstance(this).init();

    }

    public static Context getContext() {
        return context;
    }
}
