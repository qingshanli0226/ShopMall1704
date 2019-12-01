package com.example.shopmall;

import android.graphics.Color;
import android.util.Log;

public class MainActivity extends BaseActivity {

    TitleBar titleBar;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        titleBar = findViewById(R.id.tb_main);
    }

    @Override
    public void initData() {
        titleBar.setCenterText("标题栏", 30, Color.RED);
        titleBar.setLeftText("左边");
        titleBar.setRightText("右边");
        titleBar.setTitleBacKGround(Color.BLUE);

        titleBar.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                Log.e("####", "左边");
            }

            @Override
            public void RightClick() {
                Log.e("####", "右边");
            }

            @Override
            public void CenterClick() {
                Log.e("####", "中间");
            }
        });
    }

}
