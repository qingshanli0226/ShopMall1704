package com.example.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.common.view.MyToolBar;
import com.example.framework.R;
import com.example.framework.manager.ActivityInstanceManager;
import com.example.framework.port.IActivity;
import com.gyf.immersionbar.ImmersionBar;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

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
        //：该方法是【友盟+】Push后台进行日活统计及多维度推送的必调用方法，请务必调用！
        PushAgent.getInstance(this).onAppStart();
        //TODO 沉浸式状态栏
        ImmersionBar.with(this).init();
        activityInstanceManager = ActivityInstanceManager.getInstance();
        activityInstanceManager.addActivity(this);
        init();

        initDate();
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
    //确保在APP的所有Activity的onResume函数中调用MobclickAgent.onResume函数，在所有Activity的onPause函数中调用MobclickAgent.onPause函数。这两个调用将不会阻塞应用程序的主线程，也不会影响应用程序的性能。
    //如果您的Activity之间有继承关系请不要同时在父和子Activity中重复添加onPause和onResume方法，否则会造成重复统计，导致启动次数异常增高。仅在BaseActivity中onResume和onPause函数中添加MobclickAgent.onResume和MobclickAgent.onPause函数。
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
