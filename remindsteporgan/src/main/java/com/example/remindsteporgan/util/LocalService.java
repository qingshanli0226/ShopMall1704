package com.example.remindsteporgan.util;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import util.IMyAidlInterface;

public class LocalService extends Service {
    private Intent intent;

    private class LocalBinder extends IMyAidlInterface.Stub {

        @Override
        public String getAnotherProcessData(String name) throws RemoteException {
            return null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new LocalBinder();
    }

    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //to do
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {//当绑定的远程服务被系统回收后，该回调将被调用

            Log.d("LQS: ", "LocalService onServiceDisconnected.....");
            startService(intent);//重新启动被系统回收的进程
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();

        //去绑定远程service
        intent = new Intent(this, RemoteService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }


}
