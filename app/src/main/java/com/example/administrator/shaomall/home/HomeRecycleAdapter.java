package com.example.administrator.shaomall.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.home.adapter.ChannelAdapter;
import com.example.net.AppNetConfig;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class HomeRecycleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private HomeBean.ResultBean data;
    private Context context;
    private final int BANNER = 0;
    private final int CHANNEL = 1;
    private final int ACT = 2;
    private final int SECKILL = 3;
    private final int RECOMMEND = 4;
    private final int HOT = 5;
    private int currentType = BANNER;

    public HomeRecycleAdapter(HomeBean.ResultBean data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i == BANNER)
            return new BannerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_banner,null));
        else if (i == CHANNEL)
            return new ChannelViewHolder(LayoutInflater.from(context).inflate(R.layout.item_channel,null));
        else if (i == ACT)
            return new ActViewHolder(LayoutInflater.from(context).inflate(R.layout.item_act,null));

        return null;
    }

    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case BANNER:
                currentType = BANNER;
                break;
            case CHANNEL:
                currentType = CHANNEL;
                break;
            case ACT:
                currentType = ACT;
                break;
            case SECKILL:
                currentType = SECKILL;
                break;
            case RECOMMEND:
                currentType = RECOMMEND;
                break;
            case HOT:
                currentType = HOT;
                break;
        }
        return currentType;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        switch (getItemViewType(i)){
            case BANNER :
                BannerViewHolder bannerViewHolder = (BannerViewHolder) viewHolder;
                bannerViewHolder.setData();
                break;
            case CHANNEL :
                ChannelViewHolder channelViewHolder = (ChannelViewHolder) viewHolder;
                channelViewHolder.setData();
                break;
            case ACT :
                ActViewHolder actViewHolder = (ActViewHolder) viewHolder;
                actViewHolder.setData();
                break;

        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    private class BannerViewHolder extends RecyclerView.ViewHolder {
        private Banner banner;
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            banner = itemView.findViewById(R.id.home_item_banner);
        }
        public void setData(){
            final List<String> images = new ArrayList<>();
            for (int i = 0; i < data.getBanner_info().size(); i++) {
                images.add(AppNetConfig.BASE_URl_IMAGE + data.getBanner_info().get(i).getImage());
            }
            banner.setImages(images);
            banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Glide.with(context).load((String) path).apply(RequestOptions.bitmapTransform(new RoundedCorners(30))).into(imageView);
                }
            });
            banner.start();
        }
    }

    private class ChannelViewHolder extends RecyclerView.ViewHolder {
        private GridView gridView;
        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            gridView = itemView.findViewById(R.id.home_channel_gv);
        }
        public void setData(){
            gridView.setAdapter(new ChannelAdapter(data.getChannel_info()));
        }
    }

    class ActViewHolder extends RecyclerView.ViewHolder{
        private ViewPager viewPager;
        public ActViewHolder(@NonNull View itemView) {
            super(itemView);
            viewPager = itemView.findViewById(R.id.home_act_vp);
        }
        public void setData(){
            viewPager.setPageMargin(20);
            viewPager.setOffscreenPageLimit(3);
//            viewPager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));

            viewPager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return data.getAct_info().size();
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
                    Glide.with(context)
                            .load(AppNetConfig.BASE_URl_IMAGE + data.getAct_info().get(position).getIcon_url())
                            .into(view);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });

        }
    }
}
