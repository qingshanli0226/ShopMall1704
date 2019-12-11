package com.example.buy.activity;

import com.example.buy.R;
import com.example.buy.ShopCartFragment;
import com.example.framework.base.BaseNetConnectActivity;

public class ShoppCartActivity extends BaseNetConnectActivity {
    ShopCartFragment fragment =new ShopCartFragment();
    @Override
    public void init() {
        super.init();
        getSupportFragmentManager().beginTransaction().add(R.id.shopFragment,fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shoppcart;
    }
}
