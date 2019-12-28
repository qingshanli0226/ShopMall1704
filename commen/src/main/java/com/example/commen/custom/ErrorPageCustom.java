package com.example.commen.custom;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.commen.R;

public class ErrorPageCustom extends LinearLayout implements ShowHideView, View.OnClickListener {
    private Context context;
    private LinearLayout errorPage;
    private Button btErrorPage;

    public ErrorPageCustom(Context context) {
        this(context, null);
    }

    public ErrorPageCustom(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ErrorPageCustom(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        LayoutInflater.from(context).inflate(R.layout.item_error_page, this);
        errorPage = this.findViewById(R.id.ll_error_page);
        btErrorPage = this.findViewById(R.id.bt_error_page);
        btErrorPage.setOnClickListener(this);
    }

    @Override
    public void showView() {
        errorPage.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideView() {
        errorPage.setVisibility(View.GONE);
    }

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
