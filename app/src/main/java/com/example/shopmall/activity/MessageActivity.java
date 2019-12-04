package com.example.shopmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.base.BaseActivity;
import com.example.common.TitleBar;
import com.example.shopmall.R;

public class MessageActivity extends BaseActivity {

    TitleBar tb_message;

    @Override
    protected int setLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        tb_message = findViewById(R.id.tb_message);
    }

    @Override
    public void initData() {
        tb_message.setBackgroundColor(Color.RED);
        tb_message.setLeftImg(R.drawable.left);
        tb_message.setCenterText("消息中心",18,Color.WHITE);

        tb_message.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                finish();
            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });

    }
}
