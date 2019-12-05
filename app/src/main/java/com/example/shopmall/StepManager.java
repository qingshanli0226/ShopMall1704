package com.example.shopmall;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.example.shopmall.bean.StepBean;
import com.example.shopmall.step.StepService;

public class StepManager {

    private static StepManager stepManager;
    private Context context;
    StepService stepService;
    ServiceConnection serviceConnection;
    StepManagerListener stepManagerListener;
    public static StepManager getInstance() {
        if (stepManager == null) {
            stepManager = new StepManager();
        }
        return stepManager;
    }

    public void init(Context context){
        this.context=context;
        Intent intent = new Intent(context, StepService.class);
         serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                stepService=((StepService.StepBinder)iBinder).getService();
                stepService.registerListener(new StepService.UpdateUi() {
                    @Override
                    public void getUpdateStep(int count) {
                        if(stepManagerListener!=null){
                            stepManagerListener.onStepChange(count);
                        }
                    }
                });
            }
    //添加步数,并通知接口回调activity返回步数
    public void addStep(StepBean stepBean) {
    }

    //根据时间返回数据记录
//    public List<StepBean> findStep(String date) {
//        QueryBuilder<StepBean> findDate = stepBeanDao.queryBuilder().where(StepBeanDao.Properties.Data.eq(date));
//        return findDate.list();
//    }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };
        context.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);
    }

    interface StepManagerListener{
        void onStepChange(int count);
    }

    public void registerListener(StepManagerListener stepManagerListener){
        this.stepManagerListener=stepManagerListener;
    }
    public void unRegisterLisener(){
        this.stepManagerListener=null;
        context.unbindService(serviceConnection);
    }

}
