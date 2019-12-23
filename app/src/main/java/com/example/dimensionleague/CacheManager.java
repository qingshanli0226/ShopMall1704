package com.example.dimensionleague;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Parcel;
import android.util.Log;

import com.example.common.HomeBean;
import com.example.framework.manager.ErrorDisposeManager;
import com.example.net.AppNetConfig;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CacheManager {
    private static CacheManager instance;
    private IHomeReceivedListener listener;
    private static final String indexPath = "/sdcard/indexData.txt";

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

    private static void writeObject(HomeBean data) {
        FileOutputStream out = null;
        BufferedOutputStream bos = null;
        try {
            out = new FileOutputStream(new File(indexPath));
            bos = new BufferedOutputStream(out);
            @SuppressLint("Recycle") Parcel parcel = Parcel.obtain();
            parcel.writeParcelable(data, 0);
            bos.write(parcel.marshall());
            bos.flush();
            out.flush();
            bos.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            ErrorDisposeManager.HandlerError(e);
        }
    }

    public Object getHomeBeanData() {
        return readObject();
    }

    public void registerGetDateListener(IHomeReceivedListener listener) {
        synchronized (CacheManager.class) {
            this.listener = listener;
        }
    }

    public void unRegisterGetDateListener() {
        synchronized (CacheManager.class) {
            this.listener = null;
        }
    }


    private Object readObject() {
        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(CacheManager.indexPath));
            Log.e("xxx","书卷："+in);
            byte[] bytes = new byte[in.available()];
            in.read(bytes);
            Parcel parcel = Parcel.obtain();
            parcel.unmarshall(bytes, 0, bytes.length);
            parcel.setDataPosition(0);
            HomeBean homeBean = parcel.readParcelable(Thread.currentThread().getContextClassLoader());
            Log.d("xxx","homebean"+homeBean);
            return homeBean;
        } catch (Exception e) {
            Log.e("xxx","书卷："+e.getMessage());
            ErrorDisposeManager.HandlerError(e);
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public void getHomeDate() {
        RetrofitCreator.getNetInterence().getData(new HashMap<>(), AppNetConfig.BASE_URL_JSON + AppNetConfig.HOME_URL, new HashMap<>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        synchronized (CacheManager.class) {
                            try {
                                String string = responseBody.string();
                                HomeBean homeBean = new Gson().fromJson(string, HomeBean.class);
                                Log.e("xxx",string);
                                writeObject(homeBean);
                                if (string == null) {
                                    getHomeDate();
                                    return;
                                }
                                if (listener != null) {
                                    listener.onHomeDataReceived(homeBean.getResult());
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        synchronized (CacheManager.class) {
                            listener.onHomeDataError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public interface IHomeReceivedListener {
        void onHomeDataReceived(HomeBean.ResultBean homeBean);

        void onHomeDataError(String s);
    }
}
