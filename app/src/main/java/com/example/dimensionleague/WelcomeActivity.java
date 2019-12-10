package com.example.dimensionleague;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dimensionleague.activity.MainActivity;
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
    private boolean isNetOk = true;

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
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcome;
    }

    @Override
    public int getRelativeLayout() {
        return 0;
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
                    if (isNetOk) {
                        startActivity(MainActivity.class,null);
                        finish();
                    } else {
                        Toast.makeText(WelcomeActivity.this, "您的网络状态不佳, 请检查", Toast.LENGTH_LONG).show();
                        handler.sendEmptyMessageDelayed(102, 5000);
                    }
                    break;
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
                }
            }
        }

        public void onClose() {
            flag = false;
        }
    }
}
