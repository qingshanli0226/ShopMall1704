package com.example.framework.manager;

/*
  初始化init 开启服务, 注册记步的回调接口 ,在onDestory中注销服务
 */

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.text.format.Time;
import android.util.Log;


import com.example.common.OrmUtils;
import com.example.framework.bean.ShopStepBean;
import com.example.framework.bean.ShopStepTimeRealBean;
import com.example.framework.greendao.DaoMaster;
import com.example.framework.greendao.DaoSession;
import com.example.framework.greendao.ShopStepTimeRealBeanDao;
import com.example.framework.service.StepService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class StepManager {

    private static StepManager stepManager;
    private Context context;

    StepService stepService;
    ServiceConnection serviceConnection;
    List<StepManagerListener> stepManagerListeners=new ArrayList<>();
    List<StepIntegalListener> IntegalListeners=new ArrayList<>();
    private Intent intent;
    ShopStepTimeRealBeanDao realBeanDao;
    private static final String SQL_DISTINCT="SELECT DISTINCT"+ShopStepTimeRealBeanDao.Properties.Time+"FROM"+ShopStepTimeRealBeanDao.TABLENAME;

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

                        for (int i =0;i<IntegalListeners.size();i++){
                            IntegalListeners.get(i).onIntegral(ingal);
                        }

                    }


                });




            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        context.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);

        SQLiteDatabase database = new DaoMaster.DevOpenHelper(context, "realTimes.db", null).getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(database);
        DaoSession daoSession = daoMaster.newSession();
        realBeanDao = daoSession.getShopStepTimeRealBeanDao();

    }




    public void saveReal(String time,String date,int current){
        ShopStepTimeRealBean shopStepTimeRealBean = new ShopStepTimeRealBean(null, time, date, current);
        realBeanDao.insertOrReplace(shopStepTimeRealBean);
    }
    public List<ShopStepTimeRealBean> getReal(){


        return realBeanDao.queryBuilder().list();
    }

    public boolean isThisMonth(long time){
        return isThisTime(time,"yyyy-MM-dd");
    }
    public boolean isThisTime(long time,String pattern){

        Calendar calendar = Calendar.getInstance();


        Date date = new Date(time);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        String param = simpleDateFormat.format(date);

        String now = simpleDateFormat.format(new Date());

        if(param.equals(now)){

            return true;
        }
        return false;

    }

    public int getLastDayMonth(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,month-1);
        int lastDay=0;
        if(month==2){
            lastDay=calendar.getLeastMaximum(Calendar.DAY_OF_MONTH);
        }else{
            lastDay=calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
        return lastDay;
    }

    public int getFirstDayMonth(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,month-1);
        //某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

        return firstDay;
    }


    public  List<String> getWeekDay(){
        Calendar calendar = Calendar.getInstance();
        //本周的第一天
        int firstDayOfWeek = calendar.getFirstDayOfWeek();
        List<String> list=new ArrayList<>();
        for (int i=0;i<7;i++){
            calendar.set(Calendar.DAY_OF_WEEK,firstDayOfWeek+i);
            String format = new SimpleDateFormat("yyyy-MM-dd").format(calendar.getTime());
            list.add(format);
        }
        return list;
    }
    //是否本周
//    public boolean isThisWeek(long time){
//        Calendar calendar = Calendar.getInstance();
//        int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
//        calendar.setTime(new Date(time));
//        int targetWeek = calendar.get(Calendar.WEEK_OF_YEAR);
//        if(targetWeek==currentWeek){
//            Log.e("Show","True");
//            return true;
//        }
//        Log.e("Show","False"+currentWeek+"--"+targetWeek);
//        return false;
//
//    }
    //是否在 0.0 -0.0时间段
    public boolean isCurrentTimeRange(int beginHour,int beginMin,int endHour,int endMin){
        boolean result=false;
        final long DayInMills=1000*60*60*24;
        final long currentMills=System.currentTimeMillis();
        Time now=new Time();
        now.set(currentMills);

        Time startTime=new Time();
        startTime.set(currentMills);

        startTime.hour=beginHour;
        startTime.minute=beginMin;

        Time endTime=new Time();
        endTime.set(currentMills);
        endTime.hour=endHour;
        endTime.minute=endMin;
        if(!startTime.before(endTime)){
            startTime.set(startTime.toMillis(true)-DayInMills);
            result=!now.before(startTime)&& !now.after(endTime);
            Time startThisDay=new Time();
            startThisDay.set(startTime.toMillis(true)+DayInMills);
            if(!now.before(startThisDay)){
                result=true;
            }
        }else{
            result=!now.before(startTime)&&!now.after(endTime); //start<=now <=end
        }
        return result;
    }
    //广播
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
    //跳转Activity
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

    public interface StepIntegalListener{
        void onIntegral(int integal);
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

    public void registerIntalListenr(StepIntegalListener stepIntegalListener){
        if(!IntegalListeners.contains(stepIntegalListener)){
            this.IntegalListeners.add(stepIntegalListener);
        }
    }
    public void unInstaLitener(StepIntegalListener stepIntegalListener){
        IntegalListeners.remove(stepIntegalListener);

    }


}
