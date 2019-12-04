package com.example.shopmall;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.net.Constant;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CaCheManager {

    private static CaCheManager caCheManager;
    private Context context;

    public static CaCheManager getInstance() {
        if (caCheManager == null) {
            return new CaCheManager();
        }
        return caCheManager;
    }

    private ShopService shopService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            shopService = ((ShopService.MyBinder) service).getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void getHttpData() {
        RetrofitCreator.getNetGetSerivice().getGetData( )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        try {
                            Object o = new Gson().fromJson(body.string(), );
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
                })
    }

    public void init(Context context) {
        this.context = context;
        Intent intent = new Intent(context, ShopService.class);
        this.context.startService(intent);
        this.context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
