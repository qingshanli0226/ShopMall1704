package com.example.shopmall;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;

public class ConnectManager {

    private static ConnectManager connectManager = new ConnectManager();
    private Context mContext;

    public static ConnectManager getInstance() {
        return connectManager;
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
        this.mContext = context;
        Intent intent = new Intent(mContext, ShopService.class);
        mContext.startService(intent);
        mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

}
