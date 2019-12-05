package com.example.shopmall.activity;

import android.graphics.Color;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.shopmall.R;

public class RegisterActivity extends BaseActivity {

    TitleBar tb_register;

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        tb_register = findViewById(R.id.tb_register);
    }

    @Override
    public void initData() {

        tb_register.setBackgroundColor(Color.RED);
        tb_register.setLeftImg(R.drawable.left);
        tb_register.setCenterText("注册",18, Color.WHITE);

        tb_register.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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
