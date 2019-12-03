package com.example.common;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static CrashHandler crashHandler;

    Context context;


    public CrashHandler(Context context) {
        this.context = context;
    }

    public static CrashHandler getInstance(Context context) {
        if (crashHandler == null) {
            crashHandler = new CrashHandler(context);
        }
        return crashHandler;
    }

    public void init() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }


    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                Toast.makeText(context, "出现未捕获异常", Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }

        try {
            PrintStream printStream = new PrintStream(new FileOutputStream("/sdcard/exception.txt"));
            e.printStackTrace(printStream);
            e.printStackTrace();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }

        AppActivityManager.finishActivity();
        System.exit(0);
    }
}
