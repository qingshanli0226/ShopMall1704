package com.example.administrator.shaomall.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.view.KeyEvent;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.Toast;

import com.example.administrator.shaomall.app.ShaoHuaApplication;
import com.example.administrator.shaomall.cache.CacheManager;
import com.example.administrator.shaomall.R;
import com.shaomall.framework.bean.HomeBean;
import com.shaomall.framework.base.BaseActivity;

import java.security.Permission;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends BaseActivity {
    private android.widget.RelativeLayout mWelcomeBackground;
    private android.widget.ImageView mIvWelcomeIcon;
    private android.widget.TextView mTvWelcomeVersion;
    private volatile boolean isData = false;
    private int count = 0;
    String[] permissions=new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};


    @Override
    protected int setLayoutId() {
        return R.layout.activity_welcome;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initView() {
//        aCache = ACache.get(this);
////        iHomePresenter = new HomePresenter();
//        iHomePresenter.attachView(this);
//        iHomePresenter.doGetHttpRequest(AppNetConfig.HOME_DATA_CODE);
        CacheManager.getInstance().init(ShaoHuaApplication.context);

        mWelcomeBackground = findViewById(R.id.welcome_background);
        mIvWelcomeIcon = findViewById(R.id.iv_welcome_icon);
        mTvWelcomeVersion = findViewById(R.id.tv_welcome_version);
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



        CacheManager.getInstance().registerListener(new CacheManager.IHomeReceivedListener() {
            @Override
            public void onHomeDataReceived(HomeBean.ResultBean homeBean) {
                isData=true;
//                toClass(RemindActivity.class);
            }
        });
        TimeThread();


//        if (CacheManager.getInstance().getHomeBean()!=null){
//            isData=true;
//        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermisson() {
        boolean flag=true;//默认全部被申请过
        for(int i=0;i<permissions.length;i++){//只要有一个没有申请成功
            if(!(ActivityCompat.checkSelfPermission(this,permissions[i])== PackageManager.PERMISSION_GRANTED)){
                flag=false;
            }
        }
        if(!flag){
            //动态申请权限
            requestPermissions(permissions,100);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==100){
            boolean flag=true;
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i]!=PackageManager.PERMISSION_GRANTED){
                    flag=false;
                }
            }
            if(flag){
                Toast.makeText(this, "ok ", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();
            }
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

//    @Override
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK){
//         return false;
//        }
//        else
//        return super.onKeyDown(keyCode, event);
//    }
}
