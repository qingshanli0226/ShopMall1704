package com.example.dimensionleague.type;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.activity.GoodsActivity;
import com.example.buy.databeans.GoodsBean;
import com.example.common.utils.IntentUtil;
import com.example.dimensionleague.R;
import com.example.common.TypeBean;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.net.AppNetConfig;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

class TypeRightAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context mContext;
    //热卖商品列表的数据
    private final List<TypeBean.ResultBean> resultBeans;
    private final LayoutInflater mLayoutInflater;
    private ArrayList<TypeBean.ResultBean.HotProductListBean> hotProductList=new ArrayList();

    public TypeRightAdapter(Context mContext, List<TypeBean.ResultBean> result) {
        this.mContext = mContext;
        this.resultBeans = result;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        return new OrdinaryViewHolder(mLayoutInflater.inflate(R.layout.type_rv_right, parent,false), mContext);

    }

    @Override
    public void onBindViewHolder(@NotNull RecyclerView.ViewHolder holder, int position) {
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
        final Context mContext;
        final RecyclerView rv_type_right;
        final RecyclerView rv_ordinary_right;

        OrdinaryViewHolder(View itemView, final Context mContext) {
            super(itemView);
            this.mContext = mContext;
            rv_type_right = itemView.findViewById(R.id.rv_hot_right);
            rv_type_right.setLayoutManager(new GridLayoutManager(mContext,3));
            rv_ordinary_right = itemView.findViewById(R.id.rv_ordinary_right);
            rv_ordinary_right.setLayoutManager(new GridLayoutManager(mContext,3));

        }

        void setData(final List<TypeBean.ResultBean> resultBeans, final int position) {
            hotProductList.clear();
            hotProductList.addAll(resultBeans.get(position).getHot_product_list());
            rv_type_right.setAdapter( new BaseRecyclerAdapter<TypeBean.ResultBean.HotProductListBean>(R.layout.type_right_rv,hotProductList){

                @Override
                public void onBind(BaseViewHolder holder, int position) {
                    holder.getTextView(R.id.type_right_tv_ordinary_right, "￥" + dateList.get(position).getCover_price()).setTextColor(Color.RED);
                    holder.getImageView(R.id.type_right_iv_ordinary_right, AppNetConfig.BASE_URl_IMAGE + dateList.get(position).getFigure())
                            .setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Intent intent = new Intent(holder.itemView.getContext(), GoodsActivity.class);
                                    intent.putExtra(IntentUtil.GOTO_GOOD, new GoodsBean(
                                            hotProductList.get(position).getProduct_id(),
                                            0,
                                            hotProductList.get(position).getName(),
                                            hotProductList.get(position).getFigure(),
                                            hotProductList.get(position).getCover_price()));
                                    holder.itemView.getContext().startActivity(intent);
                                }
                            });
                }
            });
            TypeChildRecycleAdapter childRecycleAdapter = new TypeChildRecycleAdapter(mContext, (ArrayList<TypeBean.ResultBean.ChildBean>) resultBeans.get(position).getChild());
            rv_ordinary_right.setAdapter(childRecycleAdapter);
        }

    }
}
