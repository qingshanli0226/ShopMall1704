package com.example.shopmall.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;
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

public class WelcomeActivity extends BaseActivity implements IGetBaseView<HomepageBean> {

    public ImageView iv_welcome;
    public int i = 0;
    public Timer timer;

//    private HandlerThread handlerThread = new HandlerThread("sync");
//    private Handler syncHandler;
//
//    private HandlerThread handlerThreadInt = new HandlerThread("littleInt");
//    private Handler littleInt;
//
//    private HandlerThread handlerThreadCache = new HandlerThread("littleCache");
//    private Handler littleCache;
//
//    private BigTask bigTaskInt = new BigTask();
//    private BigTask bigTaskCache = new BigTask();

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 100){
                i++;
                Log.d("####", "handleMessage: " + i);
                //判断缓存是否有内容
                HomepageBean cacheBean = CaCheManager.getInstance(MyApplication.getContext()).getCacheBean(MyApplication.getContext());
                Log.d("####", "handleMessage: " + cacheBean.getMsg());
                if (i >= 3 &&  cacheBean != null){
                    timer.cancel();
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                }
            }

        }
    };

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        iv_welcome = findViewById(R.id.iv_welcome);
    }

    @Override
    public void initData() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_welcome,"Alpha",0,1);
        objectAnimator.setDuration(3000);
        objectAnimator.start();



        boolean connectStatus = ConnectManager.getInstance().getConnectStatus();
        if (connectStatus) {
            Toast.makeText(this, "有网络连接", Toast.LENGTH_SHORT).show();

            IntegerPresenter integerPresenter = new IntegerPresenter(Constant.HOME_URL, HomepageBean.class);
            integerPresenter.attachGetView(this);
            integerPresenter.getGetData();

            timer = new Timer(true);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.sendEmptyMessage(100);
                }
            },1000,1000);

//            handlerThread.start();
//            handlerThreadInt.start();
//            handlerThreadCache.start();
//
//            syncHandler = new Handler(handlerThread.getLooper()){
//                @Override
//                public void handleMessage(@NonNull Message msg) {
//                    super.handleMessage(msg);
//
//                    if (msg.what == 100){
//                        Log.d("####", "handleMessage: 开始");
//                        littleInt.post(bigTaskInt.getLittleInt());
//                        littleCache.post(bigTaskCache.getLittleCache());
//                    }
//
//                }
//            };
//
//            littleInt = new Handler(littleInt.getLooper());
//            littleCache = new Handler(littleCache.getLooper());
//
//            initLittleTask();
//
//            syncHandler.sendEmptyMessage(100);

        } else {
            Toast.makeText(this, "无网络连接", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }

    }

//    //初始化任务
//    private void initLittleTask() {
//        bigTaskInt.setLittleInt(new LittleInt(){
//            @Override
//            public void run() {
//                super.run();
//
//                i++;
//                Log.d("####", "run: " + i);
//                if (i >= 3){
//                    if (bigTaskCache.getFinishFlag() == 1){
//                        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
//                        finish();
//                    }else {
//                        run();
//                    }
//                }else {
//                    run();
//                }
//
//            }
//        });
//
//        bigTaskCache.setLittleCache(new LittleCache(){
//            @Override
//            public void run() {
//                super.run();
//                HomepageBean cacheBean = CaCheManager.getInstance(WelcomeActivity.this).getCacheBean(WelcomeActivity.this);
//                Log.d("####", "run: " + cacheBean.getMsg());
//                if (cacheBean != null){
//                    bigTaskCache.incFinishFlag();
//                }
//
//            }
//        });
//    }

    @Override
    public void onGetDataSucess(HomepageBean data) {
        CaCheManager.getInstance(this).savaBean(data);
    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {

    }
}