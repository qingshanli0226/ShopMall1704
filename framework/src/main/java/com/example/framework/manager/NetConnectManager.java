package com.example.framework.manager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;
import android.util.Log;

import com.example.framework.port.INetConnectListener;

import java.util.LinkedList;
import java.util.List;
/**
 * author:李浩帆
 */
public class NetConnectManager {
    //TODO application的上下文
    private Context applicationContext;
    //TODO 网络连接状态
    private boolean netConnectStatus = false;
    //TODO 网络类型
    private String netType;
    //TODO 连接管理
    private ConnectivityManager connectivityManager;
    //TODO 使用链表 可能有多个页面需要网络监听
    private List<INetConnectListener> iNetConnectListenerList = new LinkedList<>();

    /**
     * 单例模式
     */
    //TODO 私有化对象
    private static NetConnectManager netConnectManager;
    //TODO 私有化构造
    private NetConnectManager() {
    }

    //TODO 对外提供对象
    public static NetConnectManager getInstance(){
        if(netConnectManager==null){
            netConnectManager = new NetConnectManager();
        }
        return netConnectManager;
    }

    //TODO 初始化
    public void init(Context applicationContext){
        this.applicationContext = applicationContext;
        //TODO 获取网络连接状态
        connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo!= null && networkInfo.isConnected()){
            netConnectStatus = true;
        } else {
            netConnectStatus = false;
        }

        //TODO 注册广播去监听当前网络连接的变化
        IntentFilter intentFilter = new IntentFilter();
        //TODO 监听系统广播
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //TODO 监听
        applicationContext.registerReceiver(broadcastReceiver, intentFilter);
    }

    //TODO 注册网络监听
    public void registerNetConnectListener(INetConnectListener netConnectListener){
        if(!iNetConnectListenerList.contains(netConnectListener) && netConnectListener!=null){
            iNetConnectListenerList.add(netConnectListener);
        }
    }

    //TODO 注销网络监听
    public void unRegisterNetConnectListener(INetConnectListener netConnectListener){
        if(iNetConnectListenerList.contains(netConnectListener) && netConnectListener!=null){
            iNetConnectListenerList.remove(netConnectListener);
        }
    }

    //TODO 让其他类获取网络状态
    public boolean isNetConnectStatus(){
        return netConnectStatus;
    }

    //TODO 让其他类获取网络类型
    public String isNetType(){
        return netType;
    }

    //TODO 回调更新网络状态
    private void notifyConnectChanged(){
        for (INetConnectListener iNetConnectListener : iNetConnectListenerList) {
            if(netConnectStatus){
                iNetConnectListener.onConnected();
            }else{
                iNetConnectListener.onDisConnected();
            }
        }
    }

    //TODO 通过广播来监听网络
    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //TODO 监听wifi的打开与关闭，与wifi的连接无关
            if (WifiManager.WIFI_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                int wifiState = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0);
                if (wifiState == WifiManager.WIFI_STATE_DISABLED) {//wifi关闭
                    Log.d("netstatus", "wifi已关闭");
                } else if (wifiState == WifiManager.WIFI_STATE_ENABLED) {//wifi开启
                    Log.d("netstatus", "wifi已开启");
                } else if (wifiState == WifiManager.WIFI_STATE_ENABLING) {//wifi开启中
                    Log.d("netstatus", "wifi开启中");
                } else if (wifiState == WifiManager.WIFI_STATE_DISABLING) {//wifi关闭中
                    Log.d("netstatus", "wifi关闭中");
                }
            }
            //TODO 监听wifi的连接状态即是否连上了一个有效无线路由
            if (WifiManager.NETWORK_STATE_CHANGED_ACTION.equals(intent.getAction())) {
                Parcelable parcelableExtra = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                if (parcelableExtra != null) {
                    Log.d("netstatus", "wifi parcelableExtra不为空");
                    NetworkInfo networkInfo = (NetworkInfo) parcelableExtra;
                    if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {//已连接网络
                        Log.d("netstatus", "wifi 已连接网络");
                        if (networkInfo.isAvailable()) {//并且网络可用
                            Log.d("netstatus", "wifi 已连接网络，并且可用");
                        } else {//并且网络不可用
                            Log.d("netstatus", "wifi 已连接网络，但不可用");
                        }
                    } else {//网络未连接
                        Log.d("netstatus", "wifi 未连接网络");
                    }
                } else {
                    Log.d("netstatus", "wifi parcelableExtra为空");
                }
            }
            //TODO 监听网络连接，总网络判断，即包括wifi和移动网络的监听
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                NetworkInfo networkInfo = intent.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);
                //TODO 连上的网络类型判断：wifi还是移动网络
                if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                    netType = "WIFI";
                } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                    netType = "移动流量";
                }
            }
            //TODO 监听网络
            if (intent.getAction().equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                connectivityManager = (ConnectivityManager) applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager
                        .getActiveNetworkInfo();
                if (networkInfo!=null && networkInfo.isConnected()) {
                    netConnectStatus = true;
                } else {
                    netConnectStatus = false;
                }
                //TODO 回调通知网络连接的变化
                notifyConnectChanged();
            }
        }
    };
}
