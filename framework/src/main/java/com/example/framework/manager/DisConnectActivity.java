package com.example.framework.manager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.framework.R;
import com.example.framework.base.BaseActivity;

public class DisConnectActivity extends BaseActivity {


    @Override
    protected int setLayout() {
        return R.layout.activity_dis_connect;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {
        ConnectManager.getInstance().registerConnectListener(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.mipmap.ic_launcher);
        builder.setTitle("网络连接不稳定...");
        builder.setMessage(" 请检查网络后重试");
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    @Override
    public void onConnect() {
        super.onConnect();
        Intent intent = StepManager.getInstance().getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onDisConnect() {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ConnectManager.getInstance().unregisterConnectListener(this);
    }
}
