package com.shaomall.framework.base.view;

import com.example.commen.util.ShopMailError;

import java.util.List;

/**
 * 它是被presenter回调的
 * presenter拿到数据后 通过view回调方法
 * 这个接口就是view的通用回调接口
 * <p>
 * 因为要支持不同的Activity Fragment, 所以使用泛型
 */
public interface IBaseView<T> {
    //通过requestCode进行多次访问
    void onRequestHttpDataSuccess(String message, T data);

    void onRequestHttpDataSuccess(int requestCode, String message, T data);

    void onRequestHttpDataListSuccess(String message, List<T> data);

    void onRequestHttpDataListSuccess(int requestCode, String message, List<T> data);

    void onRequestHttpDataFailed(ShopMailError error);

    void onRequestHttpDataFailed(int requestCode, ShopMailError error);

    void loadingPage(int code);
}
