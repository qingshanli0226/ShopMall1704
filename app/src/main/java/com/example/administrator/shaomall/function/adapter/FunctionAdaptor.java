package com.example.administrator.shaomall.function.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.databinding.FunctionAdapterFindforBinding;
import com.shaomall.framework.bean.FunctionBean;

import java.util.ArrayList;
import java.util.List;

public class FunctionAdaptor extends RecyclerView.Adapter<FunctionAdaptor.FunctionHolder> {
    private List<FunctionBean> datas = new ArrayList<>();

    public FunctionAdaptor() {
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
        FunctionAdapterFindforBinding findForBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.function_adapter_findfor, viewGroup, false);
        return new FunctionHolder(findForBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull FunctionHolder holder, int i) {
//        ViewDataBinding inflate = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.function_adapter_findfor, viewGroup, false);
//        inflate.setVariable(BR.functionBean, datas.get(i));
//        inflate.executePendingBindings();//防止闪烁
        holder.findForBinding.setFunctionAdapter(this);
        holder.findForBinding.setFunctionBean(datas.get(i));
        holder.findForBinding.executePendingBindings(); //防止闪烁
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public class FunctionHolder extends RecyclerView.ViewHolder {
        public FunctionAdapterFindforBinding findForBinding;

        public FunctionHolder(FunctionAdapterFindforBinding itemView) {
            super(itemView.getRoot());
            this.findForBinding = itemView;
        }
    }
}
