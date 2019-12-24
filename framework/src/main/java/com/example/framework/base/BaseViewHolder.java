package com.example.framework.base;

import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.example.framework.R;

/**
 * author:李浩帆
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {

    //TODO 存放View的集合
    private SparseArray<View> sparseArray = new SparseArray<>();

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    //TODO 获取View控件
    public View getView(int viewId){
        View view = sparseArray.get(viewId);
        if(view==null){
            view = itemView.findViewById(viewId);
            sparseArray.put(viewId,view);
        }
        return view;
    }

    //TODO 设置文本控件
    public TextView getTextView(int viewId,String msg){
        TextView textView = (TextView) getView(viewId);
        if(textView!=null){
            textView.setText(msg);
        }
        return textView;
    }

    //TODO 设置图片控件
    public ImageView getImageView(int viewId,Object path){
        ImageView imageView = (ImageView)getView(viewId);
        if(imageView!=null){
            Glide.with(itemView.getContext())
                    .load(path)
                    .apply(new RequestOptions()
                            .placeholder(R.mipmap.register_icon)
                            .error(R.drawable.error_image))
                    .into(imageView);
        }
        return imageView;
    }

    //TODO 设置圆形图片控件
    public ImageView getCircularImages(int viewId,Object path){
        ImageView imageView = (ImageView)getView(viewId);
        if(imageView!=null){
            Glide.with(itemView.getContext()).load(path).apply(new RequestOptions().circleCrop()).into(imageView);
        }
        return imageView;
    }

}
