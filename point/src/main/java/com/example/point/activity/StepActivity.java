package com.example.point.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.framework.base.BaseActivity;
import com.example.point.R;
import com.example.point.StepIsSupport;
import com.example.point.stepmanager.StepPointManager;
import com.example.point.view.StepView;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;

public class StepActivity extends BaseActivity   {
    private TextView tv_isSupport;
    private StepView stepView;
    private TextView time;
    private int i=0;//循环的初始值
    private  int v=1;//获取当前步数
    private  int stepInt;
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

        if (new StepIsSupport().isSupportStepCountSensor(this)) {
            tv_isSupport.setText("计步中...");
            StepPointManager.getInstance(this).addGetStepListener(new StepPointManager.GetStepListener() {
                @Override
                public void onsetStep(int step) {
                  stepView.setCurrentCount(stepInt,step);
                    Log.i("wzy", "onsetStep: "+stepInt+"   "+step);
                }
            });
        } else {
            tv_isSupport.setText("该设备不支持计步");
        }
    }
    @Override
    public int getLayoutId() {

        return R.layout.step_activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        initDate();
    }

    //调用moveTaskToBack可以让程序退出到后台运行，false表示只对主界面生效，true表示任何界面都可以生效。
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(false);
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }



}
