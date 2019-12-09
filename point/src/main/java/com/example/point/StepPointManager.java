package com.example.point;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.point.service.StepService;

public class StepPointManager {
    public  static StepPointManager stepPointManager;
    private Context context;
    private StepService stepService;
    private int step;

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
                        if(getStepListener!=null){
                            getStepListener.setStep(stepCount);
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
//    public void  initStep(int step){
//        this.step=step;
//    }
//
//    public int getStep(){
//        return step;
//    }
    public interface GetStepListener{
        void setStep(int step);
    }
    public void unRegisterLisener(){
        this.getStepListener=null;
        context.unbindService(connection);
    }
}
