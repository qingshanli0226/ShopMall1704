package com.example.framework.base;

import android.view.View;
import android.widget.RelativeLayout;

import com.example.common.LoadingPageUtils;
import com.example.framework.R;
import com.example.framework.manager.NetConnectManager;
import com.example.framework.port.INetConnectListener;
import com.example.framework.port.IPresenter;
import com.example.framework.port.IView;

import io.reactivex.Observable;

/**
 * author:李浩帆
 */
public abstract class BaseNetConnectFragment extends BaseFragment implements IView, INetConnectListener {

    private LoadingPageUtils loadingPage;
    private RelativeLayout relativeLayout;
    //TODO 网络连接管理类
    NetConnectManager netConnectManager=NetConnectManager.getInstance();
    @Override
    public void init(View view) {
        relativeLayout = view.findViewById(getRelativeLayout());
        loadingPage = new LoadingPageUtils(getContext(),relativeLayout);
    }

    //TODO 一次请求
    @Override
    public void onRequestSuccess(Object data) {

    }

    //TODO 网络状态
    public boolean isConnectStatus(){
        return netConnectManager.isNetConnectStatus();
    }

    //TODO 网络类型
    public String isNetType(){
        return netConnectManager.isNetType();
    }

    //TODO 多次请求
    @Override
    public void onRequestSuccess(int requestCode, Object data) {

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

}
