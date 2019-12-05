package com.example.shopmall.activity;

import android.graphics.Color;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.shopmall.R;

public class IntegralActivity extends BaseActivity {

    TitleBar tb_integral;

    @Override
    protected int setLayout() {
        return R.layout.activity_integral;
    }

    @Override
    public void initView() {
        tb_integral = findViewById(R.id.tb_integral);
    }

    @Override
    public void initData() {
        tb_integral.setBackgroundColor(Color.RED);
        tb_integral.setCenterText("我的积分",18, Color.WHITE);
        tb_integral.setLeftImg(R.drawable.left);

        tb_integral.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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
