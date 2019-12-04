package com.example.dimensionleague.activity;

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
import android.util.TimeUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.dimensionleague.R;
import com.example.dimensionleague.database.StepData;
import com.example.dimensionleague.service.StepService;
import com.example.dimensionleague.view.StepView;
import com.example.framework.base.BaseActivity;
import com.gyf.immersionbar.ImmersionBar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StepActivity extends BaseActivity implements SensorEventListener {
    private TextView tv_isSupport;
    private StepView stepView;
    private TextView time;
    private SensorManager sensorManager;
    private Sensor countSensor;
    private  int stepInt;
    private   StepService stepService;//计步服务
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            while (i<=v){
                handler.sendEmptyMessage(1);
                try {
                    Thread.sleep(1);
                    i++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (i>=stepInt){
                    //如果大于设置的锻炼步数就退出
                   break;
                }
            }
        }
    };
    private Thread thread=new Thread();
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
            }
            //计步弧度的走动
            else if (msg.what==1){
                SharedPreferences step = getSharedPreferences("Step", MODE_PRIVATE);
                 stepInt = step.getInt("step", 3000);
                stepView.setCurrentCount(stepInt,i);
            }


        }
    };
    private ImageView iv_left;
    private TextView physical;
    private TextView tv_set;
    private TextView tv_data;
    private int i=0;//循环的初始值
    private  int v;//获取当前步数
    public  ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
             stepService = ((StepService.StepBinder) iBinder).getService();
            Toast.makeText(StepActivity.this, "草", Toast.LENGTH_SHORT).show();
            SharedPreferences step = getSharedPreferences("Step", MODE_PRIVATE);
            int stepInt = step.getInt("step", 3000);
            stepView.setCurrentCount(stepInt,stepService.getStepCount());
            Log.i("ComponentName", "ComponentName: ");
            stepService.registerCallback(new StepService.UpdateUiCallBack() {
                @Override
                public void updateUi(int stepCount) {
                    SharedPreferences step = getSharedPreferences("Step", MODE_PRIVATE);
                    int stepInt = step.getInt("step", 3000);
                    stepView.setCurrentCount(stepInt,stepCount);
                }
            });

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };
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

            stepView.setCurrentCount(1000, 0);
            sensorManager.registerListener(this, countSensor, 10000);
            tv_isSupport.setText("计步中...");

        } else {
            Toast.makeText(this, "" + isSupportStepCountSensor(this), Toast.LENGTH_SHORT).show();
            tv_isSupport.setText("该设备不支持计步");
        }
    }

    //开启计步服务
    private void seteupService() {
        Intent intent = new Intent(StepActivity.this, StepService.class);
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    @Override
    public int getLayoutId() {

        return R.layout.step_activity;
    }

    /**
     * 判断该设备是否支持计歩
     *
     * @return
     */
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
        seteupService();
        init();
        initDate();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
       v= (int) sensorEvent.values[0];
        new Thread(runnable).start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

}
