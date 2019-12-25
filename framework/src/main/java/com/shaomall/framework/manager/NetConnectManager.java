package com.shaomall.framework.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.LinkedList;
import java.util.List;

//Mannager类都为单例
public class NetConnectManager {
    private Context applicationContext;
    private boolean connectStatus = false;
    ConnectivityManager connectivityManager;
    //使用链表 因为可能多个页面需要来监听
    List<INetConnectListener> iNetConnectListenerList = new LinkedList<>();
    private static NetConnectManager instance;

    private NetConnectManager() {

    }

    public static NetConnectManager getInstance() {
        if (instance == null)
            instance = new NetConnectManager();
        return instance;
    }

    public void init(Context applicationContext) {
        //1.获取当前网络连接情况
        this.applicationContext = applicationContext;
        connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        connectStatus = networkInfo != null && networkInfo.isConnected();
        //注册广播监听当前网络连接的变化
        IntentFilter intentFilter = new IntentFilter();
        //监听系统广播
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //注册广播 监听
        applicationContext.registerReceiver(connextReceiver, intentFilter);
    }

    private void notifyConnectChanged() {
        for (INetConnectListener listener : iNetConnectListenerList) {
            if (connectStatus) {
                listener.onConnected();
            } else {
                listener.onDisConnected();
            }
        }
    }

    //网络变化广播
    private BroadcastReceiver connextReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                connectStatus = networkInfo != null && networkInfo.isConnected();
                notifyConnectChanged();//回调通知网络连接的变化
            }
        }
    };

    public void registerNetConnectListener(INetConnectListener iNetConnectListener) {
        if (!iNetConnectListenerList.contains(iNetConnectListener) && iNetConnectListener != null) {
            iNetConnectListenerList.add(iNetConnectListener);
        }
    }

    public void unRegisterNetConnerctListener(INetConnectListener iNetConnectListener) {
        if (iNetConnectListenerList.contains(iNetConnectListener) && iNetConnectListener != null) {
            iNetConnectListenerList.remove(iNetConnectListener);
        }
    }

    public interface INetConnectListener {
        void onConnected();

        void onDisConnected();
    }

    //让其他类获取网络状态
    public boolean isConnectStatus() {
        return connectStatus;
    }
}
