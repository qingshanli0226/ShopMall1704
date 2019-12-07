package com.example.shopmall;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;

import com.example.common.ACache;
import com.example.framework.manager.ConnectManager;
import com.example.shopmall.bean.HomepageBean;

import java.util.LinkedList;
import java.util.List;

public class CaCheManager {

    private static CaCheManager caCheManager;
    private Context context;
    private List<IDataRecivedListener> iDataRecivedListeners = new LinkedList<>();
    private ShopService shopService;

    static CaCheManager getInstance() {
        if (caCheManager == null) {
            return new CaCheManager();
        }
        return caCheManager;
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            shopService = ((ShopService.MyBinder) service).getService();
            //注册服务中的接口,此接口是网络请求成功后,把请求下来的bean类传过来
            shopService.registerDownLoadDataInterface(new ShopService.DownLoadDataInterface() {
                @Override
                public void getHomeBeanData(HomepageBean homepageBean) {
                    //遍历注册的接口,并把这个bean类回调给activity使用
                    for (IDataRecivedListener iDataRecivedListener : iDataRecivedListeners) {
                        iDataRecivedListener.onDataRecive(homepageBean);
                    }
                    //存储bean类到sp中
                    savaBean(homepageBean);
                }
            });
            //获取网络状态
            boolean connectStatus = ConnectManager.getInstance().getConnectStatus();
            if (connectStatus) {
                //如果有网就进行网络请求
                shopService.getHttpData();
            }
            ConnectManager.getInstance().registerConnectListener(new ConnectManager.INetConnetListener() {
                @Override
                public void onConnect() {//有网的情况下
                    shopService.getHttpData();//执行服务中的网络请求
                }

                @Override
                public void onDisConnect() {

                }
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    interface IDataRecivedListener {
        void onDataRecive(HomepageBean homepageBean);
    }

    //注册回调接口
    public void registerListener(IDataRecivedListener iDataRecivedListener) {
        if (!iDataRecivedListeners.contains(iDataRecivedListener)) {
            iDataRecivedListeners.add(iDataRecivedListener);
        }
    }

    public void unregisterListener(IDataRecivedListener iDataRecivedListener) {
        iDataRecivedListeners.remove(iDataRecivedListener);
    }

    //把bean类存储到sp中
    private void savaBean(HomepageBean homepageBean) {
        Log.e("####", homepageBean.toString());
        ACache aCache = ACache.get(context);
        aCache.put("HomepageBean", homepageBean);
    }

    //获取存储到sp中的bean类
    public HomepageBean getCacheBean() {
        ACache aCache = ACache.get(context);
        HomepageBean homepageBean = (HomepageBean) aCache.getAsObject("HomepageBean");
        if (homepageBean != null) {
            return homepageBean;
        }
        return null;
    }

    //管理类初始化
    public void init(Context context) {
        this.context = context;
        Intent intent = new Intent(context, ShopService.class);
        this.context.startService(intent);
        this.context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
