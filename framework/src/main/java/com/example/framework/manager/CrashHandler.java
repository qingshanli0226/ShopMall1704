package com.example.framework.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import androidx.annotation.NonNull;
import com.example.common.code.Constant;

import org.xml.sax.ErrorHandler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;


public class CrashHandler implements Thread.UncaughtExceptionHandler {


    private static final String TAG = CrashHandler.class.getSimpleName();
    private static final String TAG_THIS_APP_NAME = Constant.APP_NAME;
    private static final String ERROR_INFO = "后台检测到程序异常，程序在五秒内自动关闭，请告知开发人员李某某来解决此问题";


    private Context context;
    private Thread.UncaughtExceptionHandler uncaughtExceptionHandler;

    private static CrashHandler crashHandler;

    private final Map<String, String> mMessage = new HashMap<>();
    private static ErrorHandler errorHandler;


    private CrashHandler() {
    }
    public static CrashHandler getInstance(){
        if(crashHandler==null){
            crashHandler = new CrashHandler();
        }
        return crashHandler;
    }
//    初始化异常捕获
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
            if (uncaughtExceptionHandler != null) {
                uncaughtExceptionHandler.uncaughtException(t, e);
            }
        } else {
                //处理过;了人自己退出
                try {
                    Thread.sleep(2000);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
                //TODO 关闭APP
                //noinspection AccessStaticViaInstance
                ActivityInstanceManager.getInstance().finishAllActivity();
                //TODO 结束当前应用程序进程
                android.os.Process.killProcess(android.os.Process.myPid());
                //TODO 结束虚拟机，释放所有内存
                System.exit(0);
        }
        // 传递给保存的UncaughtExceptionHandler
        uncaughtExceptionHandler.uncaughtException(t, e);
    }
        //是否为空
        private boolean isHandler (Throwable e){
            if (null == e) {
                return false;
            } else {
                new Thread() {
                    @Override
                    public void run() {
                        Looper.prepare();
                        Log.e("ssss", "isHandler: 捕获到异常");
                        Looper.loop();
                    }
                }.start();
                collectErrorMessages();
                saveErrorMessages(e);
                return true;
            }
        }

//    保存错误信息
        private void saveErrorMessages (Throwable e){
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : mMessage.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                sb.append(key).append("=").append(value).append("\n");
            }
            Writer writer = new StringWriter();
            PrintWriter pw = new PrintWriter(writer);
            e.printStackTrace(pw);
            Throwable cause = e.getCause();
            // 循环取出Cause
            while (cause != null) {
                cause.printStackTrace(pw);
                cause = e.getCause();
            }
            pw.close();
            String result = writer.toString();
            sb.append(result);
            String time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).format(new Date());
            String fileName = "crash-" + time + "-" + System.currentTimeMillis() + ".log";
            // 有无SD卡
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getPath() + "crash/";
                File dir = new File(path);
                if (!dir.exists()) dir.mkdirs();
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(path + fileName);
                    fos.write(sb.toString().getBytes());
                } catch (Exception e1) {
                    e1.printStackTrace();
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }

        private void collectErrorMessages(){
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
                if (packageInfo != null) {
                    String versionName = TextUtils.isEmpty(packageInfo.versionName) ? "null" : packageInfo.versionName;
                    String versionCode = "" + packageInfo.versionCode;
                    mMessage.put("versionName", versionName);
                    mMessage.put("versionCode", versionCode);
                }
                // 通过反射拿到错误信息
                Field[] fields = Build.class.getFields();
                if (fields.length > 0) {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        try {
                            mMessage.put(field.getName(), Objects.requireNonNull(field.get(null)).toString());
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }

        /**
         * 收集异常信息
         * @param e
         */
        private void collectException(Throwable e){

            final String deviceInfo = Build.VERSION.SDK_INT + ", " + Build.MODEL + ", " + Build.PRODUCT;
            final String errorInfo = e.getMessage();
            new Thread() {
                @Override
                public void run() {
                    super.run();
                    Log.e(TAG_THIS_APP_NAME, "deviceInfo:" + deviceInfo + ", errorInfo:" + errorInfo);

                }
            }.start();
        }

}
