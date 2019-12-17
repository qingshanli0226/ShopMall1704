package com.example.common.view;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.common.R;

public class MyToast {

    public static void showToast(Context context, String msg, Object path,int duration) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View toast_layout = inflater.inflate(R.layout.toast_layout, null);
        toast_layout.setBackground(new ColorDrawable(0));
        ImageView toast_image = toast_layout.findViewById(R.id.toast_image);
        TextView toast_title = toast_layout.findViewById(R.id.toast_title);
        toast_title.setText(msg);
        if(path!=null){
            Glide.with(context).load(path).into(toast_image);
        }else{
            toast_image.setVisibility(View.GONE);
        }
        Toast toast = new Toast(context);
        toast.setView(toast_layout);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setDuration(duration);
        toast.show();
    }
}
