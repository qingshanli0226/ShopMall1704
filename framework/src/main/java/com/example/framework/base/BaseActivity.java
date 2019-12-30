package com.example.framework.base;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.framework.R;
import com.example.framework.manager.AppActivityManager;
import com.example.framework.manager.CaCheManager;
import com.example.framework.manager.ConnectManager;
import com.example.framework.manager.DisConnectActivity;
import com.example.framework.manager.StepManager;

/**
 * base类
 */
public abstract class BaseActivity extends AppCompatActivity implements ConnectManager.INetConnetListener {

    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); //切换正常主题
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        AppActivityManager.addActivity(this);

        //沉浸式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        ConnectManager.getInstance().registerConnectListener(this);
        //初始化
        initView();
        //数据初始化
        initData();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION_CODE) {
            for (int i = 0; i < permissions.length; i++) {
                Log.i("MainActivity", "申请的权限为：" + permissions[i] + ",申请结果：" + grantResults[i]);
            }
        }
    }

    //设置布局
    protected abstract int setLayout();

    public abstract void initView();

    public abstract void initData();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppActivityManager.removeActivity(this);
        ConnectManager.getInstance().unregisterConnectListener(this);

    }

    @Override
    public void onConnect() {

    }

    @Override
    public void onDisConnect() {
        boolean connectStatus = ConnectManager.getInstance().getConnectStatus();
        if (!connectStatus) {
            Intent intent = new Intent(BaseActivity.this, DisConnectActivity.class);
            startActivity(intent);
        }
    }
}
