package com.example.framework.base;

import com.example.framework.manager.NetConnectManager;
import com.example.framework.port.IActivity;
import com.example.framework.port.INetConnectListener;
import com.example.framework.port.IView;

public abstract class BaseNetConnectActivity extends BaseActivity implements IActivity, IView, INetConnectListener {

    //TODO 网络连接管理类
    NetConnectManager netConnectManager;
    @Override
    public void init() {
        netConnectManager = NetConnectManager.getInstance();
        netConnectManager.registerNetConnectListener(this);
    }

    @Override
    public void initDate() {

    }


    //TODO 网络状态
    public boolean isConnectStatus(){
        return netConnectManager.isNetConnectStatus();
    }

    //TODO 网络类型
    public String isNetType(){
        return netConnectManager.isNetType();
    }

    //TODO get请求到的数据默认实现
    @Override
    public void onHttpGetRequestDataSuccess(Object data) {

    }

    //TODO post请求到的数据默认实现
    @Override
    public void onHttpPostRequestDataSuccess(Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onConnected() {

    }

    @Override
    public void onDisConnected() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        netConnectManager.unRegisterNetConnectListener(this);
    }
}
