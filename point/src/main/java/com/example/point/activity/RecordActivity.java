package com.example.point.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framework.base.BaseActivity;
import com.example.point.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tyrantgit.widget.HeartLayout;

public class RecordActivity extends BaseActivity {

    private ImageView iv_left;
    private TextView physical;
    private ImageView iv_right;
    private LinearLayout layout_titlebar;
    private TextView record_tv;
    private HeartLayout heart_layout;
    private Random mRandom = new Random();
    private Timer mTimer = new Timer();

    @Override
    public int getLayoutId() {
        return R.layout.record_activity;
    }

    @Override
    public void init() {
        iv_left = findViewById(R.id.iv_left);
        physical = findViewById(R.id.physical);
        iv_right = findViewById(R.id.iv_right);
        layout_titlebar = findViewById(R.id.layout_titlebar);
        record_tv = findViewById(R.id.record_tv);
        heart_layout=findViewById(R.id.heart_layout);
        layout_titlebar.setBackgroundColor(Color.rgb(247, 195, 93));
        physical.setText("记录详情");

        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Intent intent = getIntent();
        String curr_date = intent.getStringExtra("curr_date");
        int step = intent.getIntExtra("step", 0);
        record_tv.setText("\t\t\t\t今天是" + curr_date + ",您今天一共\n走了" + step + "步。\n" + "\t\t\t\t这天获取了" + (step / 100) + "积分,消耗\n了" +
                (step / 70) + "卡路里,继续努力!");
        // 下划线
        record_tv.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        record_tv.getPaint().setAntiAlias(true);

        mTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                heart_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        heart_layout.addHeart(randomColor());
                    }
                });
            }
        }, 500, 200);
    }

    @Override
    public void initDate() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTimer.cancel();
    }

    private int randomColor() {
        return Color.rgb(mRandom.nextInt(255), mRandom.nextInt(255), mRandom.nextInt(255));
    }
}
