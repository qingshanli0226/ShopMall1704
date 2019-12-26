package com.example.buy.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.buy.R;
import com.example.buy.bean.PayGoodsBean;
import com.example.framework.base.BaseAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class MyShoppingSendAdapter extends BaseAdapter<PayGoodsBean.ResultBean, MyShoppingSendAdapter.ViewHolder> {

    private Context context;

    public MyShoppingSendAdapter(Context context) {
        this.context = context;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_shop_send;
    }

    @Override
    protected void onBindHolder(ViewHolder holder, List<PayGoodsBean.ResultBean> data, final int position) {
        PayGoodsBean.ResultBean bean = data.get(position);
        holder.tvTitle.setText(bean.getTradeNo());
        holder.tvPrice.setText(bean.getTotalPrice());
        String time = bean.getTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        long l = Long.parseLong(time);
        String format = simpleDateFormat.format(new Date(l));
        holder.tvTime.setText(format);
        holder.llItemlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onItemClickListener!=null){
                    onItemClickListener.OnItemClick(position);
                }
            }
        });
    }

    @Override
    protected int getViewType(int position) {
        return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        TextView tvTitle = itemView.findViewById(R.id.tv_buy_title);
        TextView tvPrice = itemView.findViewById(R.id.tv_buy_price);
        TextView tvTime = itemView.findViewById(R.id.tv_buy_time);
        LinearLayout llItemlayout = itemView.findViewById(R.id.ll_buy_itemlayout);
    }

    private OnItemClickListener onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        void OnItemClick(int position);
    }
}
