package com.example.shopmall.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.framework.base.BaseActivity;
import com.example.framework.base.IGetBaseView;
import com.example.framework.bean.HomepageBean;
import com.example.framework.manager.CaCheManager;
import com.example.framework.manager.ConnectManager;

import com.example.net.Constant;
import com.example.shopmall.MyApplication;
import com.example.shopmall.R;
import com.example.shopmall.presenter.IntegerPresenter;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity{


    @Override
    protected int setLayout() {
        return 0;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }
}