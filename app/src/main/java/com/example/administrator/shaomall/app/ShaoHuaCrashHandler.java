package com.example.administrator.shaomall.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.shaomall.activity.WelcomeActivity;
import com.shaomall.framework.manager.ActivityInstanceManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ShaoHuaCrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = ShaoHuaCrashHandler.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private static ShaoHuaCrashHandler instance = null;
    private Context mContext;

    private ShaoHuaCrashHandler() {
    }

    public static ShaoHuaCrashHandler getInstance() {
        if (instance == null) {
            synchronized (ShaoHuaCrashHandler.class) {
                if (instance == null) {
                    instance = new ShaoHuaCrashHandler();
                }
            }
        }
        return instance;
    }


    public void init(Context context) {
        this.mContext = context;
        Thread.setDefaultUncaughtExceptionHandler(this);//替换默认对象为当前对象
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                //消息处理
                Toast.makeText(mContext, "抓住了一条漏网之鱼", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }).start();


        //将信息写到sd卡
        dumpToSDCard(t, e);
        //上传服务器
        e.printStackTrace();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        Intent intent = new Intent(mContext, WelcomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(intent);

        ActivityInstanceManager.finishAllActivity();
        System.exit(1); //杀死进程
        Process.killProcess(Process.myPid()); //主动杀死进程
    }


    /**
     * 将信息写到sd卡
     *
     * @param t
     * @param e
     */
    private void dumpToSDCard(final Thread t, final Throwable e) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            Log.i(TAG, "no sdcard skip dump ");
            return;
        }

        String mLodPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crashHandler/";
        File file = new File(mLodPath);
        if (!file.exists()) {
            file.mkdirs();
        }

        String time = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss", Locale.CHINA).format(new Date(System.currentTimeMillis()));
        Log.i(TAG, mLodPath + time + ".trace");
        File logFile = new File(mLodPath, time + ".trace");

        try {
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(logFile)));
            pw.println(time);
            dumpPhoneInfo(pw);
            pw.println();
            e.printStackTrace(pw);
            pw.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    /**
     * 应用的版本名称和版本号
     *
     * @param pw
     */
    private void dumpPhoneInfo(PrintWriter pw) {
        //应用的版本名称和版本号
        PackageManager pm = mContext.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                pw.print("App Version: ");
                pw.print(pi.versionName);
                pw.print('_');
                pw.println(pi.versionCode);

                //android版本号
                pw.print("OS Version: ");
                pw.print(Build.VERSION.RELEASE);
                pw.print("_");
                pw.println(Build.VERSION.SDK_INT);

                //手机制造商
                pw.print("Vendor: ");
                pw.println(Build.MANUFACTURER);

                //手机型号
                pw.print("Model: ");
                pw.println(Build.MODEL);

                //cpu架构
                pw.print("CPU ABI: ");
                pw.println(Build.CPU_ABI);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * //压缩文件夹，为上传做准备。节省流量。
     *
     * @param src
     * @param dest
     * @throws IOException
     */
    private void zip(String src, String dest) throws IOException {
        ZipOutputStream out = null;
        File outFile = new File(dest);
        File fileOrDirectory = new File(src);
        out = new ZipOutputStream(new FileOutputStream(outFile));
        if (fileOrDirectory.isFile()) {
            zipFileOrDirectory(out, fileOrDirectory, "");
        } else {
            File[] entries = fileOrDirectory.listFiles();
            for (int i = 0; i < entries.length; i++) {
                zipFileOrDirectory(out, entries[i], "");
            }
        }
        if (null != out) {
            out.close();
        }
    }

    private static void zipFileOrDirectory(ZipOutputStream out, File fileOrDirectory, String curPath) throws IOException {
        FileInputStream in = null;
        if (!fileOrDirectory.isDirectory()) {
            byte[] buffer = new byte[4096];
            int bytes_read;
            in = new FileInputStream(fileOrDirectory);
            ZipEntry entry = new ZipEntry(curPath + fileOrDirectory.getName());
            out.putNextEntry(entry);
            while ((bytes_read = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytes_read);
            }
            out.closeEntry();
        } else {
            File[] entries = fileOrDirectory.listFiles();
            for (int i = 0; i < entries.length; i++) {
                zipFileOrDirectory(out, entries[i], curPath + fileOrDirectory.getName() + "/");
            }
        }
        if (null != in) {
            in.close();
        }
    }


}
