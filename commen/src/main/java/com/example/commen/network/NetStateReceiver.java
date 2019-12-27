package com.example.commen.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.util.Log;

import com.example.commen.Constants;

import java.util.LinkedList;
import java.util.List;

/**
 * 网络状态
 */
public class NetStateReceiver extends BroadcastReceiver {

    NetChangeObserver mNetChangeObserver;
    private NetType type;
    private List<NetChangeObserver> mNetworkListener = new LinkedList<>();

    public NetStateReceiver() {
        this.type = NetType.NONE;
    }

    //    public void setListener(NetChangeObserver observer) {
    //        mNetChangeObserver = observer;
    //    }

    public void registerNetWorkChangeListener(NetChangeObserver listener) {
        if (!mNetworkListener.contains(listener)) {
            mNetworkListener.add(listener);
        }
    }

    public void unRegisterNetWorkChangeListener(NetChangeObserver listener) {
        if (mNetworkListener.contains(listener)) {
            mNetworkListener.remove(listener);
        }
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Log.e(Constants.TAG, "广播异常了");
            return;
        }
        if (intent.getAction().equalsIgnoreCase(ConnectivityManager.CONNECTIVITY_ACTION)) {
            Log.e(Constants.TAG, "网络状态变化了");
            type = NetWorkUtils.getNetworkType();

            if (NetWorkUtils.isNetWorkAvailable()) {
                Log.i(Constants.TAG, "网络连上了");
                //                mNetChangeObserver.onConnected(type);
                synchronized (NetStateReceiver.class) {
                    for (NetChangeObserver netChangeObserver : mNetworkListener) {
                        netChangeObserver.onConnected(type);
                    }
                }
            } else {
                Log.i(Constants.TAG, "网络断开了");
                //                mNetChangeObserver.onDisConnected();
                synchronized (NetStateReceiver.class) {
                    for (NetChangeObserver netChangeObserver : mNetworkListener) {
                        netChangeObserver.onDisConnected();
                    }
                }
            }
        }
    }
}
