package com.example.framework.port;
/**
 * author:李浩帆
 */
public interface IActivity {

    //TODO 获取布局Id
    int getLayoutId();
    //TODO 获取RelativeLayoutId
    int getRelativeLayout();
    //TODO 初始化控件
    void init();
    //TODO 初始化数据
    void initDate();
}
