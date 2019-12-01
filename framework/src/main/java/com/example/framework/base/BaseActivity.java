package com.example.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.framework.manager.ActivityInstanceManager;
import com.example.framework.port.IActivity;
import com.example.framework.port.IView;
import com.gyf.immersionbar.ImmersionBar;

public abstract class BaseActivity extends AppCompatActivity implements IActivity, IView {

    ActivityInstanceManager activityInstanceManager;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ImmersionBar.with(this).init();
        activityInstanceManager = ActivityInstanceManager.getInstance();
        activityInstanceManager.addActivity(this);
    }

    //TODO 启动新的activity
    public void startActivity(Class Activity,Bundle bundle){
        Intent intent = new Intent(this,Activity);
        //TODO 携带数据
        if(bundle != null && bundle.size() != 0){
            intent.putExtra("data",bundle);
        }
        startActivity(intent);
    }

    //TODO 销毁当前的Activity
    public void removeCurrentActivity(){
        activityInstanceManager.removeActivity(this);
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
        removeCurrentActivity();
    }
}
