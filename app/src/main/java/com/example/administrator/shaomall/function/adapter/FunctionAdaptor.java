package com.example.administrator.shaomall.function.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.function.FunctionActivity;
import com.shaomall.framework.bean.FunctionBean;

import java.util.ArrayList;
import java.util.List;

public class FunctionAdaptor extends RecyclerView.Adapter<FunctionAdaptor.FunctionHolder> {
    private final FunctionActivity activity;
    private List<FunctionBean> datas = new ArrayList<>();

    public FunctionAdaptor(FunctionActivity activity) {
        this.activity = activity;
    }

    //更新数据
    public void upDateData(List<FunctionBean> datas) {
        this.datas.clear();
        this.datas.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FunctionHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.function_adapter_findfor, viewGroup, false);
        return new FunctionHolder(viewDataBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionHolder holder, int i) {
        holder.findForBinding.setVariable(BR.functionBean, datas.get(i));
        holder.findForBinding.executePendingBindings();//防止闪烁
        holder.findForBinding.setVariable(BR.functionActivity, activity);
        holder.findForBinding.setVariable(BR.functionAdapter, this);
        holder.findForBinding.setVariable(BR.position, i);
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class FunctionHolder extends RecyclerView.ViewHolder {
        public ViewDataBinding findForBinding;

        public FunctionHolder(ViewDataBinding itemView) {
            super(itemView.getRoot());
            this.findForBinding = itemView;
        }
    }
}
