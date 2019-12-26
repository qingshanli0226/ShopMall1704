package com.example.shopmall.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.framework.base.BaseActivity;
import com.example.framework.base.IGetBaseView;
import com.example.framework.base.IPostBaseView;
import com.example.framework.bean.HomepageBean;
import com.example.framework.manager.CaCheManager;
import com.example.framework.manager.ConnectManager;
import com.example.framework.manager.UserManager;
import com.example.net.Constant;
import com.example.shopmall.MyApplication;
import com.example.shopmall.R;
import com.example.shopmall.bean.AutoLoginBean;
import com.example.shopmall.presenter.AutoLoginPresenter;
import com.example.shopmall.presenter.IntegerPresenter;

/**
 * 起始页
 */
public class WelcomeActivity extends BaseActivity implements IGetBaseView<HomepageBean>, IPostBaseView<AutoLoginBean> {

    private ImageView iv_welcome;
    private int flag = 0;
    private final HandlerThread handlerThread = new HandlerThread("welcome");
    private Handler handler;
    private IntegerPresenter integerPresenter;

    private String[] prems=new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    private boolean isJump=false;
    private ObjectAnimator objectAnimator;

    @Override
    protected int setLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initView() {
        iv_welcome = findViewById(R.id.iv_welcome);

        CaCheManager.getInstance(MyApplication.getContext()).init();

    }

    @SuppressLint("NewApi")
    @Override
    public void initData() {

        handlerThread.start();

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            for (int i=0;i<prems.length;i++){
                if(  checkSelfPermission(prems[i])
                        != PackageManager.PERMISSION_GRANTED){
                    isJump=false;
                    requestPermissions(prems,100);
                }else{
                    isJump=true;
                }
            }
        }else{
            JumpActivity();
        }

        if(isJump==true){
            initAutoLogin();
        }

        initAutoLogin();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (int i = 0; i < prems.length; i++) {
                if (checkSelfPermission(prems[i])
                        != PackageManager.PERMISSION_GRANTED) {
                    isJump = false;
                    requestPermissions(prems, 100);
                } else {
                    isJump = true;
                }
            }
        } else {
            JumpActivity();
        }

        if (isJump == true) {
            JumpActivity();
        }
    }

    private void initAutoLogin() {
        boolean loginStatus = UserManager.getInstance().getLoginStatus();
        if (loginStatus) {
            String token = UserManager.getInstance().getToken();
            AutoLoginPresenter autoLoginPresenter = new AutoLoginPresenter(token);
            autoLoginPresenter.attachPostView(this);
            autoLoginPresenter.getCipherTextData();
        } else {
            Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void JumpActivity() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(iv_welcome, "Alpha", 0, 1);
        objectAnimator.setDuration(3000);
        objectAnimator.start();

        integerPresenter = new IntegerPresenter(Constant.HOME_URL, HomepageBean.class);
        integerPresenter.attachGetView(this);
        integerPresenter.getGetData();

        Thread welcomeThread = new Thread() {
            @Override
            public void run() {
                super.run();
                while (flag <= 3) {
                    synchronized (WelcomeActivity.class) {
                        flag++;
                        if (flag == 3) {
                            handler.sendEmptyMessage(1);
                        }
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        boolean connectStatus = ConnectManager.getInstance().getConnectStatus();
        if (connectStatus) {
            welcomeThread.start();
            handler = new Handler(handlerThread.getLooper()) {
                @Override
                public void handleMessage(@NonNull Message msg) {
                    super.handleMessage(msg);
                    if (flag == 3 && msg.what == 1) {
                        startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                        finish();
                    }
                }
            };
        } else {
            Toast.makeText(this, "请检查网络连接情况...", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "请手动添加权限后,然后重启应用", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    isJump = true;
                }
            }
            if (isJump == true) {
                JumpActivity();
            }
        }
    }

    public void onGetDataSucess(HomepageBean data) {
        CaCheManager.getInstance(this).savaBean(data);
        handler.sendEmptyMessage(1);
    }

    @Override
    public void onPostDataSucess(AutoLoginBean data) {
        if (data.getMessage().equals("登录成功")) {
            Toast.makeText(this, "自动登录成功", Toast.LENGTH_SHORT).show();
            AutoLoginBean.ResultBean result = data.getResult();
            UserManager.getInstance().savaToken(result.getToken());
        }
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (integerPresenter != null) {
            integerPresenter.detachView();
        }


        SharedPreferences welcome = getSharedPreferences("welcome", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = welcome.edit();
        edit.putBoolean("isWelcome", false);
        edit.apply();

        Drawable drawable = iv_welcome.getDrawable();
        BitmapDrawable drawable1 = (BitmapDrawable) drawable;
        Bitmap bitmap = drawable1.getBitmap();
        bitmap.recycle();
        bitmap = null;

        handlerThread.quit();
        if (handler != null) {
            handler.removeCallbacksAndMessages(this);
        }

        if (objectAnimator != null){
            objectAnimator.cancel();
        }

    }
}