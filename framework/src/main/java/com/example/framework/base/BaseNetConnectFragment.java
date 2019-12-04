package com.example.framework.base;

import android.view.View;
import android.widget.RelativeLayout;

import com.example.common.LoadingPageUtils;
import com.example.framework.R;
import com.example.framework.port.INetConnectListener;
import com.example.framework.port.IPresenter;
import com.example.framework.port.IView;

/**
 * author:李浩帆
 */
public abstract class BaseNetConnectFragment extends BaseFragment implements IView, INetConnectListener {

    private LoadingPageUtils loadingPage;
    private RelativeLayout relativeLayout;

    @Override
    public void init(View view) {
        relativeLayout = view.findViewById(getRelativeLayout());
        loadingPage = new LoadingPageUtils(getContext(),relativeLayout);
    }

    //TODO 默认实现get请求数据
    @Override
    public void onHttpGetRequestDataSuccess(Object data){

    }
    //TODO 默认实现post请求数据
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

}
