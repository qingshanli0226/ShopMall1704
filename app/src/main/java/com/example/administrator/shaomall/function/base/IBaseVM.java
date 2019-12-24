package com.example.administrator.shaomall.function.base;

/**
 * IBaseVM(ViewModel的Base)
 * 因为ViewModel需要持有一个IBaseView的实例，
 * （当从Model中获取数据后，或者显示错误/显示数据获取完成等需要使用接口的方式反馈给View）
 *
 * @param <V>
 */
public interface IBaseVM<V extends IBaseView> {
    void attachView(V view);

    void detachView();

    V getIView();
}
