package com.shaomall.framework.base;

import android.view.View;


public abstract class BaseHolder<T> {
    private View rootView;
    private T datas;
    public BaseHolder() {
        rootView = initView();
        rootView.setTag(this);

}

    protected abstract View initView();

    public T getDatas(){return datas;}

    public void setDatas(T datas){
        this.datas = datas;
        refreshData();
    }

    protected abstract void refreshData();

    public View getRootView() {
        return rootView;
    }
}