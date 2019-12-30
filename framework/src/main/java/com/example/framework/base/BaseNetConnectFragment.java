package com.example.framework.base;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.common.code.ErrorCode;
import com.example.common.utils.LoadingPageUtils;
import com.example.framework.manager.NetConnectManager;
import com.example.framework.port.INetConnectListener;
import com.example.framework.port.IPresenter;
import com.example.framework.port.IView;

/**
 * author:李浩帆
 */
public abstract class BaseNetConnectFragment extends BaseFragment implements IView, INetConnectListener {

    //TODO 网络连接管理类
    NetConnectManager netConnectManager;

    private LoadingPageUtils loadingPage;

    @Override
    public void init(View view) {
        netConnectManager = NetConnectManager.getInstance();
        netConnectManager.registerNetConnectListener(this);
        if(getRelativeLayout()!=0){
            ViewGroup relativeLayout = view.findViewById(getRelativeLayout());
            loadingPage = new LoadingPageUtils(getContext(),relativeLayout);
        }else {
            loadingPage=new LoadingPageUtils(getContext(),null);
        }
    }

    //TODO 网络状态
    public boolean isConnectStatus(){
        return netConnectManager.isNetConnectStatus();
    }

    //TODO 网络类型
    public String isNetType(){
        return netConnectManager.isNetType();
    }


    @Override
    public void onRequestSuccess(Object data) {


    }

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
    public void onHttpRequestDataFailed(int requestCode, ErrorCode error) {
        toast(getActivity(),error.getErrorMessage());
    }

    @Override
    public void getSearchDataSuccess(String str) {

    }

    @Override
    public void getSearchDataSuccess(int requestCode, String str) {

    }

    @Override
    public void onDestroyView() {
        if (loadingPage!=null){
            loadingPage=null;
            netConnectManager.unRegisterNetConnectListener(this);
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (loadingPage!=null){
            loadingPage=null;
            netConnectManager.unRegisterNetConnectListener(this);
        }
        super.onDestroy();
    }
}

