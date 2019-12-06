package com.example.framework.base;

import android.util.Log;

import com.example.common.SignUtil;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

//网络下载
public abstract class BasePresenter<T> implements IBasePresenter<T> {

    private IBaseView<T> baseView;

    @Override
    public void getGetData() {
        RetrofitCreator.getNetGetSerivice().getGetData(getPath())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        baseView.onLoadingPage();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        baseView.onStopLoadingPage();
                        try {
                            T data = new Gson().fromJson(responseBody.string(), getBeanType());
                            if (baseView != null)
                                baseView.onGetDataSucess(data);
                            baseView.onLoadingPage();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("####", "" + e.getMessage());
                        if (baseView != null) {
                            baseView.onGetDataFailed(ErrorUtil.handleError(e));
                            baseView.onStopLoadingPage();
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void register() {
        RetrofitCreator.getNetPostService().register(getPath(), getSign())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            T registerBean = new Gson().fromJson(body.string(), getBeanType());
                            baseView.onPostDataSucess(registerBean);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public Map<String, String> getSign() {
        HashMap<String, String> query = getQuery();
        String sign = SignUtil.generateSign(query);
        query.put("sign", sign);
        return SignUtil.encryptParamsByBase64(query);
    }

    @Override
    public void login() {
        RetrofitCreator.getNetPostService().login(getPath(), getSign())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            T loginBean = new Gson().fromJson(body.string(), getBeanType());
                            baseView.onPostDataSucess(loginBean);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    protected abstract Type getBeanType();

    @Override
    public void getPostFormData() {
        RetrofitCreator.getNetPostService().getFormData(getPath(), getHeader(), getQuery())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            if (baseView != null)
                                baseView.onPostDataSucess((T) responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("####", "" + e.getMessage());
                        if (baseView != null) {
                            baseView.onGetDataFailed(ErrorUtil.handleError(e));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void getPostJsonData() {
        RequestBody requestBody = RequestBody.create(MediaType.parse(getType()), getJsonArray().toString());
        RetrofitCreator.getNetPostService().getJsonData(getPath(), requestBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            if (baseView != null)
                                baseView.onPostDataSucess((T) responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("####", "" + e.getMessage());
                        if (baseView != null) {
                            baseView.onGetDataFailed(ErrorUtil.handleError(e));
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private JsonArray getJsonArray() {
        return null;
    }

    private String getType() {
        return null;
    }

    protected abstract String getPath();

    protected abstract HashMap<String, String> getHeader();

    protected abstract HashMap<String, String> getQuery();

    //绑定
    @Override
    public void attachView(IBaseView<T> baseView) {
        this.baseView = baseView;
    }

    //解绑
    @Override
    public void detachView() {
        this.baseView = null;
    }


}
