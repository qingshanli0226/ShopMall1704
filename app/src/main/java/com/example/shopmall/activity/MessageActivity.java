package com.example.shopmall.activity;

import android.graphics.Color;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.shopmall.R;

public class MessageActivity extends BaseActivity {

    private TitleBar tbMessage;

    @Override
    protected int setLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        tbMessage = findViewById(R.id.tb_message);
    }

    @Override
    public void initData() {
        tbMessage.setBackgroundColor(Color.RED);
        tbMessage.setLeftImg(R.drawable.left);
        tbMessage.setCenterText("消息中心",18,Color.WHITE);

        tbMessage.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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
