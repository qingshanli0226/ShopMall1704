package com.example.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

//T代表bean类,V代表viewholder
public abstract class BaseAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {
    //数据集合
    private List<T> dataList = new ArrayList<>();

    //刷新数据
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

    //根据类型返回不同的viewholder
    protected abstract V getViewHolder(View view, int viewType);

<<<<<<< HEAD
    protected abstract int getLayout();
=======
    //返回布局文件
    protected abstract int getLayout(int viewType);
>>>>>>> one

    @Override
    public void onBindViewHolder(@NonNull V holder, int position) {
        //绑定holder,根据不通过的position来给不同的布局绑定数据
        onBindHolder(holder, dataList.get(position), position);
    }

    //绑定布局
    protected abstract void onBindHolder(V holder, T t, int type);

    //返回不同的布局类型
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
