package com.example.commen.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.commen.Constants;

/**
 * 网络状态
 */
public class NetStateReceiver extends BroadcastReceiver {

    NetChangeObserver mNetChangeObserver;
    private NetType type;

    public NetStateReceiver() {
        this.type = NetType.NONE;
    }

    public void setListener(NetChangeObserver observer) {
        mNetChangeObserver = observer;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent == null || intent.getAction() == null) {
            Log.e(Constants.TAG, "广播异常了");
            return;
        }
        if (intent.getAction().equalsIgnoreCase(Constants.ANDROID_NET_CHANGE_ACTION)) {
            Log.e(Constants.TAG, "网络状态变化了");
            type = NetWorkUtils.getNetworkType();
            if (NetWorkUtils.isNetWorkAvailable()) {
                Log.i(Constants.TAG, "网络连上了");
                mNetChangeObserver.onConnected(type);
            } else {
                Log.i(Constants.TAG, "网络断开了");
                mNetChangeObserver.onDisConnected();
            }
        }
    }
}
