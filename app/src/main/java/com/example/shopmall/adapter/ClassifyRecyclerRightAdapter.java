package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.framework.base.BaseAdapter;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.ClassifyBean;

import java.util.List;

/**
 * 热卖推荐适配器
 */
public class ClassifyRecyclerRightAdapter extends BaseAdapter<ClassifyBean.ResultBean.HotProductListBean,ClassifyRecyclerRightAdapter.ViewHolder> {

    private Context mContext;

    public ClassifyRecyclerRightAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected ClassifyRecyclerRightAdapter.ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_ordinary_right;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindHolder(ClassifyRecyclerRightAdapter.ViewHolder holder, List<ClassifyBean.ResultBean.HotProductListBean> hot_product_list_bean, final int position) {

        holder.setData(hot_product_list_bean,position);
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivOrdinaryRight;
        private TextView tvOrdinaryRight;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivOrdinaryRight = itemView.findViewById(R.id.iv_ordinary_right);
            tvOrdinaryRight = itemView.findViewById(R.id.tv_ordinary_right);

        }

        public void setData(List<ClassifyBean.ResultBean.HotProductListBean> hot_product_list_bean, final int position) {
            Glide.with(mContext).load(Constant.BASE_URL_IMAGE + hot_product_list_bean.get(position).getFigure()).into(ivOrdinaryRight);
            tvOrdinaryRight.setText( "￥" + hot_product_list_bean.get(position).getCover_price());
            tvOrdinaryRight.setTextColor(Color.RED);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeliest.getLikeliest(position);
                }
            });
        }
    }

    private Likeliest likeliest;

    interface Likeliest {
        public void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

}
