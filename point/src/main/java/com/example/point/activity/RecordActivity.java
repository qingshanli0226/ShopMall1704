package com.example.point.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import android.widget.TextView;

import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseActivity;
import com.example.point.R;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import tyrantgit.widget.HeartLayout;

public class RecordActivity extends BaseActivity {

    private TextView record_tv;
    private HeartLayout heart_layout;
    private Random mRandom = new Random();
    private Timer mTimer = new Timer();
    private MyToolBar record_tool;

    @Override
    public int getLayoutId() {
        return R.layout.record_activity;
    }

    @Override
    public void init() {
        record_tool = (MyToolBar) findViewById(R.id.record_tool);
        record_tool.init(Constant.OTHER_STYLE);
        record_tool.getOther_title().setText("计步详情");
        record_tool.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        //返回详情页
        record_tool.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        record_tv = findViewById(R.id.record_tv);
        heart_layout = findViewById(R.id.heart_layout);


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
