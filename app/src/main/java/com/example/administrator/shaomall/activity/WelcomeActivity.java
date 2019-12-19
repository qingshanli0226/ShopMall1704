package com.example.administrator.shaomall.activity;

import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;

import com.example.administrator.shaomall.app.ShaoHuaApplication;
import com.example.administrator.shaomall.cache.CacheManager;
import com.example.administrator.shaomall.R;
import com.shaomall.framework.bean.HomeBean;
import com.shaomall.framework.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity implements CacheManager.IHomeReceivedListener {
    private android.widget.RelativeLayout mWelcomeBackground;
    private android.widget.ImageView mIvWelcomeIcon;
    private android.widget.TextView mTvWelcomeVersion;
    private TextView timeTv;
    private volatile boolean isData = false;
    private int count = 0;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
        CacheManager.getInstance().init(ShaoHuaApplication.context);
        timeTv = findViewById(R.id.welcome_time_tv);
        mWelcomeBackground = findViewById(R.id.welcome_background);
        mIvWelcomeIcon = findViewById(R.id.iv_welcome_icon);
        mTvWelcomeVersion = findViewById(R.id.tv_welcome_version);
        //0完全透明到1完全不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1f);
        //动画时间
        alphaAnimation.setDuration(3000);
        mWelcomeBackground.startAnimation(alphaAnimation);
    }

    @Override
    protected void initData() {
        CacheManager.getInstance().registerListener(this);

        timeTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isData = true;
            }
        });
        TimeThread();
    }

    @Override
    public void flagFullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private int time = 4;

    private void TimeThread() {
        //使用时间戳控制跳转主页面并销毁当前页面
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                time--;
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        if (time <= 0) {
                            timeTv.setText("跳过");
                            timeTv.setClickable(true);
                        }else
                        timeTv.setText("" + time);
                    }
                });
                if (count >= 4 && isData) {
                    toClass(MainActivity.class);
                    finish();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CacheManager.getInstance().unregisterListener(this);
    }

    @Override
    public void onHomeDataReceived(HomeBean.ResultBean homeBean) {
        isData = true;

    }
}
    