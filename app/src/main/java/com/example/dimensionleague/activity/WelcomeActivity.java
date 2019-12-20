package com.example.dimensionleague.activity;


import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;
import com.example.common.User;
import com.example.framework.manager.AccountManager;
import com.example.dimensionleague.AutoLoginManager;
import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.common.HomeBean;
import com.example.dimensionleague.userbean.AutoLoginBean;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.port.ITaskFinishListener;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.umeng.analytics.MobclickAgent;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

public class WelcomeActivity extends BaseNetConnectActivity implements ITaskFinishListener {
    private final Handler handler =new MyHandler(this);

    private VideoView videoView;
    private Button but;
    private int count = 3;

    private boolean isCarouselFinish = false;
    private boolean isRequestHomeBean = false;
    private boolean isRequestAutoLogin = false;
    private boolean isNotNet = false;

    @SuppressLint("CheckResult")
    @Override
    public void init() {
        super.init();
        //初始化需要的权限
        final RxPermissions rxPermissions = new RxPermissions(this);
        //noinspection ResultOfMethodCallIgnored
        rxPermissions.request(
                Manifest.permission.INTERNET,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                       .subscribe(permission -> {
                           // 成功
                           // 失败
                       });

        videoView = findViewById(R.id.videoView);
        but = findViewById(R.id.welcome_button);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
         //设置监听
        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(0,0);
            mp.setLooping(true);
            mp.start();
        });

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void initDate() {

        videoView.setVideoPath(Uri.parse("android.resource://" + getPackageName() + "/"+R.raw.peng).toString());

        handler.sendEmptyMessage(1);
        but.setText(count +getString(R.string.second));
        CacheManager.getInstance().getHomeDate();
        AutoLoginManager.getInstance().getLoginData();
        CacheManager.getInstance().registerGetDateListener(new CacheManager.IHomeReceivedListener() {
            @Override
            public void onHomeDataReceived(HomeBean.ResultBean homeBean) {
                if (homeBean != null) {
                    isRequestHomeBean = true;
                    onFinish();
                }
            }

            @Override
            public void onHomeDataError(String s) {
                isRequestHomeBean = false;
                isNotNet = true;
                onFinish();

            }
        });

        AutoLoginManager.getInstance().registerAutoLoginListener(new AutoLoginManager.IAutoLoginReceivedListener() {
            @Override
            public void onAutoLoginReceived(AutoLoginBean.ResultBean resultBean) {
                if (resultBean != null) {
                    //TODO 保存用户信息
                    AccountManager.getInstance().setUser(new User(
                            resultBean.getName(),
                            resultBean.getPassword(),
                            resultBean.getEmail(),
                            resultBean.getPhone(),
                            resultBean.getPoint(),
                            resultBean.getAddress(),
                            resultBean.getMoney(),
                            resultBean.getAvatar()
                    ));
                    //TODO 更新登录状态
                    AccountManager.getInstance().notifyLogin();
                    AccountManager.getInstance().saveToken(resultBean.getToken());
                    isRequestAutoLogin = true;
                    onFinish();
                }
            }

            @Override
            public void onAutoDataError(String s) {
                isRequestAutoLogin = false;
                onFinish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheManager.getInstance().unRegisterGetDateListener();
        AutoLoginManager.getInstance().unRegisterAutoLoginListener();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public void onFinish() {
        if (isCarouselFinish && isRequestHomeBean) {
            //跳转到主页面
            Bundle bundle = new Bundle();
            bundle.putBoolean(getString(R.string.test_auto_login),isRequestAutoLogin);
            startActivity(MainActivity.class,bundle);
            finish();
        }else if(isCarouselFinish && isNotNet){
            //跳转到主页面
            Bundle bundle = new Bundle();
            bundle.putBoolean(getString(R.string.test_auto_login),isRequestAutoLogin);
            startActivity(MainActivity.class,bundle);
            finish();
        }
    }
    @Override
    protected void onPause() {
        MobclickAgent.onPause(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        MobclickAgent.onResume(this);
        super.onResume();
    }
    private static class MyHandler extends Handler {
        private final WeakReference<WelcomeActivity> mWeakReference;
        private final Context mContext;

        MyHandler(WelcomeActivity activity) {
            mWeakReference = new WeakReference<>(activity);
            mContext=activity;
        }

        @Override
        public void handleMessage(@NotNull Message msg) {
            super.handleMessage(msg);
            WelcomeActivity activity = mWeakReference.get();
            if(msg.what==1){
                if (activity.count >0) {
                    activity.but.setText(activity.count + mContext.getString(R.string.second));
                    activity.count--;
                    sendEmptyMessageDelayed(1,1000);
                } else {
                    activity.isCarouselFinish = true;
                    activity.onFinish();
                }
            }
        }
    }
}

