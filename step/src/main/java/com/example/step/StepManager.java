package com.example.step;

/*
  初始化init 开启服务, 注册记步的回调接口 ,在onDestory中注销服务
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.widget.Toast;
import java.util.List;

public class StepManager {

    private static StepManager stepManager;
    private Context context;
    StepService stepService;
    ServiceConnection serviceConnection;
    StepManagerListener stepManagerListener;

    SharedPreferences sharedPreferences;

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
                    public void getUpdateStep(int count) {
                        if(stepManagerListener!=null){
                            stepManagerListener.onStepChange(count);
                        }
                    }
                });
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


       sharedPreferences = context.getSharedPreferences("Gal", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sharedPreferences.edit();
        edit.putInt("gal",0);
        edit.commit();


    }

//    Stack<Activity> activityStack=new Stack<>();
//    public Stack<Activity> getActivityStack() {
//        return activityStack;
//    }

    public void setActivityStack(Intent intent){
//        activityStack.add(activity);
        this.intent=intent;
        getIntent();
   }
   public Intent getIntent(){
//            Activity activity = getActivityStack().get(0);
//            activity.getClass();
//            intent = new Intent(context, activity.getClass());
        if(this.intent !=null){
           return this.intent;

        }else{
            return null;
        }
   }
    public int getGal(){
        getInterGal();
        int gal = sharedPreferences.getInt("gal", 0);
        return gal;
    }

    public void getInterGal(){
        List<ShopStepBean> queryAll = OrmUtils.getQueryAll(ShopStepBean.class);

        if(queryAll!=null){
        for (int i=0;i<queryAll.size();i++){
            String current_step = queryAll.get(i).getCurrent_step();
            int i1 = Integer.parseInt(current_step);
            if(i1>100){
                int intGal =(int) i1 / 100;
                int gal = sharedPreferences.getInt("gal", 0);
                if(gal>0){
                    gal+=intGal;
                    SharedPreferences.Editor edit = sharedPreferences.edit();
                    edit.putInt("gal",gal);
                    edit.commit();
                }else{

                }


            }else {
                //步数没有到达要求
            }
        }

        }else{
            Toast.makeText(context, "您目前没有积分,今天运动一下把!", Toast.LENGTH_SHORT).show();
        }

    }

    //获取历史记录
    public List<ShopStepBean> getStepHistory(){
        List<ShopStepBean> queryAll = OrmUtils.getQueryAll(ShopStepBean.class);
        return queryAll;
    }
    public interface StepManagerListener{
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
