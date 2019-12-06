package com.shaomall.framework.base;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

public abstract class BaseListAdapter<T> extends BaseAdapter {
    private List<T> datas;

    public BaseListAdapter(List<T> datas) {
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder<T> holder;
        if (convertView == null){
            holder = geHolder();
        }else {
            holder = (BaseHolder<T>) convertView.getTag();
        }

        T data = datas.get(position);
        holder.setDatas(data);
        return holder.getRootView();
    }
    protected abstract BaseHolder<T> geHolder();
}
