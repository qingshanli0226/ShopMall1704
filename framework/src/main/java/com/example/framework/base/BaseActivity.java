package com.example.framework.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.framework.manager.ActivityInstanceManager;
import com.example.framework.manager.NetConnectManager;
import com.example.framework.port.IActivity;
import com.example.framework.port.INetConnectListener;
import com.example.framework.port.IView;
import com.gyf.immersionbar.ImmersionBar;
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
        ImmersionBar.with(this).init();
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
