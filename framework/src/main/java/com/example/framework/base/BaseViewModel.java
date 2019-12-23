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
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * author:李浩帆
 */
public abstract class BaseViewModel extends ViewModel {

    private MutableLiveData<ResponseBody> liveData = new MutableLiveData<>();

    public LiveData<ResponseBody> getLiveData() {
        return liveData;
    }
    //TODO get请求
    public void doGetData(){
        RetrofitCreator.getNetInterence().getData(getHeaders(),getPath(),getParams())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>(){
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody requestBody) {
                        liveData.setValue(requestBody);
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
