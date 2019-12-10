package com.example.framework.base;

import com.example.common.Constant;
import com.example.common.utils.SignUtil;
import com.example.framework.port.IPresenter;
import com.example.framework.port.IView;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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

    //TODO get获取单数据
    @Override
    public void doHttpGetRequest(){
        getDate(RetrofitCreator.getNetInterence().getData(getHeaders(), getPath(), getParams()));
    }

    //TODO post获取单数据
    @Override
    public void doHttpPostRequest() {
        getDate(RetrofitCreator.getNetInterence().postData(getHeaders(), getPath(), signEncrypt()));
    }

    //TODO get获取多数据
    @Override
    public void doHttpGetRequest(final int requestCode){
        getDate(requestCode,RetrofitCreator.getNetInterence().getData(getHeaders(), getPath(), getParams()));
    }

    //TODO post获取多数据
    @Override
    public void doHttpPostRequest(final int requestCode) {
        getDate(requestCode,RetrofitCreator.getNetInterence().postData(getHeaders(), getPath(), signEncrypt()));
    }

    @Override
    public void getDate(Observable<ResponseBody> data) {
        data.subscribeOn(Schedulers.io())
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
                            //TODO 获取数据成功
                            iView.onRequestSuccess(resEntity);
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

    @Override
    public void getDate(final int requestCode, Observable<ResponseBody> data) {
        data.subscribeOn(Schedulers.io())
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
                            iView.onRequestSuccess(requestCode,resEntity);
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

    //TODO 父类默认实现
    public Map<String, String> getParams() {
        return new HashMap<>();
    }

    //TODO 上传Json数据
    public Object getJsonParams() {
        return null;
    }
    //TODO 加签加密
    public Map<String,String> signEncrypt(){
        Map<String, String> params = getParams();
        String sign = SignUtil.generateSign(params);
        params.put(Constant.SIGN,sign);
        TreeMap<String, String> treeMap = SignUtil.encryptParamsByBase64(params);
        return treeMap;
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
