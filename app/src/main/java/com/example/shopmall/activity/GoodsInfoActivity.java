package com.example.shopmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.shopmall.R;

public class GoodsInfoActivity extends BaseActivity {

    TitleBar tb_goods_info;

    @Override
    protected int setLayout() {
        return R.layout.activity_goods_info;
    }

    @Override
    public void initView() {
        tb_goods_info = findViewById(R.id.tb_goods_info);
    }

    @Override
    public void initData() {

    }
}
