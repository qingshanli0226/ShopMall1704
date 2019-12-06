package com.example.dimensionleague.activity;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.dimensionleague.CacheManager;
import com.example.dimensionleague.R;
import com.example.dimensionleague.businessbean.HomeBean;
import com.example.framework.base.BaseNetConnectActivity;
import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends BaseNetConnectActivity {

    private ViewPager vp;
    private Button but;
    private List<Integer> icon;
    private MyThread thread;
    private int index = 5;
    private int count = 0;
    private Handler handler;
    private boolean isNetOk = false;


    @Override
    public void init() {
        super.init();
        vp = findViewById(R.id.welcome_vp);
        but = findViewById(R.id.welcome_button);
        handler = new WelcomeHandler();
        thread = new MyThread();
        thread.start();
        icon = new ArrayList<>();

    }

    @Override
    public void initDate() {
        super.initDate();
        icon.add(R.drawable.timg1);
        icon.add(R.drawable.timg2);
        icon.add(R.drawable.timg3);
        CacheManager.getInstance().getHomeDate();
        CacheManager.getInstance().registerGetDateListener(new CacheManager.IHomeReceivedListener() {
            @Override
            public void onHomeDataReceived(HomeBean.ResultBean homeBean) {
               synchronized (WelcomeActivity.this){
                   isNetOk=true;
                   if(index==-1){
                       //                    跳转到主页面
                       startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                       finish();
                   }
               }
            }

            @Override
            public void onHomeDataError(String s) {
                synchronized (WelcomeActivity.this){
                    isNetOk=false;
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
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (thread != null) {

            thread.onClose();
        }
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        thread = null;
        handler = null;
        CacheManager.getInstance().unRegisterGetDateListener();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    private class WelcomeHandler extends Handler {

        // 通过复写handlerMessage() 从而确定更新UI的操作
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 101:
                    but.setText("" + index + "秒");
                    count++;
                    if (count < 3) {
                        vp.setCurrentItem(count);
                        Log.i("SSS", "run: welcomeHandler" + count);
                    }
                    break;
                case 102:
                    synchronized (WelcomeActivity.this){
                        if (isNetOk&&index==-1) {
                            //                    跳转到主页面
                            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                            finish();

                        } else {
                            return;
                        }
                        break;
                    }

                default:
                    throw new IllegalStateException("Unexpected value: " + msg.what);
            }
        }
    }

    private class MyThread extends Thread {
        boolean flag = true;

        @Override
        public void run() {
            while (flag) {
                synchronized (WelcomeActivity.this){
                index--;
                Log.i("SSS", "run: welcome线程" + index);
                try {
                    Thread.sleep(1000);
                    if (index == 0) {
                        flag = false;
                        handler.sendEmptyMessage(102);
                    } else {
                        handler.sendEmptyMessage(101);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
            }
        }

        public void onClose() {
            flag = false;
            thread.interrupt();
        }
    }
}
