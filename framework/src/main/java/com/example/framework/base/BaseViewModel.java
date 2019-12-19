package com.example.framework.base;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.net.RetrofitCreator;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:李浩帆
 */
public abstract class BaseViewModel<T> extends ViewModel {

    private MutableLiveData<T> liveData = new MutableLiveData<>();

    public LiveData<T> getLiveData() {
        return liveData;
    }
    //TODO get请求
    public void doGetData(){
        RetrofitCreator.getNetInterence().getData(getHeaders(),getPath(),getParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<T>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(T t) {
                        liveData.setValue(t);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //TODO 获取头文件
    public  Map getHeaders(){
        return new HashMap();
    }

    //TODO 获取路径
    public abstract String getPath();

    //TODO 获取参数
    public Map getParams(){
        return new HashMap();
    }
}
