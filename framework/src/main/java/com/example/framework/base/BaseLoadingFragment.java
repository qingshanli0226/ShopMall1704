package com.example.framework.base;

import java.util.List;

public class BaseLoadingFragment extends BaseFragment{
    @Override
    public int getLayoutId() {
        return 0;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onHttpRequestDataSuccess(int requestCode, Object data) {

    }

    @Override
    public void onHttpRequestDataListSuccess(int requestCode, List data) {

    }
}
