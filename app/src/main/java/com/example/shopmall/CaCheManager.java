package com.example.shopmall;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class CaCheManager {

    private static CaCheManager caCheManager;
    private Context context;

    public static CaCheManager getInstance() {
        if (caCheManager == null) {
            return new CaCheManager();
        }
        return caCheManager;
    }

    private ShopService shopService;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            shopService = ((ShopService.MyBinder) service).getService();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    public void init(Context context) {
        this.context = context;
        Intent intent = new Intent(context, ShopService.class);
        this.context.startService(intent);
        this.context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }
}
