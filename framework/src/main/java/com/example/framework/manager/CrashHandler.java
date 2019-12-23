package com.example.framework.manager;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.common.code.Constant;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = CrashHandler.class.getSimpleName();
    private static final String TAG_THIS_APP_NAME = Constant.APP_NAME;
    private static final String ERROR_INFO = "后台检测到程序异常，程序在五秒内自动关闭，请告知开发人员李某某来解决此问题";


    private Context context;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    private static CrashHandler errorHandler;

    private CrashHandler() {
    }
    public static CrashHandler getInstance(){
        if(errorHandler==null){
            errorHandler = new CrashHandler();


        }
        return errorHandler;
    }

    public void initErrorHandler(Context context){
        this.context = context;
        //  /*
        //        * 弹出解决方案之后把崩溃继续交给系统处理，
        //        * 所以保存当前UncaughtExceptionHandler用于崩溃发生时使用。
        //        */
        uncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }
    // 当有未截获的异常时，回调此方法
    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        if (isHandler(e)) {
            handlerException(t, e);
        }
        // 传递给保存的UncaughtExceptionHandler
        uncaughtExceptionHandler.uncaughtException(t, e);


    }
    /**
     * 判断是否为空，才能知道是否需要自己处理
     * @param e
     * @return
     */
    private boolean isHandler(Throwable e) {
        if (null == e) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 处理我需要处理的异常信息，并告知用户，并退出当前应用程序
     * @param t
     * @param ex
     */
    private void handlerException(Thread t, Throwable ex) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                //TODO 默认下,Android是没有开启Looper消息处理的，主线程除外
                Looper.prepare();
                ActivityInstanceManager.toast((Activity) context,ERROR_INFO);
                Looper.loop();
            }
        }.start();

        //TODO 收集异常信息
        collectException(ex);

        try {
            t.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //TODO 关闭APP
        ActivityInstanceManager.getInstance().finishAllActivity();
        //TODO 结束当前应用程序进程
        android.os.Process.killProcess(android.os.Process.myPid());
        //TODO 结束虚拟机，是否所有内存
        System.exit(0);
    }

    /**
     * 收集异常信息
     * @param e
     */
    private void collectException(Throwable e) {

        final String deviceInfo = Build.VERSION.SDK_INT + ", " + Build.MODEL + ", " + Build.PRODUCT;
        final String errorInfo = e.getMessage();
        new Thread(){
            @Override
            public void run() {
                super.run();
                Log.e(TAG_THIS_APP_NAME, "deviceInfo:" + deviceInfo + ", errorInfo:" + errorInfo);

            }
        }.start();
    }
}
