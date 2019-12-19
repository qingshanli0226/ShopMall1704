package com.example.dimensionleague.type;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.activity.GoodsActiviy;
import com.example.common.utils.IntentUtil;
import com.example.dimensionleague.R;
import com.example.common.TypeBean;

import java.util.ArrayList;
import java.util.List;

public class TypeRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    //热卖商品列表的数据
    private List<TypeBean.ResultBean> resultBeans;
    //热卖
    public static final int HOT = 0;
    //普通的
    public static final int ORDINARY = 1;

    //当前的类型
    public int currentType;

    private final LayoutInflater mLayoutInflater;

    public TypeRightAdapter(Context mContext, List<TypeBean.ResultBean> result) {
        this.mContext = mContext;
        this.resultBeans = result;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new OrdinaryViewHolder(mLayoutInflater.inflate(R.layout.type_rv_right, parent,false), mContext);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        OrdinaryViewHolder ordinaryViewHolder = (OrdinaryViewHolder) holder;
        ordinaryViewHolder.setData(resultBeans,position);

    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return resultBeans.size();
    }

    class OrdinaryViewHolder extends RecyclerView.ViewHolder {
        public Context mContext;
        public RecyclerView rv_type_right;
        public RecyclerView rv_ordinary_right;

        public OrdinaryViewHolder(View itemView, final Context mContext) {
            super(itemView);
            this.mContext = mContext;
            rv_type_right = itemView.findViewById(R.id.rv_hot_right);
            rv_type_right.setLayoutManager(new GridLayoutManager(mContext,3));
            rv_ordinary_right = itemView.findViewById(R.id.rv_ordinary_right);
            rv_ordinary_right.setLayoutManager(new GridLayoutManager(mContext,3));

        }

        public void setData(final List<TypeBean.ResultBean> resultBeans, final int position) {

            TypeRecycleViewAdapter classifyRecyclerRightAdapter = new TypeRecycleViewAdapter(R.layout.type_right_rv,resultBeans.get(position).getHot_product_list());
            rv_type_right.setAdapter(classifyRecyclerRightAdapter);

            classifyRecyclerRightAdapter.setLinkedlist(i -> {
                Intent intent =  new Intent(mContext, GoodsActiviy.class);
                intent.putExtra(IntentUtil.SHOW_GOOD, resultBeans.get(position));
                mContext.startActivity(intent);
            });
            TypeChildRecycleAdapter childRecycleAdapter = new TypeChildRecycleAdapter(mContext, (ArrayList<TypeBean.ResultBean.ChildBean>) resultBeans.get(position).getChild());
            rv_ordinary_right.setAdapter(childRecycleAdapter);
        }

    }
}
