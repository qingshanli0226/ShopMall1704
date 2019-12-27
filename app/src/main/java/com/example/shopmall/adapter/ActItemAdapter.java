package com.example.shopmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.framework.bean.HomepageBean;
import com.example.net.Constant;

import java.util.List;

/**
 * Act适配器
 */
 class ActItemAdapter extends PagerAdapter {

    private final Context mContext;
    private final List<HomepageBean.ResultBean.ActInfoBean> actInfoBeans;

    public ActItemAdapter(Context mContext, List<HomepageBean.ResultBean.ActInfoBean> actInfoBeans) {
        this.mContext = mContext;
        this.actInfoBeans = actInfoBeans;
    }

    @Override
    public int getCount() {
        return actInfoBeans.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(mContext);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        //绑定数据
        Glide.with(mContext).load(Constant.BASE_URL_IMAGE + actInfoBeans.get(position).getIcon_url()).into(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
