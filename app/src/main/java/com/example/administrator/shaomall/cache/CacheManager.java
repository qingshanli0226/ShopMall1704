package com.example.administrator.shaomall.cache;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.shaomall.framework.bean.HomeBean;
import com.example.commen.ACache;
import com.example.commen.Constants;
import com.shaomall.framework.manager.NetConnectManager;


import java.util.LinkedList;
import java.util.List;

//数据缓存管理类
public class CacheManager {
    private static CacheManager instace;
    private List<IHomeReceivedListener> iHomeReceivedListeners = new LinkedList<>();
    private ACache Acache;//缓存
    private CacheService cacheService;

    private CacheManager() {
    }

    public static CacheManager getInstance() {
        if (instace == null)
            instace = new CacheManager();
        return instace;
    }
    public CacheService getCacheService(){
        return this.cacheService;
    }

    public void init(Context context) {
        Acache = ACache.get(context);
        Intent intent = new Intent();
        intent.setClass(context, CacheService.class);

        context.startService(intent);
        context.bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("LW-", "onServiceConnected.......");
                CacheService.CacheBinder serviceBinder = (CacheService.CacheBinder) service;
                cacheService = serviceBinder.getCacheService();
                cacheService.getHomeDate();

                cacheService.registerListener(new CacheService.IHomeDataListener() {
                    @Override
                    public void onHomeDateRecived(HomeBean.ResultBean bean) {
                        if (bean != null) {
                            saveLocal(bean);
                            Log.i("lw", "onHomeDateRecived------: "+bean.getBanner_info().size());
                            //service通知数据已经获取到
                            for (IHomeReceivedListener listener : iHomeReceivedListeners) {
                                listener.onHomeDataReceived(bean);
                            }
                        }
                    }
                });
                if (!NetConnectManager.getInstance().isConnectStatus()) {
                    return;
                } else {
                    cacheService.getHomeDate();
                }
                NetConnectManager.getInstance().registerNetConnectListener(new NetConnectManager.INetConnectListener() {
                    @Override
                    public void onConnected() {
                        cacheService.getHomeDate();
                    }

                    @Override
                    public void onDisConnected() {

                    }
                });
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, Context.BIND_AUTO_CREATE);
    }

    private void saveLocal(HomeBean.ResultBean bean) {
        //把Bean存储到ACache
        Acache.put(Constants.KEY_HOME_DATA, bean);
    }

    //获取缓存的bean
    public HomeBean.ResultBean getHomeBean() {
        HomeBean.ResultBean acache = (HomeBean.ResultBean) Acache.getAsObject(Constants.KEY_HOME_DATA);
        return acache;
    }

    public void unregisterListener(IHomeReceivedListener iHomeReceivedListener) {
        iHomeReceivedListeners.remove(iHomeReceivedListener);
    }

    public void registerListener(IHomeReceivedListener iHomeReceivedListener) {
        if (iHomeReceivedListeners.contains(iHomeReceivedListener))
            return;
        else
            iHomeReceivedListeners.add(iHomeReceivedListener);
    }

    public interface IHomeReceivedListener {
        void onHomeDataReceived(HomeBean.ResultBean homeBean);
    }



}
