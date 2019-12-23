package com.example.framework.base;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

public abstract class BaseBindFragment<DB extends ViewDataBinding> extends Fragment {

    private DB db;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = DataBindingUtil.inflate(inflater,getLayoutId(),container,false);
        return db.getRoot();
    }

    protected abstract int getLayoutId();

    /**
     * 初始化界面
     * @param bindView 界面绑定对象
     */
    protected abstract void initView(DB bindView);

}
