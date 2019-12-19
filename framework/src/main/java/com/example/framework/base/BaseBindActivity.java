package com.example.framework.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.framework.port.IActivity;

import java.util.LinkedList;

public abstract class BaseBindActivity<DB extends ViewDataBinding> extends AppCompatActivity implements IActivity {
    DB db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = DataBindingUtil.setContentView(this, getLayoutId());
        initView(db);
        init();
        initDate();
    }

    /**
     * 初始化界面
     * @param bindView 界面绑定对象
     */
    protected abstract void initView(DB bindView);

    public interface IDataBindingListener {
        void onViewDataBinding(ViewDataBinding viewDataBinding);
    }
}
