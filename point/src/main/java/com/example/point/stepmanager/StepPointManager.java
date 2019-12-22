package com.example.point.stepmanager;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.point.service.StepService;

import java.util.ArrayList;
import java.util.List;

public class StepPointManager {
    public  static StepPointManager stepPointManager;
    private Context context;
    private StepService stepService;
    private int step;
    private List<GetStepListener> iStepChangeListeners = new ArrayList<>();
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


        Intent intent = new Intent(context, StepService.class);
        context.startService(intent);
        //绑定服务
        context. bindService(intent,connection , Context.BIND_AUTO_CREATE);
        context.startService(intent);
    }
    private ServiceConnection connection= new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
               stepService = ((StepService.StepBinder) iBinder).getService();
                stepService.registerCallback(new StepService.UpdateUiCallBack() {
                    @Override
                    public void updateUi(int stepCount) {
                        if (getStepListener!=null){
                            getStepListener.onsetStep(stepCount);
                        }
                    }
                });
        }
        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
    private  GetStepListener getStepListener;
    public void addGetStepListener(GetStepListener getStepListener){
        this.getStepListener=getStepListener;
    }
    public interface GetStepListener{
        void onsetStep(int step);
    }
    public void unRegisterLisener(){
        this.getStepListener=null;
        context.unbindService(connection);
    }
}
