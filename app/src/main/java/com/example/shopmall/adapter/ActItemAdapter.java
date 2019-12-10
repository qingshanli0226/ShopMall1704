package com.example.shopmall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.example.net.Constant;
import com.example.shopmall.bean.HomepageBean;

import java.util.ArrayList;
import java.util.List;

public class ActItemAdapter extends PagerAdapter {

    private Context context;
    private List<HomepageBean.ResultBean.ActInfoBean> act_info_bean;

    public ActItemAdapter(Context context, List<HomepageBean.ResultBean.ActInfoBean> act_info_bean) {
        this.context = context;
        this.act_info_bean = act_info_bean;
    }

    @Override
    public int getCount() {
        return act_info_bean.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView view = new ImageView(context);
        view.setScaleType(ImageView.ScaleType.FIT_XY);
        //绑定数据
        Glide.with(context).load(Constant.BASE_URL_IMAGE + act_info_bean.get(position).getIcon_url()).into(view);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

}
