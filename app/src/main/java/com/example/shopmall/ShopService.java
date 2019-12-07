package com.example.shopmall;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.common.ACache;
import com.example.net.RetrofitCreator;
import com.example.shopmall.bean.HomepageBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class ShopService extends Service {
    public ShopService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends Binder {
        public ShopService getService() {
            return ShopService.this;
        }
    }

    public void getHttpData() {
        RetrofitCreator.getNetGetSerivice().getGetData("HOME_URL.json", new HashMap<String, String>(), new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            HomepageBean homepageBean = new Gson().fromJson(body.string(), HomepageBean.class);
                            downLoadDataInterface.getHomeBeanData(homepageBean);
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




    DownLoadDataInterface downLoadDataInterface;

    public void registerDownLoadDataInterface(DownLoadDataInterface downLoadDataInterface) {
        this.downLoadDataInterface = downLoadDataInterface;
    }


    public interface DownLoadDataInterface {
        void getHomeBeanData(HomepageBean homepageBean);
    }


}
