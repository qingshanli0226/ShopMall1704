package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.framework.base.BaseAdapter;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.activity.GoodsInfoActivity;
import com.example.shopmall.bean.GoodsBean;
import com.example.shopmall.bean.GoodsListBean;

import java.util.List;

/**
 * GoodsList适配器
 */
public class GoodsListAdapter extends BaseAdapter<GoodsListBean.ResultBean.PageDataBean,GoodsListAdapter.ViewHolder> {

    private Context mContext;

    public GoodsListAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_goods_list_inflate;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, final List<GoodsListBean.ResultBean.PageDataBean> pageDataBeans, final int position) {

        holder.setData(pageDataBeans,position);

    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivGoodsListFigure;
        private TextView tvGoodsListName;
        private TextView tvGoodsListCoverPrice;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGoodsListFigure = itemView.findViewById(R.id.iv_goods_list_figure);
            tvGoodsListName = itemView.findViewById(R.id.tv_goods_list_name);
            tvGoodsListCoverPrice = itemView.findViewById(R.id.tv_goods_list_cover_price);

        }

        @SuppressLint("SetTextI18n")
        private void setData(final List<GoodsListBean.ResultBean.PageDataBean> pageDataBeans, final int position) {
            Glide.with(mContext).load(Constant.BASE_URL_IMAGE + pageDataBeans.get(position).getFigure()).into(ivGoodsListFigure);
            tvGoodsListName.setText(pageDataBeans.get(position).getName());
            tvGoodsListCoverPrice.setText("￥" + pageDataBeans.get(position).getOrigin_price());

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String cover_price = pageDataBeans.get(position).getCover_price();
                    String name = pageDataBeans.get(position).getName();
                    String figure = pageDataBeans.get(position).getFigure();
                    String product_id = pageDataBeans.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra("goods_bean",goodsBean);
                    mContext.startActivity(intent);
                }
            });

        }
    }
}