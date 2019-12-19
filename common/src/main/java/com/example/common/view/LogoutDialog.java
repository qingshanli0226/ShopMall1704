package com.example.common.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.common.R;

public class LogoutDialog extends Dialog {
    private Context context;
    public LogoutDialog(@NonNull Context context) {
        super(context);
        this.context = context;
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public LogoutDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;

    }

    protected LogoutDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void init(int layout){
        this.setContentView(layout);
    }
}
