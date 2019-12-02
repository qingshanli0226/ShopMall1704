package com.example.base;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class ShopService extends Service {
    public ShopService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    class MyBinder extends Binder {
        public ShopService getService() {
            return ShopService.this;
        }
    }


}
