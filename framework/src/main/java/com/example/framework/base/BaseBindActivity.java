package com.example.framework.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.framework.R;
import com.example.framework.port.IActivity;
import com.gyf.immersionbar.ImmersionBar;

import java.util.LinkedList;

public abstract class BaseBindActivity<DB extends ViewDataBinding> extends AppCompatActivity{
    DB db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this, getLayoutId());
        initView(db);
        initDate();
        //TODO 沉浸式状态栏
        ImmersionBar.with(this).init();
    }

    /**
     * 获取布局Id
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化界面
     * @param bindView 界面绑定对象
     */
    protected abstract void initView(DB bindView);

    /**
     * 初始化数据
     */
    protected abstract void initDate();

    //TODO finish的入场退场动画
    public void finishActivity() {
        finish();
        overridePendingTransition(R.anim.slide_to_right_in, R.anim.slide_to_right_out);
    }
}
