package com.example.shopmall.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.example.base.BaseActivity;
import com.example.base.BasePresenter;
import com.example.base.IBaseView;
import com.example.common.ConnectManager;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.HomepageBean;
import com.example.shopmall.posenter.WelcomePosenter;

public class WelcomeActivity extends BaseActivity implements IBaseView<HomepageBean> {

    WelcomePosenter welcomePosenter;

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {
        Log.d("####", "initData: ");
        welcomePosenter = new WelcomePosenter(Constant.HOME_URL,HomepageBean.class);
        welcomePosenter.attachView(this);
        welcomePosenter.getGetData();
        boolean connectStatus = ConnectManager.getInstance().getConnectStatus();
        if (connectStatus){
            Toast.makeText(this, "有网络连接", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                }
            },3000);
        }else {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
            finish();
        }
    }

    @Override
    public void onGetDataSucess(HomepageBean data) {
//        Toast.makeText(this, "" + data.getMsg(), Toast.LENGTH_SHORT).show();
        Log.d("####", "onGetDataSucess: " + data.getMsg());
    }

    @Override
    public void onPostDataSucess(HomepageBean data) {

    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {
        Log.d("####", "onGetDataSucess: ");
    }

    @Override
    public void onLoadingPage() {

    }

    @Override
    public void onStopLoadingPage() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        welcomePosenter.detachView();
    }
}