package com.example.administrator.shaomall.activity;

import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import com.example.administrator.shaomall.cache.CacheManager;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.home.HomeBean;
import com.shaomall.framework.base.BaseActivity;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {
    private android.widget.RelativeLayout mWelcomeBackground;
    private android.widget.ImageView mIvWelcomeIcon;
    private android.widget.TextView mTvWelcomeVersion;
    private volatile boolean isData = false;
    private int count = 0;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {

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
        CacheManager.getInstance().registerListener(new CacheManager.IHomeReceivedListener() {
            @Override
            public void onHomeDataReceived(HomeBean.ResultBean homeBean) {
                isData=true;
//                toClass(MainActivity.class);
            }
        });
        TimeThread();


//        if (CacheManager.getInstance().getHomeBean()!=null){
//            isData=true;
//        }
    }



    @Override
    public void flagFullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void TimeThread() {
        //使用时间戳控制跳转主页面并销毁当前页面
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                if (count >= 4 && isData) {
                    toClass(MainActivity.class);
                    finish();
                    timer.cancel();
                }
            }
        }, 0, 1000);
    }


}
