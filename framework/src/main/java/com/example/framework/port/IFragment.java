package com.example.framework.port;

import android.view.View;

/**
 * author:李浩帆
 */
public interface IFragment {
    //TODO 获取布局Id
    int getLayoutId();

    //TODO 获取RelativeLayoutId
    int getRelativeLayout();

    //TODO 初始化方法
    void init(View view);

    //TODO 初始化数据方法
    void initDate();

}
