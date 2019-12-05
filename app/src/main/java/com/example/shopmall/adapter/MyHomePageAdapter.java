package com.example.shopmall.adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseAdapter;


public class MyHomePageAdapter extends BaseAdapter<Object,RecyclerView.ViewHolder> {



    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        return null;
    }

    @Override
    protected int getLayout(int viewType) {
        return 0;
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, Object o, int type) {

    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }
}
