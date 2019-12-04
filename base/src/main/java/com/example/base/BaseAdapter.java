package com.example.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    private List<T> dataList = new ArrayList<>();

    public void reFresh(List<T> newList) {
        dataList.clear();
        dataList.addAll(newList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public V onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(), null);
        return getViewHolder(view, viewType);
    }

    protected abstract V getViewHolder(View view, int viewType);

    protected abstract int getLayout();

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        onBindHolder(holder, dataList.get(position), position);
    }

    protected abstract void onBindHolder(V holder, T t, int type);

    @Override
    public int getItemViewType(int position) {
        return getViewType(position);
    }

    protected abstract int getViewType(int position);

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        public BaseViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
