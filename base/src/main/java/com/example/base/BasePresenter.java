package com.example.base;

import android.util.Log;

import com.example.net.Constant;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Observable;

import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

//网络下载
public abstract class BasePresenter<T> implements IBasePresenter<T> {

    private IBaseView<T> baseView;


    @Override
    public void getData() {
        RetrofitCreator.getNetGetSerivice().getData(getPath(), getHeader(), getQuery())
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
                                baseView.onGetDataSucess((T) responseBody.string());
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

    protected abstract String getPath();

    abstract HashMap<String, String> getHeader();

    abstract HashMap<String, String> getQuery();

    //绑定
    @Override
    public void attachView(IBaseView<T> baseView) {
        if (baseView == null) {
            this.baseView = baseView;
        }
    }

    //解绑
    @Override
    public void detachView() {
        this.baseView = null;
    }
}
