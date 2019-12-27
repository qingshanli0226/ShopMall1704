package com.example.buy.activity;


import android.graphics.Color;

import com.example.buy.R;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.manager.ShoppingManager;

public class ShoppingResultActivity extends BaseActivity {
    private TitleBar tbPayResult;

    @Override
    protected int setLayout() {
        return R.layout.activity_shopping_result;
    }

    @Override
    public void initView() {
        tbPayResult = findViewById(R.id.tb_buy_payresult);

        initTitleBar();
    }

    private void initTitleBar() {
        tbPayResult.setCenterText("购物", 20, Color.WHITE);
        tbPayResult.setBackgroundColor(Color.RED);
        tbPayResult.setRightText("返回", 13, Color.WHITE);

        tbPayResult.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {
                ShoppingManager.getInstance().setMainitem(0);
                finish();
            }

            @Override
            public void CenterClick() {

            }
        });
    }

    @Override
    public void initData() {

    }
}
