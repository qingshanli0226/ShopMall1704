package com.example.framework.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.framework.port.IActivity;


public abstract class BaseVMActivity extends AppCompatActivity implements IActivity {
    ViewDataBinding viewDataBinding;
    public IDataBindingListener iDataBindingListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewDataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        iDataBindingListener.onViewDataBinding(viewDataBinding);
        init();
        initDate();
    }

    protected void registerDataBingListener(IDataBindingListener iDataBindingListener) {
        this.iDataBindingListener = iDataBindingListener;
    }

    protected void unRegisterDataBingListener() {
        iDataBindingListener = null;
    }

    public interface IDataBindingListener {
        void onViewDataBinding(ViewDataBinding viewDataBinding);
    }
}
