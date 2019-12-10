package com.example.shopmall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.framework.bean.HomepageBean;

import java.util.List;

public class HotRecyclerAdapter extends RecyclerView.Adapter<HotRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<HomepageBean.ResultBean.HotInfoBean> hotInfoBeans;

    public HotRecyclerAdapter(Context context, List<HomepageBean.ResultBean.HotInfoBean> hotInfoBeans) {
        this.context = context;
        this.hotInfoBeans = hotInfoBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hot_grid_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        Glide.with(context).load(Constant.BASE_URL_IMAGE + hotInfoBeans.get(position).getFigure()).into(holder.iv_hot);
        holder.tv_name.setText(hotInfoBeans.get(position).getName());
        holder.tv_price.setText("￥" + hotInfoBeans.get(position).getCover_price());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listelist.getListelist(holder,position);
            }
        });

    }

    Likeliest listelist;

    interface Likeliest {
        public void getListelist(ViewHolder holder, int position);
    }

    public void setListelist(Likeliest listelist) {
        this.listelist = listelist;
    }

    @Override
    public int getItemCount() {
        return hotInfoBeans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView iv_hot;
        public TextView tv_name;
        public TextView tv_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_hot = itemView.findViewById(R.id.iv_hot);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_price = itemView.findViewById(R.id.tv_price);

        }
    }
}
