package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.framework.base.BaseAdapter;
import com.example.framework.bean.HomepageBean;
import com.example.net.Constant;
import com.example.shopmall.R;

import java.util.List;

/**
 * Seckill适配器
 */
public class SeckillItemAdapter extends BaseAdapter<HomepageBean.ResultBean.SeckillInfoBean.ListBean,SeckillItemAdapter.ViewHolder> {

    private Context mContext;

    public SeckillItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected SeckillItemAdapter.ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_seckill_inflate;
    }

    @Override
    protected void onBindHolder(SeckillItemAdapter.ViewHolder holder, List<HomepageBean.ResultBean.SeckillInfoBean.ListBean> listBeans, final int position) {

        holder.setData(listBeans,position);

    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivSeckillInflateFigure;
        private TextView tvSeckillInflateCoverPrice;
        private TextView tvSeckillInflateOriginPrice;

        private ViewHolder(View itemView) {
            super(itemView);
            ivSeckillInflateFigure = itemView.findViewById(R.id.iv_seckill_inflate_figure);
            tvSeckillInflateCoverPrice = itemView.findViewById(R.id.tv_seckill_inflate_cover_price);
            tvSeckillInflateOriginPrice = itemView.findViewById(R.id.tv_seckill_inflate_origin_price);
        }

        @SuppressLint("SetTextI18n")
        private void setData(List<HomepageBean.ResultBean.SeckillInfoBean.ListBean> listBeans, final int position) {
            HomepageBean.ResultBean.SeckillInfoBean.ListBean listBean = listBeans.get(position);
            tvSeckillInflateCoverPrice.setText("￥" + listBean.getCover_price());
            tvSeckillInflateOriginPrice.setText("￥" + listBean.getOrigin_price());
            Glide.with(mContext).load(Constant.BASE_URL_IMAGE +listBean.getFigure()).into(ivSeckillInflateFigure);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    likeliest.getLikeliest(position);
                }
            });
        }
    }

    private Likeliest likeliest;

    interface Likeliest{
         void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

}
