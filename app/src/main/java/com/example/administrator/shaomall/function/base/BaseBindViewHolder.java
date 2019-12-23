package com.example.administrator.shaomall.function.base;

import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class BaseBindViewHolder extends RecyclerView.ViewHolder {
    ViewDataBinding mDataBinding;
    public BaseBindViewHolder(@NonNull ViewDataBinding itemView) {
        super(itemView.getRoot());
        this.mDataBinding = itemView;
    }

    public ViewDataBinding getmDataBinding() {
        return mDataBinding;
    }
}
