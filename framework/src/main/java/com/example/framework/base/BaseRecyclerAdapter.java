package com.example.framework.base;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.port.OnClickItemListener;

import java.util.List;

/**
 * author:李浩帆
 */
public abstract class BaseRecyclerAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {

    //TODO ViewHolder的布局ID
    private int layoutId;
    //TODO 数据集合
    public List<T> dateList;
    //TODO 点击事件
    private OnClickItemListener clickListener;

    public void setClickListener(OnClickItemListener clickListener) {
        this.clickListener = clickListener;
    }

    //TODO 构造
    public BaseRecyclerAdapter(int layoutId, List<T> dateList) {
        this.layoutId = layoutId;
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new BaseViewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutId,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, final int position) {
        onBind(holder,position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener!=null){
                    clickListener.onClickListener(position);
                }
            }
        });
    }

    public abstract void onBind(BaseViewHolder holder, int position);

    @Override
    public int getItemCount() {
        return dateList.size()>0? dateList.size():0;
    }
}
