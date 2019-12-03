package com.example.framework.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.framework.LoadingPage;
import com.example.framework.R;
import com.example.framework.port.IFragment;
import com.example.framework.port.INetConnectListener;
import com.example.framework.port.IView;

/**
 * author:李浩帆
 */
public abstract class BaseNetConnectFragment extends BaseFragment implements IView, INetConnectListener {

    private LoadingPage loadingPage;

    @Override
    public void init(View view) {
        loadingPage = new LoadingPage(getContext(), R.layout.page_loading,R.layout.page_error,R.layout.page_empty);
    }

    @Override
    public void initDate() {

    }


    //TODO 默认实现get请求数据
    @Override
    public void onHttpGetRequestDataSuccess(Object data){

    }
    //TODO 默认实现post请求数据
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

}
