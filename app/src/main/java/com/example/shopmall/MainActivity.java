package com.example.shopmall;

import android.graphics.Color;
import android.util.Log;

import com.example.base.BaseActivity;
import com.example.common.LoadingPage;
import com.example.common.TitleBar;

public class MainActivity extends BaseActivity {

    TitleBar titleBar;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }
    LoadingPage loadingPage;
    @Override
    public void initView() {
        loadingPage = findViewById(R.id.loading);
    }



    @Override
    public void initData() {
        super.initData();
        loadingPage.start(LoadingPage.LOADING_FAILURE);
    }

}
