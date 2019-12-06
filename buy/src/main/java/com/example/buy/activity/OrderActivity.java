package com.example.buy.activity;

import android.graphics.Color;

import com.example.buy.R;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;

public class OrderActivity extends BaseActivity {

    TitleBar tb_order;

    @Override
    protected int setLayout() {
        return R.layout.activity_order;
    }

    @Override
    public void initView() {
        tb_order = findViewById(R.id.tb_order);
    }

    @Override
    public void initData() {
        initTitlebar();
    }

    private void initTitlebar() {
        tb_order.setCenterText("支付订单", 20, Color.RED);
        tb_order.setRightText("取消", 13, Color.BLACK);
        tb_order.setBackgroundColor(Color.WHITE);

        tb_order.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {
                finish();
            }

            @Override
            public void CenterClick() {

            }
        });
    }
}
