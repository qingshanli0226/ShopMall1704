package com.example.administrator.shaomall.activity;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.TextView;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;

import com.example.administrator.shaomall.cache.CacheManager;
import com.example.administrator.shaomall.R;
import com.example.commen.network.NetType;
import com.shaomall.framework.bean.HomeBean;
import com.shaomall.framework.base.BaseActivity;
import com.shaomall.framework.manager.ActivityInstanceManager;
import com.shaomall.framework.manager.UserInfoManager;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity implements CacheManager.IHomeReceivedListener, View.OnClickListener {
    private android.widget.RelativeLayout mWelcomeBackground;
    private TextView timeTv;
    String[] permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    private Handler workHandler;
    private Timer timer;
    private HandlerThread welcomeHandlerThread;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
        CacheManager.getInstance().registerListener(this);

        timeTv = findViewById(R.id.welcome_time_tv);
        mWelcomeBackground = findViewById(R.id.welcome_background);

        timeTv.setOnClickListener(this);

        TimeThread(); //倒计时
        CacheManager.getInstance().getData(); //请求数据

        //动态申请权限的结果
        checkPermisson();
        //0完全透明到1完全不透明
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.2f, 1f);
        //动画时间
        alphaAnimation.setDuration(3000);
        mWelcomeBackground.startAnimation(alphaAnimation);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {
        welcomeHandlerThread = new HandlerThread("WelcomeHandlerThread");
        welcomeHandlerThread.start();

        //创建工作线程
        workHandler = new Handler(welcomeHandlerThread.getLooper()) {
            private boolean isNet = false;
            private boolean isData = false;
            private boolean isTimer = false;

            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                int what = msg.what;

                if (what == 101) {//网络获取到数据
                    isData = true;

                } else if (what == 102) { //倒计时
                    isTimer = true;

                } else if (what == 103) { //点击跳过
                    toClass(MainActivity.class);
                    ActivityInstanceManager.removeActivity(WelcomeActivity.this);//销毁欢迎页面

                } else if (what == 104) { //网络出现异常
                    isNet = true;
                    toast("网络连接异常, 请检查", false);

                }

                if ((isData && isTimer) || (isTimer && isNet)) {
                    Log.i("TAG", "handleMessage: isTimer: " + isTimer + "  isData: " + isData + " isNet: " + isNet);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            workHandler.removeCallbacksAndMessages(null);
                            UserInfoManager.getInstance().autoLogin();
                            toClass(MainActivity.class);
                            ActivityInstanceManager.removeActivity(WelcomeActivity.this);//销毁欢迎页面
                        }
                    });
                }
            }
        };
    }

    @Override
    public void onConnected(NetType type) {
        super.onConnected(type);
        CacheManager.getInstance().getData(); //请求数据
    }

    @Override
    public void onDisConnected() {
        workHandler.sendEmptyMessage(104);
    }

    /**
     * 数据加载结果
     *
     * @param homeBean
     */
    @Override
    public void onHomeDataReceived(HomeBean.ResultBean homeBean) {
        workHandler.sendEmptyMessage(101);
        CacheManager.getInstance().unregisterListener(this);
    }

    private int time = 3;

    private void TimeThread() {
        //使用时间戳控制跳转主页面并销毁当前页面
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                time--;
                runOnUiThread(new TimerTask() {
                    @Override
                    public void run() {
                        if (time <= 0) {
                            timeTv.setText("跳过");
                            workHandler.sendEmptyMessage(102);
                            timer.cancel();
                        } else {
                            String s = "" + time;
                            timeTv.setText(s);
                        }
                    }
                });
            }
        }, 0, 1000);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermisson() {
        boolean flag = true;//默认全部被申请过
        for (int i = 0; i < permissions.length; i++) {//只要有一个没有申请成功
            if (!(ActivityCompat.checkSelfPermission(this, permissions[i]) == PackageManager.PERMISSION_GRANTED)) {
                flag = false;
            }
        }
        if (!flag) {
            //动态申请权限
            requestPermissions(permissions, 100);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            boolean flag = true;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    flag = false;
                }
            }
            if (flag) {
                toast("ok", false);
            } else {
                toast("error", false);
            }
        }
    }

    @Override
    public void flagFullScreen() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 点击事件监听
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.welcome_time_tv) { //跳过按钮
            workHandler.sendEmptyMessage(103);
        }
    }

    @Override
    protected void onDestroy() {
        welcomeHandlerThread.quit();
        workHandler.removeCallbacksAndMessages(null);
        super.onDestroy();
    }
}
    