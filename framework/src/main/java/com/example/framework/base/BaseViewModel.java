package com.example.framework.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class BaseViewModel<T> extends ViewModel {

    private MutableLiveData<T> tMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<T> gettMutableLiveData() {
        return tMutableLiveData;
    }

    //网络请求
    public void getData() {
        RetrofitCreator.getNetGetSerivice().getGetData(getPath(), getHead(), getParms())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            String string = body.string();
                            T data = new Gson().fromJson(string, getType());
                            tMutableLiveData.setValue(data);
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

    private Type getType() {
        return null;
    }

    private HashMap<String, String> getParms() {
        return new HashMap<>();
    }

    private HashMap<String, String> getHead() {
        return new HashMap<>();
    }

    private String getPath() {
        return null;
    }
}
