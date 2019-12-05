package com.example.shopmall.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.framework.base.BaseActivity;
import com.example.framework.manager.ConnectManager;

import com.example.shopmall.R;

public class WelcomeActivity extends BaseActivity {

    ImageView iv_welcome;

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        iv_welcome = findViewById(R.id.iv_welcome);
    }

    @Override
    public void initData() {
        boolean connectStatus = ConnectManager.getInstance().getConnectStatus();
        if (connectStatus) {
            Toast.makeText(this, "有网络连接", Toast.LENGTH_SHORT).show();

            ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_welcome,"Alpha",0,1);
            objectAnimator.setDuration(3000);
            objectAnimator.start();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                }
            },3000);
        } else {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }
    }
}