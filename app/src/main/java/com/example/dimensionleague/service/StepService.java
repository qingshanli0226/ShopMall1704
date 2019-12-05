package com.example.dimensionleague.service;

import android.app.Notification;
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
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.example.dimensionleague.R;
import com.example.dimensionleague.activity.StepActivity;
import com.example.dimensionleague.database.StepData;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StepService extends Service {
    private TimeCount timeCount;
    //通知管理
     private  NotificationManager service;
    //创建我们的通知
    private Notification.Builder builder;
    //计步数据库
    private StepData stepData;
    // 当前的日期
    private static String CURRENT_DATE = "";
    //当前所走的步数
    private int CURRENT_STEP;
    //IBinder对象，向Activity传递数据的桥梁
    private StepBinder stepBinder = new StepBinder();
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
        Log.i("create", "onCreate: ");
        initNotification();
        initTodayData();
        initBroadcastReceiver();
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();
        startTimeCount();
    }

    private void initBroadcastReceiver() {
        final IntentFilter filter = new IntentFilter();
        // 屏幕灭屏广播
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        //关机广播
        filter.addAction(Intent.ACTION_SHUTDOWN);
        // 屏幕亮屏广播
        filter.addAction(Intent.ACTION_SCREEN_ON);
        // 屏幕解锁广播
//        filter.addAction(Intent.ACTION_USER_PRESENT);
        // 当长按电源键弹出“关机”对话或者锁屏时系统会发出这个广播
        // example：有时候会用到系统对话框，权限可能很高，会覆盖在锁屏界面或者“关机”对话框之上，
        // 所以监听这个广播，当收到时就隐藏自己的对话，如点击pad右下角部分弹出的对话框
        filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        //监听日期变化
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        filter.addAction(Intent.ACTION_TIME_CHANGED);
        filter.addAction(Intent.ACTION_TIME_TICK);

        mBatInfoReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(final Context context, final Intent intent) {
                String action = intent.getAction();
                if (Intent.ACTION_SCREEN_ON.equals(action)) {
                    Log.d("screen", "screen on");
                } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
                    Log.d("screen", "screen off");
                    //改为60秒一存储
                    duration = 60000;
                } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
                    Log.d("screen", "screen unlock");
                    //改为30秒一存储
                    duration = 30000;
                } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {
                    Log.i("receive", " receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");
                    //保存一次
                    save();
                } else if (Intent.ACTION_SHUTDOWN.equals(intent.getAction())) {
                    Log.i("receive", " receive ACTION_SHUTDOWN");
                    save();
                } else if (Intent.ACTION_DATE_CHANGED.equals(action)) {//日期变化步数重置为0
                    save();
                    isNewDay();
                } else if (Intent.ACTION_TIME_CHANGED.equals(action)) {
                    //时间变化步数重置为0
                    isCall();
                    save();
                    isNewDay();
                } else if (Intent.ACTION_TIME_TICK.equals(action)) {//日期变化步数重置为0
                    isCall();
//                    Logger.d("重置步数" + StepDcretor.CURRENT_STEP);
                    save();
                    isNewDay();
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
            remindNotify(stepi);
        }

    }
    //锻炼提醒的通知
    private void remindNotify(int stepi) {
        builder=new Notification.Builder(this);
        builder.setContentTitle("今日步数" + CURRENT_STEP + " 步")
                .setContentText("距离目标还差" + (stepi - CURRENT_STEP) + "步，加油！")
                .setTicker(getResources().getString(R.string.app_name) + "提醒您开始锻炼了")//通知首次出现在通知栏，带上升动画效果的
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(true)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setSmallIcon(R.mipmap.logo);

        service = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        service.notify(100,builder.build());
    }

    //初始化当天的步数
    private void initTodayData() {
        CURRENT_DATE = getTodayDate();
        stepData=new StepData(this,"StepData",null,2);
        SQLiteDatabase database = stepData.getWritableDatabase();
        //查询数据库
        Cursor step = database.query("step", null, null, null, null, null, null);
        if (step.moveToNext()){
            while (step.moveToNext()){
                String stepString = step.getString(step.getColumnIndex("curr_date"));
                int number = step.getInt(step.getColumnIndex("number"));
                //判断数据库是否和当天日期相同
                if (stepString.equals(CURRENT_DATE)){
                    CURRENT_STEP=Integer.parseInt(stepString);
                }
            }
        }else {
            CURRENT_STEP = 0;
        }
        initNotification();
    }
    //将今日步数存入数据库
    private void save(){
        SQLiteDatabase database = stepData.getWritableDatabase();
        Cursor step = database.query("step", null, null, null, null, null, null);
        if (step.moveToNext()){
            //如果数据库已经有了今天的日期   我们直接进行更新数据库中的步数即可
            while (step.moveToNext()){
                String stepString = step.getString(step.getColumnIndex(CURRENT_DATE));
                if (!stepString.isEmpty()){
                    ContentValues values = new ContentValues();
                    values.put("number",CURRENT_STEP);
                    database.update("step",values,"curr_date=?",new String[]{CURRENT_DATE});
                }
            }
        }else {
            //数据库没有的情况下  进行第一次插入
            ContentValues values = new ContentValues();
            values.put("curr_date",CURRENT_DATE);
            values.put("number",CURRENT_STEP);
            database.insert("step",null,values);
        }

    }
    //今天日期
    private String getTodayDate() {
        CharSequence charSequence = DateFormat.format("yyyy\tMM-dd", System.currentTimeMillis());
        return charSequence+"";
    }

    private void initNotification() {
        builder=new Notification.Builder(this);
        builder.setContentTitle(getResources().getString(R.string.app_name))
                .setContentText("今日步数" + CURRENT_STEP + " 步")
                .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示
                .setAutoCancel(false)//设置这个标志当用户单击面板就可以让通知将自动取消
                .setOngoing(true)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)
                .setSmallIcon(R.mipmap.logo);

        service = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        service.notify(100,builder.build());
    }

    /**
     * 监听晚上0点变化初始化数据
     */
    private void isNewDay() {
        String time = "00:00";
        if (time.equals(new SimpleDateFormat("HH:mm").format(new Date())) || !CURRENT_DATE.equals(getTodayDate())) {
            initTodayData();
        }
    }
    /**
     * 获取当前步数
     *
     * @return
     */
    public int getStepCount() {
        return CURRENT_STEP;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
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

    private void startTimeCount() {
             if (timeCount==null){
                 timeCount=new TimeCount(duration,1000);
             }
             timeCount.start();
    }

    //保存计步数据
    class TimeCount extends CountDownTimer{

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            // 如果计时器正常结束，则开始计步
            timeCount.cancel();
            save();
            startTimeCount();
        }

        @Override
        public void onFinish() {

        }
    }
}
