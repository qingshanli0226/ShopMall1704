package com.example.framework;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;


import com.example.common.OrmUtils;
import com.example.common.ShopStepBean;
import com.example.framework.manager.StepManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StepService extends Service implements SensorEventListener {


    public static String CURRENT_DATE = "";
    private static int duration=3000;
    private int systemStep;
    private int previousStep;
    private int currentStep;
    private boolean isFirst = false;

    private BroadcastReceiver broadcastReceiver;

    private  NotificationCompat.Builder nbuilder;
    private NotificationChannel channel;
    private NotificationManager notificationManager;


    private TimeCount timeCount;
    private UpdateUi updateUi;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new StepBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化通知,提到前台
        initNotification();
        //初始化当前日期
        initToday();
        //初始化广播
        initBoradCast();
        //初始化传感器
        new Thread(new Runnable() {

            @Override
            public void run() {
                initSensorListener();
            }
        }).start();

        startTimer();


    }

    public void registerListener(UpdateUi updateUi) {
        this.updateUi = updateUi;
    }

    private void startTimer() {
        if (timeCount == null) {
            timeCount = new TimeCount(duration, 1000);
        }
        timeCount.start();
    }

    private void initBoradCast() {
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction()) {
                    case Intent.ACTION_SCREEN_OFF:
                        duration=30000;
                        break;
                    case Intent.ACTION_SCREEN_ON:
                        duration=3000;
                        break;
                    case Intent.ACTION_SHUTDOWN:
                        StepManager.getInstance().save(CURRENT_DATE,currentStep,previousStep);
                        break;
                    case Intent.ACTION_DATE_CHANGED:
                        StepManager.getInstance().save(CURRENT_DATE,currentStep,previousStep);
                        isNewDay();
                        break;
                    case Intent.ACTION_TIME_CHANGED:
                        StepManager.getInstance().save(CURRENT_DATE,currentStep,previousStep);
                        isNewDay();
                        break;
                    case Intent.ACTION_TIME_TICK:
                        StepManager.getInstance().save(CURRENT_DATE,currentStep,previousStep);
                        isNewDay();
                        break;

                }

            }

        };

        if (broadcastReceiver != null) {
            registerReceiver(broadcastReceiver, StepManager.getInstance().getIntentFliter());
        }

    }

    //是否是凌晨0点
    private void isNewDay() {
        if ("00:00:00".equals(new SimpleDateFormat("HH:mm:ss").format(new Date()))) {
            initToday();
        }
    }



    private void initToday() {
        CURRENT_DATE = getTodayDate();
        OrmUtils.createDb(this, "DbStep");
        List<ShopStepBean> shopStepBeans = OrmUtils.getQueryByWhere(ShopStepBean.class, "day", new String[]{CURRENT_DATE});
        Log.e("##day", shopStepBeans.toString());
        if (shopStepBeans.size() == 0 || shopStepBeans.isEmpty()) {
            currentStep = 0;
        } else {
            currentStep = Integer.parseInt(shopStepBeans.get(0).getCurrent_step());
        }

        int count=0;
        List<ShopStepBean> queryAll = OrmUtils.getQueryAll(ShopStepBean.class);
        for (int i=0;i<queryAll.size();i++){
            count+=queryAll.get(i).getIntegral();
            if (updateUi != null) {
                updateUi.getUpdateStep(currentStep,count);
            }
        }
        updateNotification();
    }


    //更新通知
    @SuppressLint("NewApi")
    private void updateNotification() {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, StepManager.getInstance().getIntent(), PendingIntent.FLAG_CANCEL_CURRENT);
        nbuilder
                .setWhen(System.currentTimeMillis())
                .setContentTitle("用户您好!")
                .setContentText("您今天已经走了" + currentStep + "步,每天多运动,开心每一天!!")
                .setSmallIcon(R.mipmap.head)
                .setContentIntent(pendingIntent);


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            nbuilder.setChannelId(this.getPackageName());
            notificationManager.createNotificationChannel(channel);
        }
        notificationManager.notify(100, nbuilder.build());
        int count=0;
        List<ShopStepBean> queryAll = OrmUtils.getQueryAll(ShopStepBean.class);
        for (int i=0;i<queryAll.size();i++){
            count+=queryAll.get(i).getIntegral();
            if (updateUi != null) {
                updateUi.getUpdateStep(currentStep,count);
            }
        }



    }



    //获取今天的日期
    private String getTodayDate() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    //实时通知
    @SuppressLint("NewApi")
    private void initNotification() {
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.head);
        nbuilder=new NotificationCompat.Builder(this);
        nbuilder.setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("用户您好!")
                .setOngoing(true)
                .setSmallIcon(R.mipmap.head)
                .setContentText("您今天已经走了" + currentStep + "步,每天多运动,开心每一天!")
                 .setLargeIcon(bitmap)
                 .setDefaults(Notification.DEFAULT_ALL);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
             channel = new NotificationChannel(this.getPackageName(), "记步", NotificationManager.IMPORTANCE_DEFAULT);
            nbuilder.setChannelId(this.getPackageName());
            notificationManager.createNotificationChannel(channel);
        }
        startForeground(100, nbuilder.build());

    }

    public PendingIntent getDefalutIntent(int flags) {
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, new Intent(), flags);
        return pendingIntent;
    }

    //初始化注册传感器监听
    private void initSensorListener() {
        SensorManager manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = manager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor != null) {
            manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        Sensor ctor = manager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        manager.registerListener(this, ctor, SensorManager.SENSOR_DELAY_NORMAL);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    //实时获取步数
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        CURRENT_DATE = getTodayDate();
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            int sensorStep = (int) sensorEvent.values[0];
            if (!isFirst) {
                isFirst = true;
                systemStep = sensorStep;
            } else {
                int thisStep = sensorStep - systemStep;
                int i = thisStep - previousStep;
                currentStep += (i);
                previousStep = thisStep;
                Log.e("##THIS", systemStep + "--" + sensorStep + "---" + i + "--" + currentStep + "--" + previousStep+"-this"+thisStep);
            }


        } else if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR) {
            if (sensorEvent.values[0] == 1.0f) {
                currentStep++;
            }

        }
        updateNotification();


    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    //销毁应用
    @Override
    public void onDestroy() {
        super.onDestroy();
        //停止前台服务和注销广播
        stopForeground(true);
        unregisterReceiver(broadcastReceiver);
    }

    public interface UpdateUi {
        void getUpdateStep(int count,int ingal);
    }

    public class StepBinder extends Binder {
        public StepService getService() {
            return StepService.this;
        }
    }

    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

        }

        @Override
        public void onFinish() {

            timeCount.cancel();
            StepManager.getInstance().save(CURRENT_DATE,currentStep,previousStep);
            startTimer();
        }
    }



}
