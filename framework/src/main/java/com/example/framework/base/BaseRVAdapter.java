package com.example.framework.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRVAdapter<T,DB extends ViewDataBinding> extends RecyclerView.Adapter<BaseRVAdapter<T,DB>.BindViewHolder> {
    List<T> baselist ;
    private int layoutId;

    public BaseRVAdapter(List<T> list, int layoutId) {
        this.baselist = list;
        this.layoutId = layoutId;
        if(this.baselist==null){
            this.baselist = new ArrayList<>();
        }
    }

    public void updateData(List<T> beans) {
        baselist.clear();
        baselist.addAll(beans);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BindViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        DB db = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),layoutId,parent,false);
        BindViewHolder bindViewHolder = new BindViewHolder(db.getRoot());
        bindViewHolder.bindView = db;
        return bindViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BindViewHolder holder, int position) {
        onBind(holder, position);
    }

    public abstract void onBind(BindViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return baselist.size();
    }


    public class BindViewHolder extends RecyclerView.ViewHolder {

        public DB bindView;

        public BindViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
