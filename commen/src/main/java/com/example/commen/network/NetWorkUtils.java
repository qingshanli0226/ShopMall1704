package com.example.commen.network;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 获取网络状态的工具类
 */
public class NetWorkUtils {

    /**
     * @return 是否有网络
     */
    public static boolean isNetWorkAvailable() {
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo[] networkInfos = manager.getAllNetworkInfo();
        if (networkInfos != null) {
            for (NetworkInfo info : networkInfos) {
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @return 网络类型
     */
    public static NetType getNetworkType() {
        //获取连接管理器
        ConnectivityManager manager = (ConnectivityManager) NetworkManager.getDefault().getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return NetType.NONE;
        }

        //获取网络信息
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo == null) {
            return NetType.NONE;
        }

        //获取网络类型
        int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_MOBILE) {
            if (networkInfo.getExtraInfo().toLowerCase().equals("cmnet")) {
                return NetType.CMNET;
            } else {
                return NetType.CMWAP;
            }
        } else if (type == ConnectivityManager.TYPE_WIFI) {
            return NetType.WIFI;
        }
        return NetType.AUTO;
    }

    /**
     * 打开网络设置界面
     *
     * @param context
     */
    public static void openNetSetting(Context context) {
        Intent intent;
        if (android.os.Build.VERSION.SDK_INT > 10) {  // 3.0以上
            //跳转wifi网络界面
            //            intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
            intent = new Intent(android.provider.Settings.ACTION_SETTINGS); //跳转设置界面
        } else {
            intent = new Intent(Intent.ACTION_MAIN);
            intent.setClassName("com.android.phone", "com.android.phone.NetworkSetting");
        }
        context.startActivity(intent);
    }
}
