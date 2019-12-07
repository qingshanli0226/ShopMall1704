package com.example.shopmall.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.shopmall.R;

public class GoodsListActivity extends BaseActivity {

    TitleBar tb_goods_list;

    @Override
    protected int setLayout() {
        return R.layout.activity_goods_list;
    }

    @Override
    public void initView() {
        tb_goods_list = findViewById(R.id.tb_goods_list);
    }

    @Override
    public void initData() {
        tb_goods_list.setTitleBacKGround(Color.RED);
        tb_goods_list.setLeftImg(R.drawable.left);
        tb_goods_list.setCenterText("");
    }
}
