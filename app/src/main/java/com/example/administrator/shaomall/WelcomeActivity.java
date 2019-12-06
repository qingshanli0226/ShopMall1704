package com.example.administrator.shaomall;

import android.util.Log;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import com.example.administrator.shaomall.home.HomeBean;
import com.example.administrator.shaomall.home.HomePresenter;
import com.example.commen.ACache;
import com.example.net.AppNetConfig;
import com.shaomall.framework.base.BaseMVPActivity;
import com.shaomall.framework.base.presenter.IBasePresenter;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseMVPActivity<HomeBean.ResultBean> {
    private android.widget.RelativeLayout mWelcomeBackground;
    private android.widget.ImageView mIvWelcomeIcon;
    private android.widget.TextView mTvWelcomeVersion;
    private boolean isData = false;
    private int count = 0;
    private ACache aCache;
    private IBasePresenter<HomeBean.ResultBean> iHomePresenter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    protected void initView() {
//        aCache = ACache.get(this);
////        iHomePresenter = new HomePresenter();
//        iHomePresenter.attachView(this);
//        iHomePresenter.doGetHttpRequest(AppNetConfig.HOME_DATA_CODE);

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
        TimeThread();
    }

    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, HomeBean.ResultBean data) {
        super.onRequestHttpDataSuccess(requestCode, message, data);
        if (requestCode == AppNetConfig.HOME_DATA_CODE)
            if (data != null) {
                aCache.put(AppNetConfig.KEY_HOME_DATA, data);
                isData = true;
            }
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
