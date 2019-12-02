package com.example.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.util.LinkedList;

//网络连接管理类
public class ConnectManager {

    private static ConnectManager connectManager = new ConnectManager();
    private Context mContext;
    private boolean ConnectStatus;
    private ConnectivityManager connectivityManager;
    private LinkedList<INetConnetListener> netConnectListenerLinkedList = new LinkedList<>();

    //单例
    public static ConnectManager getInstance() {
        return connectManager;
    }

    //初始化
    public void init(Context context) {
        this.mContext = context;
        //初始化管理类
        connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {//是否网络连接
                ConnectStatus = true;
            } else {
                ConnectStatus = false;
            }
        }
        //注册广播
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver(broadcastReceiver, intentFilter);
    }

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    ConnectStatus = true;
                } else {
                    ConnectStatus = false;
                }
            }
            notifyConnectChanged();
        }
    };

    private void notifyConnectChanged() {
        for (INetConnetListener netConnetListener : netConnectListenerLinkedList) {
            if (ConnectStatus) {
                netConnetListener.onConnect();
            } else {
                netConnetListener.onDisConnect();
            }
        }
    }

    //返回当前是否有网络
    public boolean getConnectStatus() {
        return ConnectStatus;
    }

    //注册网络连接监听
    public void registerConnectListener(INetConnetListener netConnetListener) {
        if (!netConnectListenerLinkedList.contains(netConnetListener)) {
            netConnectListenerLinkedList.add(netConnetListener);
        }
    }

    //注销
    public void unregisterConnectListener(INetConnetListener netConnetListener) {
        netConnectListenerLinkedList.remove(netConnetListener);
    }

    //网络连接接口
    public interface INetConnetListener {
        void onConnect();

        void onDisConnect();
    }

}
