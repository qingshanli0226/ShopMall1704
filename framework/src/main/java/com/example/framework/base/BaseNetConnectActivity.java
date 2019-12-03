package com.example.framework.base;

import android.widget.RelativeLayout;
import com.example.common.LoadingPageUtils;
import com.example.framework.manager.NetConnectManager;
import com.example.framework.port.IActivity;
import com.example.framework.port.INetConnectListener;
import com.example.framework.port.IView;

public abstract class BaseNetConnectActivity extends BaseActivity implements IActivity, IView, INetConnectListener {

    //TODO 网络连接管理类
    NetConnectManager netConnectManager;

    private LoadingPageUtils loadingPage;
    private RelativeLayout relativeLayout;

    @Override
    public void init() {
        netConnectManager = NetConnectManager.getInstance();
        netConnectManager.registerNetConnectListener(this);
        relativeLayout = findViewById(getRelativeLayout());
        loadingPage = new LoadingPageUtils(this,relativeLayout);
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

    //TODO 显示加载页面
    @Override
    public void showLoading() {
        loadingPage.showLoading();
    }

    //TODO 隐藏加载页
    @Override
    public void hideLoading() {
        loadingPage.hideLoading();
    }

    //TODO 显示错误页面
    @Override
    public void showError() {
        loadingPage.showError();
    }

    //TODO 显示无网络页面
    @Override
    public void showEmpty() {
        loadingPage.showEmpty();
    }

    //TODO 隐藏错误页面
    @Override
    public void hideError() {
        loadingPage.hideError();
    }
    //TODO 隐藏无网络页面
    @Override
    public void hideEmpty() {
        loadingPage.hideEmpty();
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
