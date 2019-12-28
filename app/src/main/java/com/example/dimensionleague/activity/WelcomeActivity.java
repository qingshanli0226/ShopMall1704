package com.example.dimensionleague.activity;


import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.example.common.User;
import com.example.framework.manager.AccountManager;
import com.example.dimensionleague.AutoLoginManager;
import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.common.HomeBean;
import com.example.dimensionleague.userbean.AutoLoginBean;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.manager.NetConnectManager;
import com.example.framework.port.ITaskFinishListener;
import com.example.point.StepIsSupport;
import com.example.point.stepmanager.StepPointManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import org.jetbrains.annotations.NotNull;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;

public class WelcomeActivity extends BaseNetConnectActivity implements ITaskFinishListener {
    private final Handler handler = new MyHandler(this);

    private VideoView videoView;
    private ImageView welcomeImage;
    private Button but;
    private int count = 3;

    private boolean isCarouselFinish = false;
    private boolean isRequestHomeBean = false;
    private boolean isRequestAutoLogin = false;
    private boolean isNotNet = false;

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
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.VIBRATE,
                Manifest.permission.GET_TASKS,
                Manifest.permission.CHANGE_NETWORK_STATE,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.CHANGE_WIFI_STATE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.SYSTEM_ALERT_WINDOW,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                .subscribe(permission -> {
                    if (permission) {
                        //TODO 成功

                    } else {//TODO 失败

                    }
                });
        rxPermissions.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE).subscribe(permission -> {
            CacheManager.getInstance().setSdPermission(permission);
        });

        welcomeImage = findViewById(R.id.welcomeImage);
        but = findViewById(R.id.welcome_button);
        if (videoView == null) {
            videoView = new VideoView(this);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            videoView.setLayoutParams(layoutParams);
        }
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void initDate() {
        but.setVisibility(View.GONE);
        //动态添加videoView
        ViewGroup welLayout = findViewById(R.id.welLayout);
        welLayout.removeAllViews();
        welLayout.addView(videoView);
        welLayout.addView(welcomeImage);
        welLayout.addView(but);

        videoView.setVideoPath(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.peng).toString());
        //设置监听
        videoView.setOnPreparedListener(mp -> {
            mp.setVolume(0, 0);
            mp.setLooping(true);
            mp.start();
        });
        handler.sendEmptyMessageDelayed(1, 800);
        but.setText(count + getString(R.string.second));

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
                    User user = new User(
                            resultBean.getName(),
                            resultBean.getPassword(),
                            resultBean.getEmail(),
                            resultBean.getPhone(),
                            resultBean.getPoint(),
                            resultBean.getAddress(),
                            resultBean.getMoney(),
                            resultBean.getAvatar());
                    AccountManager.getInstance().setUser(user);
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
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }

    @Override
    protected void onDestroy() {
        CacheManager.getInstance().unRegisterGetDateListener();
        AutoLoginManager.getInstance().unRegisterAutoLoginListener();
        if (videoView != null) {
            videoView.clearAnimation();
            videoView.clearFocus();
            videoView.suspend();
            videoView.setOnErrorListener(null);
            videoView.setOnPreparedListener(null);
            videoView.setOnCompletionListener(null);
            ViewGroup welcomeLayout = findViewById(R.id.welLayout);
            videoView = null;
            welcomeLayout.removeAllViews();
            welcomeLayout = null;
        }
        handler.removeCallbacksAndMessages(null);
        super.onDestroy();
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
            bundle.putBoolean(getString(R.string.test_auto_login), isRequestAutoLogin);
            startActivity(MainActivity.class, bundle);
            finish();
        } else if (isCarouselFinish && isNotNet) {
            //跳转到主页面
            Bundle bundle = new Bundle();
            bundle.putBoolean(getString(R.string.test_auto_login), isRequestAutoLogin);
            startActivity(MainActivity.class, bundle);
            finish();
        }
    }

    private static class MyHandler extends Handler {
        private final WeakReference<WelcomeActivity> mWeakReference;
        private final Context mContext;

        MyHandler(WelcomeActivity activity) {
            mWeakReference = new WeakReference<>(activity);
            mContext = activity;
        }

        @Override
        public void handleMessage(@NotNull Message msg) {
            super.handleMessage(msg);
            WelcomeActivity activity = mWeakReference.get();
            if (msg.what == 1) {
                activity.but.setText(activity.count + mContext.getString(R.string.second));
                if (activity.count == 3) {
                    activity.welcomeImage.setVisibility(View.GONE);
                    activity.but.setVisibility(View.VISIBLE);
                }
                if (activity.count > 1) {
                    activity.count--;
                    sendEmptyMessageDelayed(1, 1000);
                } else {
                    activity.isCarouselFinish = true;
                    activity.onFinish();
                }
            }
        }
    }
}

