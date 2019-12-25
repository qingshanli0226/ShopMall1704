package com.example.commen.network;

/**
 * 监听接口
 */
public interface NetChangeObserver {
    /**
     * 网络连接成功
     */
    void onConnected(NetType type);

    /**
     * 网络断开
     */
    void onDisConnected();
}
