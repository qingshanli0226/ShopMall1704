package com.example.point.activity;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.framework.base.BaseActivity;
import com.example.point.R;
import com.example.point.StepPointManager;
import com.example.point.view.StepView;
import com.example.point.service.StepService;

public class StepActivity extends BaseActivity {
    private TextView tv_isSupport;
    private StepView stepView;
    private TextView time;
    private SensorManager sensorManager;
    private Sensor countSensor;
    private int i=0;//循环的初始值
    private  int v=1;//获取当前步数
    private  int stepInt;
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while (true){
                //当前步数小于从服务里面获取的步数
                if (i<v){
                    handler.sendEmptyMessage(1);
                    try {
                Thread.sleep(100);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
                //当前步数大于从服务里面获取的步数退出
                else{
                    break;
                }
                i++;
                //当前步数大于锻炼里面的步数
                if (i>stepInt){
                    //如果大于设置的锻炼步数就退出
                   break;
                }
            }
        }
    };
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //当前时间
            if (msg.what==0){
                boolean b = DateFormat.is24HourFormat(StepActivity.this);

                if (b) {
                    CharSequence charSequence = DateFormat.format("dd-MM HH:mm:ss", System.currentTimeMillis());
                    time.setText("时间" + charSequence);

                } else {
                    CharSequence charSequence = DateFormat.format("dd-MM hh:mm:ss aa", System.currentTimeMillis());
                    time.setText("时间" + charSequence);
                }
                new Thread(runnable).start();
            }
            //计步弧度的走动
            else if (msg.what==1){
                v= StepPointManager.getInstance(StepActivity.this).getStep();
                stepView.setCurrentCount(stepInt,i);
                Log.i("wzy", "run: "+i);
            }


        }
    };
    private ImageView iv_left;
    private TextView physical;
    private TextView tv_set;
    private TextView tv_data;

    @Override
    public void init() {
        iv_left = (ImageView) findViewById(R.id.iv_left);
        physical = (TextView) findViewById(R.id.physical);
        tv_isSupport = findViewById(R.id.tv_isSupport);
        stepView = findViewById(R.id.step);
        time = findViewById(R.id.time);
        tv_set = (TextView) findViewById(R.id.tv_set);

        tv_data = (TextView) findViewById(R.id.tv_data);

        physical.setText("计步页");

        SharedPreferences step = getSharedPreferences("Step", MODE_PRIVATE);
        stepInt = step.getInt("step", 3000);

        //跳转到锻炼页面
        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepActivity.this,PhysicalActivity.class);
                startActivity(intent);

            }
        });

        //跳转到历史记录页面
        tv_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StepActivity.this,HistoryActivity.class);
                startActivity(intent);
            }
        });

    }



    @Override
    public void initDate() {
        //返回计步页面
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
         //时间的显示
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    handler.sendEmptyMessage(0);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();

        if (isSupportStepCountSensor(this)) {
            tv_isSupport.setText("计步中...");
        } else {
            Toast.makeText(this, "" + isSupportStepCountSensor(this), Toast.LENGTH_SHORT).show();
            tv_isSupport.setText("该设备不支持计步");
        }
    }


    @Override
    public int getLayoutId() {

        return R.layout.step_activity;
    }
    //这边只是通过获取传感器实例该设备是否支持计步
    //为了给用户一个视觉提醒
    //具体实现在计步服务里面
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean isSupportStepCountSensor(Context context) {
        // 获取传感器管理器的实例
        sensorManager = (SensorManager) context
                .getSystemService(context.SENSOR_SERVICE);

        countSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        Sensor detectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        return countSensor != null || detectorSensor != null;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initDate();
    }

}
