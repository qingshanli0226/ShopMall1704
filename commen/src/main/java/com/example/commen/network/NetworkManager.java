package com.example.commen.network;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import java.util.LinkedList;
import java.util.List;

public class NetworkManager {
    private static volatile NetworkManager instance;

    private NetStateReceiver mReceiver;
    private Context mApplication;
    private NetChangeObserver mListener;


    public NetworkManager() {
        mReceiver = new NetStateReceiver();
    }

    public static NetworkManager getDefault() {
        if (instance == null) {
            synchronized (NetworkManager.class) {
                if (instance == null) {
                    instance = new NetworkManager();
                }
            }
        }
        return instance;
    }

    public Context getApplication() {
        if (mApplication == null) {
            throw new RuntimeException("NetworkManager.getDefault().init()没有初始化");
        }
        return mApplication;
    }

    public void init(Context application) {
        this.mApplication = application;
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);

        mApplication.registerReceiver(mReceiver, intentFilter);
    }

    /**
     * //注销广播接收者
     */
    public void logout() {
        getApplication().unregisterReceiver(mReceiver);
    }


    public void setListener(NetChangeObserver listener) {
        //        mReceiver.setListener(listener);
        mReceiver.registerNetWorkChangeListener(listener);
    }

    public void unSetListener(NetChangeObserver listener) {
        mReceiver.unRegisterNetWorkChangeListener(listener);
    }
}
