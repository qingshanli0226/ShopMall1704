package com.example.dimensionleague;

import android.content.Context;
import android.util.Log;

import com.example.dimensionleague.businessbean.HomeBean;
import com.example.net.AppNetConfig;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class CacheManager {
    private static CacheManager instance;
    private Context context;
    private IHomeReceivedListener listener;
    private static String indexPath = "/sdcard/indexData.txt";

    public static CacheManager getInstance() {
        if (instance==null){
            instance=new CacheManager();
        }
        return instance;
    }

    private static void writeObject(HomeBean data) {
        ObjectOutputStream oos = null;
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(new File(indexPath));
            oos = new ObjectOutputStream(fos);
            Log.d("ssssss","写入SD卡");
            oos.writeObject(data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public Object getHomeBeanData() {
        return readObject(indexPath);
    }
    public void registerGetDateListener(IHomeReceivedListener listener){
        synchronized (CacheManager.class){
            this.listener=listener;
        }
    }

    public void unRegisterGetDateListener(){
        synchronized (CacheManager.class){
            this.listener=null;
        }
    }
    private Object readObject(String indexPath) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(indexPath);

            ois = new ObjectInputStream(fis);
            Object o = ois.readObject();
            Log.d("ssssss","写出SD卡"+o);
            return o;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }
        return null;
    }
    public void getHomeDate(){
        RetrofitCreator.getNetInterence().getData(new HashMap<String, String>(), AppNetConfig.BASE_URL_JSON+AppNetConfig.HOME_URL,new HashMap<String, String>())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        synchronized (CacheManager.class){
                            try {
                                String string = responseBody.string();
                                HomeBean homeBean = new Gson().fromJson(string, HomeBean.class);
                                Log.i("SSS", "onNext: "+string);
                                writeObject(homeBean);
                                listener.onHomeDataReceived(homeBean.result);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                       synchronized (CacheManager.class){
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
