package com.example.framework.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import com.example.framework.IMyAidlInterface;

public class StepRemoteService extends Service {

    private Intent intent;

    private class RemoteBinder extends IMyAidlInterface.Stub{

        @Override
        public String getAnotherProcessData(String name) throws RemoteException {
            return null;
        }
    }

    ServiceConnection serviceConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

            startActivity(intent);
            bindService(intent,serviceConnection,BIND_AUTO_CREATE);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new RemoteBinder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent=new Intent(this,StepLocalService.class);
        bindService(intent,serviceConnection,BIND_AUTO_CREATE);

    }
}
