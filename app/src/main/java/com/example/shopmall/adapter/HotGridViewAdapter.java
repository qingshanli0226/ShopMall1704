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
import com.example.framework.bean.HomepageBean;

import java.util.List;


/**
 * Created by Administrator on 2016/10/2.
 */
public class HotGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomepageBean.ResultBean.HotInfoBean> data;

    public HotGridViewAdapter(Context mContext, List<HomepageBean.ResultBean.HotInfoBean> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_hot_grid_view, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_hot = convertView.findViewById(R.id.iv_hot);
            viewHolder.tv_name = convertView.findViewById(R.id.tv_hot_grid_name);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Glide.with(mContext).load(Constant.BASE_URL_IMAGE +data.get(position).getFigure()).into(viewHolder.iv_hot);
        viewHolder.tv_name.setText(data.get(position).getName());
        viewHolder.tv_price.setText("￥" + data.get(position).getCover_price());
        return convertView;
    }

    class ViewHolder {
        ImageView iv_hot;
        TextView tv_name;
        TextView tv_price;
    }
}
