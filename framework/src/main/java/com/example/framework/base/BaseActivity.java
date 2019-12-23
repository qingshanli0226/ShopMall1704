package com.example.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.common.view.MyToolBar;
import com.example.framework.R;
import com.example.framework.manager.ActivityInstanceManager;
import com.example.framework.port.IActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.jaeger.library.StatusBarUtil;
import com.jaeger.library.StatusBarUtil;


/**
 * author:李浩帆
 */
public abstract class BaseActivity extends AppCompatActivity implements IActivity {

    //TODO Activity实例管理类
    ActivityInstanceManager activityInstanceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());


        //只改变状态栏颜色,布局不动
//        StatusBarUtil.setColor(this, Color.RED);

        //TODO 沉浸式状态栏
        ImmersionBar.with(this).init();

        activityInstanceManager = ActivityInstanceManager.getInstance();
        activityInstanceManager.addActivity(this);
        init();
        setImmersion();
        initDate();
    }

    protected void setImmersion(){

    }

    //TODO 启动新的activity
    public void startActivity(Class Activity, Bundle bundle) {
        Intent intent = new Intent(this, Activity);
        //TODO 携带数据
        if (bundle != null && bundle.size() != 0) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);
        //TODO 添加入场动画以及退场动画
        overridePendingTransition(R.anim.slide_to_left_in, R.anim.slide_to_left_out);
    }

    public void boundActivity(Intent intent) {
        startActivity(intent);
        //TODO 添加入场动画以及退场动画
        overridePendingTransition(R.anim.slide_to_right_in, R.anim.slide_to_right_out);
    }

    //TODO finish的入场退场动画
    public void finishActivity() {
        finish();
        overridePendingTransition(R.anim.slide_to_right_in, R.anim.slide_to_right_out);
    }

    //TODO 销毁所有的activity
    public void removeAll() {
        activityInstanceManager.finishAllActivity();
    }

    //TODO 吐司
    public void toast(Activity instance, String msg) {
        activityInstanceManager.toast(instance, msg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstanceManager.removeActivity(this);

    }

}
