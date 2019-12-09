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
 * Created by Administrator on 2016/9/29.
 */
public class RecommendGridViewAdapter extends BaseAdapter {
    private Context mContext;
    private List<HomepageBean.ResultBean.RecommendInfoBean> data;

    public RecommendGridViewAdapter(Context mContext, List<HomepageBean.ResultBean.RecommendInfoBean> data) {
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
            convertView = View.inflate(mContext, R.layout.item_recommend_grid_view, null);
            viewHolder = new ViewHolder();
            viewHolder.ivRecommend = convertView.findViewById(R.id.iv_recommend);
            viewHolder.tv_recommend_name = convertView.findViewById(R.id.tv_recommend_name);
            viewHolder.tv_price = convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HomepageBean.ResultBean.RecommendInfoBean recommendInfoBean = data.get(position);
        Glide.with(mContext)
                .load(Constant.BASE_URL_IMAGE +recommendInfoBean.getFigure())
                .into(viewHolder.ivRecommend);
        viewHolder.tv_recommend_name.setText(recommendInfoBean.getName());
        viewHolder.tv_price.setText("￥" + recommendInfoBean.getCover_price());
        return convertView;
    }

    static class ViewHolder {
        ImageView ivRecommend;
        TextView tv_recommend_name;
        TextView tv_price;
    }
}
