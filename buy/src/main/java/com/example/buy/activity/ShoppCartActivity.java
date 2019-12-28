package com.example.buy.activity;

import android.view.LayoutInflater;
import android.view.View;

import com.example.buy.R;
import com.example.buy.ShopCartFragment;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;

public class ShoppCartActivity extends BaseNetConnectActivity {
    ShopCartFragment fragment = new ShopCartFragment(0);
    @Override
    public void init() {
        super.init();
        getSupportFragmentManager().beginTransaction().add(R.id.shopFragment, fragment).commit();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_shoppcart;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        fragment=null;
        finishActivity();
    }
}
