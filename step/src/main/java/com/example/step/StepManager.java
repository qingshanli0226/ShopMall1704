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
                    public void getUpdateStep(int count, int ingal) {
//                        getInterGal();
                        if(stepManagerListener!=null){
                            stepManagerListener.onStepChange(count);
                            stepManagerListener.onIntegral(ingal);
                        }
                    }


                });



            }

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



//    private void saveOrm(int intGal) {
//
//        List<Gal> queryAll1 = OrmUtils.getQueryAll(Gal.class);
//        if(queryAll1.size()==0||queryAll1.isEmpty()){
//            OrmUtils.createDb(context,"Integral");
//            Gal gal = new Gal();
//            gal.setIntegral(intGal);
//            OrmUtils.insert(gal);
//            Log.e("##QQSS","888--");
//        }else if(queryAll1.size()==1){
//            Gal gal = queryAll1.get(0);
//            gal.setIntegral(intGal);
//            OrmUtils.update(gal);
//            List<Gal> queryAll = OrmUtils.getQueryAll(Gal.class);
//            Log.e("##Q",queryAll.toString()+"");
//
//        }
//    }

//    public int getIntgerGal(){
//         int count=0;
//        for (int i=0;i<OrmUtils.getQueryAll(Gal.class).size();i++){
//            count+=OrmUtils.getQueryAll(Gal.class).get(i).getIntegral();
//        }
//        return count;
//    }
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
        this.stepManagerListener=stepManagerListener;
    }
    public void unRegisterLisener(){
        this.stepManagerListener=null;
        context.unbindService(serviceConnection);
    }

}
