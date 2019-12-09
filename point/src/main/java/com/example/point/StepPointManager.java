package com.example.point;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.example.point.activity.StepActivity;
import com.example.point.service.StepService;
import com.squareup.leakcanary.LeakCanary;

public class StepPointManager {
    public  static   StepPointManager stepPointManager;
    private Context context;
    private StepService stepService;
    private int step=0;
    public StepPointManager(Context context) {
        this.context = context;
    }
    //单例
    public static StepPointManager getInstance(Context context){
        if (stepPointManager==null){
            stepPointManager=new StepPointManager(context);
        }
        return stepPointManager;
    }
    public void init(){
        if (LeakCanary.isInAnalyzerProcess(context)) {
// This process is dedicated to LeakCanary for heap analysis.
// You should not init your app in this process.

            return;
        }
        LeakCanary.install((Application) this.context);

        Intent intent = new Intent(context, StepService.class);
        context.startService(intent);
        //绑定服务
        context. bindService(intent,connection , Context.BIND_AUTO_CREATE);
        context.startService(intent);
    }
    private ServiceConnection connection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                StepService stepService = ((StepService.StepBinder) iBinder).getService();
                initStep(stepService.getStepCount());

                stepService.registerCallback(new StepService.UpdateUiCallBack() {
                    @Override
                    public void updateUi(int stepCount) {
                        initStep(stepCount);
                        Log.i("updateUi", "updateUi: "+stepCount);
                    }
                });

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    public void  initStep(int step){
        this.step=step;
    }

    public int getStep(){
        return step;
    }
}
