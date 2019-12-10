package com.example.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.framework.R;
import com.example.framework.manager.ActivityInstanceManager;
import com.example.framework.port.IActivity;

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

        //TODO 沉浸式状态栏
        //  ImmersionBar.with(this).init();

        activityInstanceManager = ActivityInstanceManager.getInstance();
        activityInstanceManager.addActivity(this);
        init();

        initDate();
    }

    //TODO 启动新的activity
    public void startActivity(Class Activity,Bundle bundle){
        Intent intent = new Intent(this,Activity);
        //TODO 携带数据
        if(bundle != null && bundle.size() != 0){
            intent.putExtra("data",bundle);
        }
        startActivity(intent);
        //TODO 添加入场动画以及退场动画
        overridePendingTransition(R.anim.slide_to_left_in,R.anim.slide_to_left_out);
    }

    //TODO finish的入场退场动画
    public void finishActivity(){
        finish();
        overridePendingTransition(R.anim.slide_to_right_in,R.anim.slide_to_right_out);
    }

    //TODO 销毁所有的activity
    public void removeAll(){
        activityInstanceManager.finishAllActivity();
    }

    //TODO 吐司
    public void toast(Activity instance,String msg){
        activityInstanceManager.toast(instance,msg);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityInstanceManager.removeActivity(this);

    }

}
