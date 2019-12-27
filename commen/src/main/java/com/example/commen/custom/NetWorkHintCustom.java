package com.example.commen.custom;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.commen.R;

public class NetWorkHintCustom extends LinearLayout implements ShowHideView, View.OnClickListener {

    private Context context;
    private TextView netWorkHint;

    public NetWorkHintCustom(Context context) {
        this(context, null);
    }

    public NetWorkHintCustom(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NetWorkHintCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_network_hint, this);
        netWorkHint = findViewById(R.id.tv_net_work_hint);
        netWorkHint.setOnClickListener(this);
    }

    @Override
    public void showView() {
        netWorkHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        netWorkHint.setVisibility(View.GONE);
    }

    /**
     * 点击跳转到设置界面
     * @param view
     */
    @Override
    public void onClick(View view) {
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
            //跳转wifi网络界面
            //            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS); //跳转设置界面
        } else {
            intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
        }
        context.startActivity(intent);
    }

}
