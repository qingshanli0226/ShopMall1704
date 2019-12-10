package com.example.point.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.framework.base.BaseActivity;
import com.example.point.R;

public class IntegralActivity extends BaseActivity {
    private ImageView iv_left;
    private TextView physical;
    private ImageView iv_right;
    private LinearLayout layout_titlebar;

    @Override
    public int getLayoutId() {
        return R.layout.integral_activity;
    }

    @Override
    public void init() {
        iv_left=findViewById(R.id.iv_left);
    }

    @Override
    public void initDate() {

    }

}
