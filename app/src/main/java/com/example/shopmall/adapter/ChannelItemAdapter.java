package com.example.shopmall.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.framework.base.BaseAdapter;
import com.example.framework.bean.HomepageBean;
import com.example.net.Constant;
import com.example.shopmall.R;

import java.util.List;

/**
 * Channel适配器
 */
public class ChannelItemAdapter extends BaseAdapter<HomepageBean.ResultBean.ChannelInfoBean,ChannelItemAdapter.ViewHolder> {

    private final Context mContext;

    public ChannelItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected ViewHolder getViewHolder(View view, int viewType) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayout(int viewType) {
        return R.layout.item_channel_inflate;
    }

    @Override
    protected void onBindHolder(final ViewHolder holder, List<HomepageBean.ResultBean.ChannelInfoBean> channelInfoBeans, final int position) {

        holder.setData(channelInfoBeans,position);

    }

    @Override
    protected int getViewType(int position) {
        return position;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivChannel;
        private TextView tvChannel;

        private ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivChannel = itemView.findViewById(R.id.iv_channel);
            tvChannel = itemView.findViewById(R.id.tv_channel);

        }

        private void setData(List<HomepageBean.ResultBean.ChannelInfoBean> channel_info_bean, final int position) {
            Glide.with(mContext).load(Constant.BASE_URL_IMAGE + channel_info_bean.get(position).getImage()).into(ivChannel);
            tvChannel.setText(channel_info_bean.get(position).getChannel_name());

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
        void getLikeliest(int position);
    }

    public void setLikeliest(Likeliest likeliest) {
        this.likeliest = likeliest;
    }

}
