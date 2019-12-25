package com.example.remindsteporgan.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.example.remindsteporgan.R;

public class LiveActivity extends Activity {
    public static final String TAG=LiveActivity.class.getSimpleName();
    public static void actionToLiveActivity(Context context){
        Intent intent=new Intent(context,LiveActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SSH",TAG);
        setContentView(R.layout.live);
        Window window=getWindow();
        //把这一个像素点放在左上角
        window.setGravity(Gravity.START | Gravity.TOP);
        WindowManager.LayoutParams attributes=window.getAttributes();
        //设置这个Activity的宽高为1个像素点
        attributes.width = 1;
        attributes.height = 1;
        //刚开始的坐标
        attributes.x = 0;
        attributes.y = 0;
        window.setAttributes(attributes);
        ScreenManager.getInstance(this).setActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy",TAG);
    }
}




