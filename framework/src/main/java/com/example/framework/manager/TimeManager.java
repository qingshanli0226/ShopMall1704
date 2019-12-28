package com.example.framework.manager;

import com.example.framework.bean.TimeBean;
import com.example.framework.port.ITimeListener;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class TimeManager {
    private static TimeManager timeManager;

    public static TimeManager getInstance(){
        if(timeManager==null){
            synchronized (String.class){
                if(timeManager==null){
                    timeManager = new TimeManager();
                }
            }
        }
        return timeManager;
    }

    private ITimeListener iTimeListener;

    public void setiTimeListener(ITimeListener iTimeListener) {
        this.iTimeListener = iTimeListener;
    }

    public void getTime(){
        RetrofitCreator.getTimeInternect().getTime()
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
                            TimeBean timeBean = new Gson().fromJson(string, TimeBean.class);
                            iTimeListener.onGetTime(timeBean);
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
}
