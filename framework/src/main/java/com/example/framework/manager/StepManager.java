package com.example.framework.manager;

/*
  初始化init 开启服务, 注册记步的回调接口 ,在onDestory中注销服务
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;


import com.example.common.OrmUtils;
import com.example.common.ShopStepBean;
import com.example.framework.StepService;

import java.util.ArrayList;
import java.util.List;

public class StepManager {

    private static StepManager stepManager;
    private Context context;

    StepService stepService;
    ServiceConnection serviceConnection;
    List<StepManagerListener> stepManagerListeners=new ArrayList<>();
    private Intent intent;

    public static StepManager getInstance() {
        if (stepManager == null) {
            stepManager = new StepManager();
        }
        return stepManager;
    }


    //绑定服务
    public void init(Context context){
        this.context=context;

        Intent intent = new Intent(context, StepService.class);
         serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                stepService=((StepService.StepBinder)iBinder).getService();

                stepService.registerListener(new StepService.UpdateUi() {
                    @Override
                    public void getUpdateStep(int count, int ingal) {

                        for (int i=0;i<stepManagerListeners.size();i++){
                            stepManagerListeners.get(i).onIntegral(ingal);
                            stepManagerListeners.get(i).onStepChange(count);
                        }

                    }


                });



            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        context.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);


    }




//    public static boolean isCurrentInTime(int beginHour,int beginMin,int endHour,int endbeginMin){
//
//        boolean result=false;
//
//    }
    public IntentFilter getIntentFliter(){
        IntentFilter intentFilter = new IntentFilter();

        //屏幕熄灭,屏幕亮开
        intentFilter.addAction(Intent.ACTION_SCREEN_OFF);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        //关机
        intentFilter.addAction(Intent.ACTION_SHUTDOWN);
        //时间和日期的监听
        intentFilter.addAction(Intent.ACTION_DATE_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_CHANGED);
        intentFilter.addAction(Intent.ACTION_TIME_TICK);
        return intentFilter;
    }

    //保存数据
    public void save(String date,int current,int previous){
        List<ShopStepBean> queryByWhere = OrmUtils.getQueryByWhere(ShopStepBean.class, "day", new String[]{date});
        if (queryByWhere.size() == 0 || queryByWhere.isEmpty()) {
            ShopStepBean shopStepBean = new ShopStepBean();
            shopStepBean.setDate(date + "");
            shopStepBean.setCurrent_step(current + "");
            shopStepBean.setYesCurrent(previous + "");
            int i1 =(int) current / 100;
            shopStepBean.setIntegral(i1);
            OrmUtils.insert(shopStepBean);
        } else if (queryByWhere.size() == 1) {
            ShopStepBean shopStepBean = queryByWhere.get(0);
            shopStepBean.setCurrent_step(current + "");
            shopStepBean.setYesCurrent(previous + "");
            int i1 =(int) current / 100;
            shopStepBean.setIntegral(i1);
            OrmUtils.update(shopStepBean);
        }
    }
    public void setActivityIntent(Intent intent){
        this.intent=intent;
        getIntent();
   }
   public Intent getIntent(){
        if(this.intent !=null){
           return this.intent;

        }else{
            return null;
        }
   }


    //获取历史记录
    public List<ShopStepBean> getStepHistory(){
        List<ShopStepBean> queryAll = OrmUtils.getQueryAll(ShopStepBean.class);
        return queryAll;
    }
    public interface StepManagerListener{
        void onStepChange(int count);
        void onIntegral(int intgal);

    }
    public void registerListener(StepManagerListener stepManagerListener){
        if(!stepManagerListeners.contains(stepManagerListener)){
        this.stepManagerListeners.add(stepManagerListener);
        }
    }
    public void unRegisterLisener(StepManagerListener stepManagerListener){
        stepManagerListeners.remove(stepManagerListener);
        context.unbindService(serviceConnection);
    }

}
