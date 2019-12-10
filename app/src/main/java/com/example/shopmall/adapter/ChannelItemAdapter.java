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
import com.example.framework.bean.HomepageBean;
import com.example.net.Constant;
import com.example.shopmall.R;

import java.util.List;

public class ChannelItemAdapter extends RecyclerView.Adapter<ChannelItemAdapter.ViewHolder> {

    private Context context;
    private List<HomepageBean.ResultBean.ChannelInfoBean> channel_info_bean;

    public ChannelItemAdapter(Context context, List<HomepageBean.ResultBean.ChannelInfoBean> channel_info_bean) {
        this.context = context;
        this.channel_info_bean = channel_info_bean;
    }

    @NonNull
    @Override
    public ChannelItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_channel_inflate, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChannelItemAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(Constant.BASE_URL_IMAGE + channel_info_bean.get(position).getImage()).into(holder.iv_channel);
        holder.tv_channel.setText(channel_info_bean.get(position).getChannel_name());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeliest.getLikeliest(position);
            }
        });

    }

    Likeliest likeliest;

    interface Likeliest{
        public void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

    @Override
    public int getItemCount() {
        return channel_info_bean.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_channel;
        private TextView tv_channel;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_channel = itemView.findViewById(R.id.iv_channel);
            tv_channel = itemView.findViewById(R.id.tv_channel);

        }
    }
}
