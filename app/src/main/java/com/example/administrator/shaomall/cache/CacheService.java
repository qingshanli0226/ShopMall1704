package com.example.administrator.shaomall.cache;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


import com.shaomall.framework.bean.HomeBean;

import com.example.net.AppNetConfig;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class CacheService extends Service {
    private IHomeDataListener iHomeDataListener;

    public void getHomeDate() {
        RetrofitCreator.getNetApiService().getData(new HashMap<String, String>(), AppNetConfig.HOME_URL, new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            HomeBean homeBean = new Gson().fromJson(string, HomeBean.class);
                            Log.i("lw", "onNext: " + string);
                            iHomeDataListener.onHomeDateRecived(homeBean.getResult());
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

    //定义监听接口
    interface IHomeDataListener {
        void onHomeDateRecived(HomeBean.ResultBean bean);
    }

    //返回Service让其他类调用
    public class CacheBinder extends Binder {
        public CacheService getCacheService() {
            return CacheService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new CacheBinder();
    }

    //注册监听
    public void registerListener(IHomeDataListener listener) {
        this.iHomeDataListener = listener;
    }

    //注销监听
    public void UNRegisterListener() {
        this.iHomeDataListener = null;
    }


}
