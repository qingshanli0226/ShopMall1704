package com.example.framework.base;

import com.example.framework.port.IPresenter;
import com.example.framework.port.IView;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * author:李浩帆
 */
public abstract class BasePresenter<T> implements IPresenter<T> {


    private IView<T> iView;

    //TODO postJson
    @Override
    public void onHttpPostJsonRequest(int requestCode) {
        Observable<ResponseBody> data = RetrofitCreator.getNetInterence().postJsonData(getHeaders(), getPath(), getJsonParams());
        onHttpRequest(requestCode,data);
    }

    //TODO get获取数据
    @Override
    public void onHttpGetRequest(final int requestCode){
        Observable<ResponseBody> data = RetrofitCreator.getNetInterence().getData(getHeaders(), getPath(), getParams());
        onHttpRequest(requestCode,data);
    }

    //TODO post获取数据
    @Override
    public void onHttpPostRequest(final int requestCode) {
        Observable<ResponseBody> data = RetrofitCreator.getNetInterence().postData(getHeaders(), getPath(), getParams());
        onHttpRequest(requestCode,data);
    }

    @Override
    public void onHttpRequest(final int requestCode, Observable<ResponseBody> observable) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        iView.showLoading();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        iView.hideLoading();
                        try {
                            T resEntity = new Gson().fromJson(responseBody.string(), getBeanType());
                            //获取数据成功
                            iView.onRequestDataSuccess(requestCode,resEntity);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        iView.hideLoading();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //TODO 实例化IView
    @Override
    public void attachView(IView iView) {
        this.iView = iView;
    }

    //TODO 提供获取头文件的数据
    public HashMap<String, String> getHeaders() {
        return new HashMap<>();
    }

    public HashMap<String, String> getParams() {
        return new HashMap<>();
    }

    public Object getJsonParams() {
        return null;
    }

    //TODO 让子类来提供返回bean的类型
    public abstract Type getBeanType();

    public abstract String getPath();

    //TODO 销毁IView
    @Override
    public void detachView() {
        if (iView != null) {
            iView = null;
        }
    }
}
