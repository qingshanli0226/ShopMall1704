package com.example.framework.manager;

/*
  初始化init 开启服务, 注册记步的回调接口 ,在onDestory中注销服务
 */

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.text.format.Time;


import com.example.common.OrmUtils;
import com.example.framework.bean.HourBean;
import com.example.framework.bean.MessageStepBean;
import com.example.framework.bean.ShopStepBean;
import com.example.framework.sql.HourSql;
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

    private int cut;

    SQLiteDatabase hourDb;

    public static StepManager getInstance() {
        if (stepManager == null) {
            stepManager = new StepManager();
        }
        return stepManager;
    }


    //绑定服务
    public void init(Context ctx){
        this.context=ctx;

        Intent intent = new Intent(context, StepService.class);
         serviceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

                stepService=((StepService.StepBinder)iBinder).getService();

                stepService.registerListener(new StepService.UpdateUi() {


                    @Override
                    public void getUpdateStep(int count, int ingal) {
                    cut=count;
                        for (int i=0;i<stepManagerListeners.size();i++){
                            stepManagerListeners.get(i).onIntegral(ingal);
                            stepManagerListeners.get(i).onStepChange(count);

                        }

                        if(IntegalListeners.size()>0){
                            for (int i =0;i<IntegalListeners.size();i++){
                                IntegalListeners.get(i).onIntegral(ingal);
                            }
                        }

                    }


                });

            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        };

        context.bindService(intent,serviceConnection,Context.BIND_AUTO_CREATE);




        HourSql hourSql = new HourSql(context);
        hourDb = hourSql.getWritableDatabase();


    }



    public List<MessageStepBean> getMessDate(){
        Cursor cursor = hourDb.rawQuery("select time,date,currentStep,integral from mess", null);
        List<MessageStepBean> messageBeans=new ArrayList<>();
        while (cursor.moveToNext()){
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            int currentStep = cursor.getInt(cursor.getColumnIndex("currentStep"));
            int integral = cursor.getInt(cursor.getColumnIndex("integral"));
            MessageStepBean messageBean = new MessageStepBean(time, date, currentStep, integral);
            messageBeans.add(messageBean);
        }
        return messageBeans;

    }
    //存储消息信息
    public void saveMessSql(String time,String date,int current,int gal){

        ContentValues contentValues = new ContentValues();
        contentValues.put("time",time);
        contentValues.put("date",date);
        contentValues.put("currentStep",current);
        contentValues.put("integral",gal);

        hourDb.insert("mess",null,contentValues);
    }

    //存储每分钟记录
    public void insertHour(String time,String date,int current){
        ContentValues contentValues = new ContentValues();
        contentValues.put("time",time);
        contentValues.put("date",date);
        contentValues.put("currentStep",current);
        hourDb.insert("history",null,contentValues);
    }
    public List<HourBean> findHour(){
        Cursor cursor = hourDb.rawQuery("select distinct time,date,currentStep from history", null);
        List<HourBean> mlist=new ArrayList<>();
        while (cursor.moveToNext())
        {
            String time = cursor.getString(cursor.getColumnIndex("time"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            int currentStep = cursor.getInt(cursor.getColumnIndex("currentStep"));
            HourBean hourBean = new HourBean(time, date, currentStep);
            mlist.add(hourBean);
        }
        return mlist;

    }

    //获取现在地1日期
    public String getTodayDate(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }
    //获取现在的时间
    public String getToadyTime(){
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        return simpleDateFormat.format(date);
    };


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

    //获取没有的最后一天
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

    //获取每月的第一天
    public int getFirstDayMonth(int month){
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH,month-1);
        //某月最小天数
        int firstDay = calendar.getActualMinimum(Calendar.DAY_OF_MONTH);

        return firstDay;
    }


    //获取每周的日期
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
