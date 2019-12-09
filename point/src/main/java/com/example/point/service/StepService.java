package com.example.point.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.point.R;
import com.example.point.activity.StepActivity;
import com.example.point.database.StepData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StepService extends Service implements SensorEventListener {
    //通知管理
     private  NotificationManager service;
    //创建我们的通知
    private Notification.Builder builder;
    //计步数据库
    private StepData stepData;
    //上一次的步数
    private int previousStepCount = 0;
    // 当前的日期
    private static String CURRENT_DATE = "";
    //每次打开APP时是否获取系统的步数
    private boolean hasData=false;
    /**
     * 保存记步计时器
     */
    private TimeCount time;
    //当前所走的步数
    private int CURRENT_STEP;
    //IBinder对象，向Activity传递数据的桥梁
    private StepBinder stepBinder = new StepBinder();
    private int stepCount;
    //计步器管理
    private SensorManager sensorManager;
    private  SQLiteDatabase database;

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                save();
            }
        }
    };
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int stepValue = (int) sensorEvent.values[0];//得到系统返回的计步数据
        if (!hasData) {
            hasData = true;
            stepCount = stepValue;
        } else {
            //获取APP打开到现在的总步数=本次系统回调的总步数-APP打开之前已有的步数
            int thisStepCount = stepValue - stepCount;
            //本次有效步数=（APP打开后所记录的总步数-上一次APP打开后所记录的总步数）
            int thisStep = thisStepCount - previousStepCount;
            //总步数=现有的步数+本次有效步数
            CURRENT_STEP += (thisStep);
            //记录最后一次APP打开到现在的总步数
            previousStepCount = thisStepCount;
        }
    }
    /**
     * 开始保存记步数据
     */
    private void startTimeCount() {
        if (time == null) {
            time = new TimeCount(duration, 1000);
        }
        time.start();
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
    //获取当前service对象
    public class StepBinder extends Binder{
        public StepService getService(){
            return StepService.this;
        }
    }
    // 默认为30秒进行一存储
    private static int duration = 30 * 1000;
    //广播接受者
    private BroadcastReceiver mBatInfoReceiver;
    @Override
    public void onCreate() {
        super.onCreate();
        stepData=new StepData(this);
        database = stepData.getWritableDatabase();
        builder=new Notification.Builder(this);
        service = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        builder=new Notification.Builder(this);


        initNotification();//已经调好
        initTodayData();//已经调好
        initBroadcastReceiver();
        new Thread(new Runnable() {
            @Override
            public void run() {
                startStepDetector();
            }
        }).start();

        startTimeCount();
    }
    //获取传感器的实例
    private void startStepDetector() {
        sensorManager=(SensorManager) this
                .getSystemService(Context.SENSOR_SERVICE);
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if (sensor != null) {
            sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
        Sensor ctor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        sensorManager.registerListener(this, ctor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void initBroadcastReceiver() {


        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF); // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SHUTDOWN);       //关机广播
        filter.addAction(Intent.ACTION_SCREEN_ON);        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_USER_PRESENT);// 屏幕解锁广播
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //开启启动
        filter.addAction(Intent.ACTION_BOOT_COMPLETED);
        //监听日期变化
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);

        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d("screen", "屏幕亮屏广播 ");
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d("screen", " 屏幕灭屏广播");
                    duration = 60000;  //改为60秒一存储
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.d("screen", " 屏幕解锁广播");
                    duration = 30000; //改为30秒一存储
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    Log.i("receive", " home     保存");
                    save();  //保存一次
                } else if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
                    Log.i("receive", " receive 关机广播");
                    save();
                } else if (Intent.ACTION_DATE_CHANGED.equals(action)) {//日期变化步数重置为0
                    Log.i("receive", " 日期改变");
                    isNewDay();
                }else if (Intent.ACTION_TIME_TICK.equals(action)) {//日期变化步数重置为0
                    Log.i("receive", " 时间变动");
                    isCall();
                    save();
                    isNewDay();
                }
                else if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
                    Intent StartIntent = new Intent(context, StepActivity.class);
                    StartIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(StartIntent);

                }
            }
        };
        registerReceiver(mBatInfoReceiver, filter);
    }

    //监听提现用户锻炼
    private void isCall(){
        SharedPreferences step = getSharedPreferences("Step", MODE_PRIVATE);
        String time = step.getString("time", "21:00");
        boolean isremind = step.getBoolean("isremind", false);
        int stepi = step.getInt("step", 7000);
        //判断一下存储的日期是否是今天 和当前步数是否超过锻炼步数 是否有开启提现
        if (isremind&&CURRENT_STEP<stepi &&time.equals(new SimpleDateFormat("HH:mm").format(new Date()))){
            Log.i("监听提现用户锻炼", " 监听提现用户锻炼");
            builder.setContentTitle("更新今日步数" + CURRENT_STEP + " 步")
                    .setContentText("距离目标还差" + (stepi - CURRENT_STEP) + "步，加油！")
                    .setTicker(getResources().getString(R.string.app_name) + "提醒您开始锻炼了")//通知首次出现在通知栏，带上升动画效果的
                    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                    .setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消
                    .setOngoing(true)//一个正在进行的通知。
                    .setSmallIcon(R.mipmap.logo);

            service = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT>=16){
                service.notify(100,builder.build());
            }
        }
    }

    //初始化当天的步数
    private void initTodayData() {
        CURRENT_DATE = DateFormat.format("MM-dd", System.currentTimeMillis())+"";//今日日期
        Log.i("initTodayData", "initTodayData: "+CURRENT_DATE);
        //查询数据库
        Cursor step = database.query("step", null, null, null, null, null, null);
        int b = step.getCount();
        if (b!=0){
            while (step.moveToNext()) {
                String stepString = step.getString(step.getColumnIndex("curr_date"));
                int number = step.getInt(step.getColumnIndex("number"));
                //判断数据库是否和当天日期相同
                if (stepString.equals(CURRENT_DATE)) {
                    //相同的话就把数据库的步数赋值给当天的步数
                    CURRENT_STEP = number;
                    Log.i("number", " number" + number);
                }
            }
        } else {
                CURRENT_STEP=0;
                //数据库没有的情况下  进行第一次插入
                ContentValues values = new ContentValues();
                values.put("curr_date", CURRENT_DATE);
                values.put("number", CURRENT_STEP);
            Log.i("CURRENT_STEP", " CURRENT_STEP" + CURRENT_STEP);
                database.insert("step", null, values);

        }
        handler.sendEmptyMessageDelayed(1,duration);
        initNotification();
    }
    //将今日步数存入数据库
    private void save(){
        Cursor step = database.query("step", null, null, null, null, null, null);
        int b = step.getCount();
        if (b!=0){
            while (step.moveToNext()) {
                String stepString = step.getString(step.getColumnIndex("curr_date"));
                //如果数据库已经有了今天的日期   我们直接进行更新数据库中的步数即可
                Log.i("stepString", " stepString"+stepString);
                if (stepString.equals(CURRENT_DATE)) {
                    ContentValues values = new ContentValues();
                    values.put("number", stepCount);
                    Log.i("ContentValues", " ContentValues");
                    database.update("step", values, "curr_date=?", new String[]{CURRENT_DATE});
                }
            }
        }else {
            //数据库没有的情况下  进行第一次插入
            ContentValues values = new ContentValues();
            values.put("curr_date", CURRENT_DATE);
            values.put("number", CURRENT_STEP);
            database.insert("step", null, values);
            Log.i("curr_date", " curr_date");
        }
    }
    private void initNotification() {
        builder.setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("今日步数" + CURRENT_STEP + " 步")
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(true)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setSmallIcon(R.mipmap.dimension_league_icon);
        if (Build.VERSION.SDK_INT>=26){
            @SuppressLint("WrongConstant") NotificationChannel channel = new NotificationChannel("1","没事",NotificationManager.IMPORTANCE_MAX);
            service.createNotificationChannel(channel);
            builder.setChannelId("1");
        }

        if (Build.VERSION.SDK_INT>=16){
            service.notify(100,builder.build());
        }
        if (mCallback!=null){
            mCallback.updateUi(CURRENT_STEP);
        }
    }

    /**
     * 监听晚上0点变化初始化数据
     */
    private void isNewDay() {
        String time = "00:00";
        if (time.equals(new SimpleDateFormat("HH:mm").format(new Date())) || !CURRENT_DATE.equals(  DateFormat.format("dd-MM", System.currentTimeMillis())+"")) {
            initTodayData();
        }
    }
    // 获取当前步数
    public int getStepCount() {
        Log.i("getStepCount", " getStepCount"+stepCount);
        return stepCount;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new StepBinder();
    }
    //系统干掉这个服务后  可以让他自己重新启动
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
    //接口回调
    private UpdateUiCallBack mCallback;
    public void registerCallback(UpdateUiCallBack paramICallback) {
        this.mCallback = paramICallback;
    }

    //步数更新回调
    public interface UpdateUiCallBack{
        void updateUi(int stepCount);
    }

     //服务销毁的注销广播and计步管理监听
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBatInfoReceiver);
        sensorManager.unregisterListener(this);
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

            time.cancel();
            save();
            startTimeCount();
        }
    }
}
