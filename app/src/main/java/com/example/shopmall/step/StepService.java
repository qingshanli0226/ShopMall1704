package com.example.shopmall.step;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shopmall.R;
import com.example.shopmall.activity.MainActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StepService extends Service implements SensorEventListener {


    public static String CURRENT_DATE = "";
    private int systemStep;
    private int previousStep;
    private int currentStep;
    private boolean isFirst = false;

    private BroadcastReceiver broadcastReceiver;


    private Notification.Builder nbuilder;
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
        initNotification();
        updateNotification();
        initToday();
        initBoradCast();


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
            timeCount = new TimeCount(2000, 1000);
        }
        timeCount.start();
    }

    private void initBoradCast() {
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


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                switch (intent.getAction()) {
                    case Intent.ACTION_SCREEN_ON:
                        break;
                    case Intent.ACTION_SHUTDOWN:
                        save();
                        break;
                    case Intent.ACTION_DATE_CHANGED:
                        Log.e("##DateC", "Dchane");
                        save();
                        isNewDay();
                        break;
                    case Intent.ACTION_TIME_CHANGED:
                        Log.e("##TimdChcane", "change");
                        save();
                        break;
                    case Intent.ACTION_TIME_TICK:
                        Log.e("##TimeC", "Time");
                        save();
                        isNewDay();
                        break;

                }

            }
        };

        if (broadcastReceiver != null) {
            registerReceiver(broadcastReceiver, intentFilter);
        }

    }

    //是否是凌晨0点
    private void isNewDay() {
        if ("00:00:00".equals(new SimpleDateFormat("HH:mm:ss").format(new Date()))) {
            initToday();
        }
    }

    public List<ShopStepBean> getHisSteptory() {
        List<ShopStepBean> queryAll = OrmUtils.getQueryAll(ShopStepBean.class);
        if (queryAll != null) {
            return queryAll;
        } else {
            return null;
        }
    }

    private void initToday() {
        CURRENT_DATE = getTodayDate();
        OrmUtils.createDb(this, "DbStep");
        List<ShopStepBean> shopStepBeans = OrmUtils.getQueryByWhere(ShopStepBean.class, "day", new String[]{CURRENT_DATE});
        Log.e("##day", shopStepBeans.toString());
        if (shopStepBeans.size() == 0 || shopStepBeans.isEmpty()) {
            currentStep = 0;
        } else if (shopStepBeans.size() == 1) {
            currentStep = Integer.parseInt(shopStepBeans.get(0).getCurrent_step());
        }


        updateNotification();
    }

    //更新通知
    @SuppressLint("NewApi")
    private void updateNotification() {

        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        notificationManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
//        channel = new NotificationChannel(this.getPackageName(), "通知记步", NotificationManager.IMPORTANCE_DEFAULT);
        nbuilder = new Notification.Builder(this);
        nbuilder.setSmallIcon(R.mipmap.custome_head)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("用户您好!")
                .setContentText("您今天已经走了" + currentStep + "步,每天多运动,开心每一天!!")
                .setContentIntent(pendingIntent);
//                .setChannelId(this.getPackageName());
//        notificationManager.createNotificationChannel(channel);
        notificationManager.notify(100, nbuilder.build());
        if (updateUi != null) {
            updateUi.getUpdateStep(currentStep);
        }


    }

    private void save() {
        List<ShopStepBean> queryByWhere = OrmUtils.getQueryByWhere(ShopStepBean.class, "day", new String[]{CURRENT_DATE});
        if (queryByWhere.size() == 0 || queryByWhere.isEmpty()) {

            ShopStepBean shopStepBean = new ShopStepBean();
            shopStepBean.setDate(CURRENT_DATE + "");
            shopStepBean.setCurrent_step(currentStep + "");
            shopStepBean.setYesCurrent(previousStep + "");
            OrmUtils.insert(shopStepBean);
        } else if (queryByWhere.size() == 1) {
            ShopStepBean shopStepBean = queryByWhere.get(0);
            shopStepBean.setCurrent_step(currentStep + "");
            shopStepBean.setYesCurrent(previousStep + "");
            OrmUtils.update(shopStepBean);
        }

        getHisSteptory();
        Log.e("##save", queryByWhere.size() + "--" + queryByWhere.toString());


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
//        channel = new NotificationChannel(this.getPackageName(), "通知记步", NotificationManager.IMPORTANCE_DEFAULT);
        nbuilder = new Notification.Builder(this);
        nbuilder.setSmallIcon(R.mipmap.custome_head)
                .setContentIntent(getDefalutIntent(Notification.FLAG_ONGOING_EVENT))
                .setPriority(Notification.PRIORITY_DEFAULT)
                .setWhen(System.currentTimeMillis())
                .setContentTitle("用户您好!")
                .setContentText("您今天已经走了" + currentStep + "步,每天多运动,开心每一天!");
//                .setChannelId(this.getPackageName());
//        notificationManager.createNotificationChannel(channel);
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
        manager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);


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
                systemStep = sensorStep;
                isFirst = true;
            } else {
                int thisStep = sensorStep - systemStep;
                int i = thisStep - previousStep;
                currentStep += i;
                previousStep = thisStep;
                Log.e("##THIS", systemStep + "--" + sensorStep + "---" + i + "--" + currentStep + "--" + previousStep);
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
        void getUpdateStep(int count);
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
            save();
            startTimer();
        }
    }


}
