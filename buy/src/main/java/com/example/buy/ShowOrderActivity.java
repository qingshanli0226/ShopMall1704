package com.example.buy;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.framework.base.BaseActivity;

public class ShowOrderActivity extends BaseActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_showorder;
    }

    @Override
    public void init() {
        //http://49.233.93.155:8080  updateMoney  money=1333
        //获取传递过来的数据,然后进行订单类型的显示
    }

    @Override
    public void initDate() {

    }
}
