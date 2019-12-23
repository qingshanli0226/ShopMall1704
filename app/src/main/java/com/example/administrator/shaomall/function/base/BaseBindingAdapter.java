package com.example.administrator.shaomall.function.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.squareup.leakcanary.LeakTraceElement;

import java.util.ArrayList;
import java.util.List;

import kotlin.contracts.Returns;

public abstract class BaseBindingAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {
    public List<T> mDataList;
    public Context mContext;
    public BaseBindingAdapter(Context context){
        mContext = context;
        mDataList = new ArrayList<>();
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return onCreateVH(viewGroup, i);
    }

    @Override
    public void onBindViewHolder(@NonNull VH vh, int i) {
        onBindVH(vh, i);
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    /**
     * 刷新数据
     * @param list
     */
    public void onRefreshData(List<T> list){
        if (mDataList != null){
            mDataList.clear();
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 加载更多
     * @param list
     */
    public void onLoadMoreData(List<T> list){
        if (mDataList != null && list != null){
            mDataList.addAll(list);
            notifyDataSetChanged();
        }
    }

    public abstract VH onCreateVH(ViewGroup parent, int viewType);
    public abstract void onBindVH(VH holder, int position);
}
