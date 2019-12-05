package com.example.shopmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.HomepageBean;

import java.util.List;

/**
 * Created by Administrator on 2016/9/28.
 */
public class ChannelAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomepageBean.ResultBean.ChannelInfoBean> channel_info;

    public ChannelAdapter(Context mContext, List<HomepageBean.ResultBean.ChannelInfoBean> channel_info) {
        this.mContext = mContext;
        this.channel_info = channel_info;
    }


    @Override
    public int getCount() {
        return channel_info == null ? 0 : channel_info.size();
    }

    @Override
    public Object getItem(int position) {
        return channel_info.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_channel, null);
            viewHolder = new ViewHolder();
            viewHolder.ivChannel = convertView.findViewById(R.id.iv_channel);
            viewHolder.tvChannel = convertView.findViewById(R.id.tv_channel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HomepageBean.ResultBean.ChannelInfoBean channelInfoBean = channel_info.get(position);
        viewHolder.tvChannel.setText(channelInfoBean.getChannel_name());
        Glide.with(mContext)
                .load(Constant.BASE_URL_IMAGE +channelInfoBean.getImage())
                .into(viewHolder.ivChannel);
        return convertView;
    }

    class ViewHolder {
        ImageView ivChannel;
        TextView tvChannel;
    }
}
