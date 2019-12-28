package com.example.remindsteporgan.util;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import util.IMyAidlInterface;

public class RemoteService extends Service {
    private Intent intent;

    private class RemoteBinder extends IMyAidlInterface.Stub {

        @Override
        public String getAnotherProcessData(String name) throws RemoteException {
            return null;
        }
    }


    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //to do
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {//当绑定的远程服务被系统回收后，该回调将被调用
            Log.d("LQS: ", "RemoteService onServiceDisconnected.....");
            startService(intent);//重新启动被系统回收的进程
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return new RemoteBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        //去绑定远程service
        intent = new Intent(this, LocalService.class);
        bindService(intent, serviceConnection, BIND_AUTO_CREATE);
    }

}
