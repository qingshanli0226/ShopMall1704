package com.example.dimensionleague.activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.buy.activity.PayActivity;
import com.example.common.User;
import com.example.framework.manager.AccountManager;
import com.example.dimensionleague.AutoLoginManager;
import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.common.HomeBean;
import com.example.dimensionleague.userbean.AutoLoginBean;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.port.ITaskFinishListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseNetConnectActivity implements ITaskFinishListener {
    private Handler handler =new MyHandler(this);

    private ViewPager vp;
    private Button but;
    private List<Integer> icon;
    private int index = 3;
    private int count = 0;

    private boolean isCarouselFinish = false;
    private boolean isRequestHomeBean = false;
    private boolean isRequestAutoLogin = false;

    @Override
    public void init() {
        super.init();
        vp = findViewById(R.id.welcome_vp);
        but = findViewById(R.id.welcome_button);
        icon = new ArrayList<>();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    @Override
    public void initDate() {
        icon.add(R.mipmap.welcomeimage6);
        icon.add(R.mipmap.welcomeimage7);
        icon.add(R.mipmap.welcomeimage8);

        but.setText(index + "秒");
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
                try {
                    AlertDialog alertDialog = new AlertDialog.Builder(WelcomeActivity.this)
                            .setTitle("警告")
                            .setMessage("网络信号不好哟~宝宝卡得要哭了~")
                            .setPositiveButton("点击重试", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    CacheManager.getInstance().getHomeDate();
                                }
                            }).create();
                    alertDialog.show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        AutoLoginManager.getInstance().registerAutoLoginListener(new AutoLoginManager.IAutoLoginReceivedListener() {
            @Override
            public void onAutoLoginReceived(AutoLoginBean.ResultBean resultBean) {
                if (resultBean != null) {
                    //TODO 保存用户信息
                    AccountManager.getInstance().setUser(new User(
                            resultBean.name,
                            resultBean.password,
                            resultBean.email,
                            resultBean.phone,
                            resultBean.point,
                            resultBean.address,
                            resultBean.money,
                            resultBean.avatar
                    ));
                    //TODO 更新登录状态
                    AccountManager.getInstance().notifyLogin();
                    AccountManager.getInstance().saveToken(resultBean.token);
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


        vp.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return icon.size();
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((ImageView) object);
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                ImageView imageView = new ImageView(WelcomeActivity.this);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                Glide.with(WelcomeActivity.this).load(icon.get(position)).into(imageView);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
                return view == object;
            }
        });
        handler.sendEmptyMessageDelayed(1,1000);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        handler = null;
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
            bundle.putBoolean("isAutoLogin",isRequestAutoLogin);
            startActivity(MainActivity.class,bundle);
            finish();
        }
    }
    private static class MyHandler extends Handler {
        private WeakReference<WelcomeActivity> mWeakReference;

        public MyHandler(WelcomeActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            WelcomeActivity activity = mWeakReference.get();
            if(msg.what==1){
                activity.count++;
                activity.index--;
                if (activity.count < 3) {
                    activity.vp.setCurrentItem(activity.count);
                    activity.but.setText("" + activity.index + "秒");
                    sendEmptyMessageDelayed(1,1000);
                } else {
                    activity.isCarouselFinish = true;
                    activity.onFinish();
                }
            }
        }
    }
}

