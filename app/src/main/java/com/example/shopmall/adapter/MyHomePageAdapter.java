package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.activity.GoodsInfoActivity;
import com.example.shopmall.activity.GoodsListActivity;
import com.example.shopmall.bean.GoodsBean;
import com.example.shopmall.bean.HomepageBean;
import com.example.shopmall.utils.AlphaPageTransformer;
import com.example.shopmall.utils.ScaleInTransformer;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import com.example.framework.base.BaseAdapter;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MyHomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String GOODS_BEAN = "goods_bean";

    //上下文
    private Context mContext;

    //数据Bean对象
    private HomepageBean homepageBean;

    //五种类型
    //横幅广告
    public static final int BANNER = 0;
    //频道
    public static final int CHANNEL = 1;
    //活动
    public static final int ACT = 2;
    //秒杀
    public static final int SECKILL = 3;
    //推荐
    public static final int RECOMMEND = 4;
    //热卖
    public static final int HOT = 5;

    //当前类型
    public int currentTyper = BANNER;
    private final LayoutInflater mLayoutInflater;

    public MyHomePageAdapter(Context mContext, HomepageBean homepageBean) {
        this.mContext = mContext;
        this.homepageBean = homepageBean;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
            case BANNER:
                currentTyper = BANNER;
                break;
            case CHANNEL:
                currentTyper = CHANNEL;
                break;
            case ACT:
                currentTyper = ACT;
                break;
            case SECKILL:
                currentTyper = SECKILL;
                break;
            case RECOMMEND:
                currentTyper = RECOMMEND;
                break;
            case HOT:
                currentTyper = HOT;
                break;
        }
        return currentTyper;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER){
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.layout_banner,parent,false),mContext,homepageBean);
        }else if (viewType == CHANNEL){
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.channel_item,parent,false),mContext);
        }else if (viewType == ACT){
            return new ActViewHolder(mLayoutInflater.inflate(R.layout.act_item,parent,false),mContext);
        }else if (viewType == SECKILL){
            return new SeckillViewHolder(mLayoutInflater.inflate(R.layout.seckill_item,parent,false),mContext);
        }else if (viewType == RECOMMEND){
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.recommend_item,parent,false),mContext);
        }else if (viewType == HOT){
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.hot_item,parent,false),mContext);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(homepageBean.getResult().getBanner_info());
        }else if (getItemViewType(position) == CHANNEL){
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(homepageBean.getResult().getChannel_info());
        }else if (getItemViewType(position) == ACT){
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(homepageBean.getResult().getAct_info());
        }else if (getItemViewType(position) == SECKILL){
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(homepageBean.getResult().getSeckill_info());
        }else if (getItemViewType(position) == RECOMMEND){
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(homepageBean.getResult().getRecommend_info());
        }else if (getItemViewType(position) == HOT){
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(homepageBean.getResult().getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    //Banner
    class BannerViewHolder extends RecyclerView.ViewHolder{

        public Banner banner;
        public Context mContext;
        public HomepageBean homepageBean;

        public BannerViewHolder(@NonNull View itemView, Context mContext, HomepageBean homepageBean) {
            super(itemView);

            banner = itemView.findViewById(R.id.bn_ner);
            this.mContext = mContext;
            this.homepageBean = homepageBean;

        }

        public void setData(final List<HomepageBean.ResultBean.BannerInfoBean> banner_info) {
            banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            ArrayList<String> imageUris = new ArrayList<>();
            for (int i = 0; i < banner_info.size(); i++) {
                imageUris.add(Constant.BASE_URL_IMAGE + banner_info.get(i).getImage());
            }
            banner.setImageLoader(new ImageLoad());
            banner.setImages(imageUris);
            banner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (position - 1 < banner_info.size()){
                        int option = banner_info.get(position - 1).getOption();
                        String product_id = "";
                        String name = "";
                        String cover_price = "";
                        if (position -1 == 0){
                            product_id = "627";
                            name = "尚硅谷  在线课堂";
                            cover_price = "50";
                        }else if (position - 1 == 1){
                            product_id = "21";
                            name = "抱歉，没座了!";
                            cover_price = "8.00";
                        }else {
                            product_id = "";
                            name = "撒贝宁主持，史上最\"高大上\"开学典礼";
                            cover_price = "80";
                        }
                    }
                }
            });
            banner.start();
        }
    }

    class ImageLoad extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(mContext).load((String) path).into(imageView);
        }
    }

    //GridView
    class ChannelViewHolder extends RecyclerView.ViewHolder {

        public GridView gridView;

        public ChannelViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);

            gridView = itemView.findViewById(R.id.gv_channel);

        }

        public void setData(List<HomepageBean.ResultBean.ChannelInfoBean> channel_info) {
            ChannelAdapter channelAdapter = new ChannelAdapter(mContext,channel_info);
            gridView.setAdapter(channelAdapter);

            //点击事件
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    if (position <= 8) {
                        Intent intent = new Intent(mContext, GoodsListActivity.class);
                        intent.putExtra("position", position);
                        mContext.startActivity(intent);
                    }
                }
            });

        }
    }

    //Act
    class ActViewHolder extends RecyclerView.ViewHolder {

        public ViewPager act_viewpager;
        public Context mContext;

        public ActViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);

            act_viewpager = itemView.findViewById(R.id.act_viewpager);
            this.mContext = mContext;

        }

        public void setData(final List<HomepageBean.ResultBean.ActInfoBean> act_info) {
            act_viewpager.setPageMargin(20);
            act_viewpager.setOffscreenPageLimit(3);
            act_viewpager.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));

            act_viewpager.setAdapter(new PagerAdapter() {
                @Override
                public int getCount() {
                    return act_info.size();
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
                    Glide.with(mContext).load(Constant.BASE_URL_IMAGE + act_info.get(position).getIcon_url()).into(view);
                    container.addView(view);
                    return view;
                }

                @Override
                public void destroyItem(ViewGroup container, int position, Object object) {
                    container.removeView((View) object);
                }
            });

            //点击事件
            act_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
//                    Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    private TextView tv_time_seckill;
    private boolean isFirst = true;
    private int dt;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                dt = dt - 1000;
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                tv_time_seckill.setText(sd.format(new Date(dt)));

                handler.removeMessages(0);
                handler.sendEmptyMessageDelayed(0, 1000);
                if (dt == 0) {
                    handler.removeMessages(0);
                }
            }

        }
    };

    //Seckill
    class SeckillViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_more_seckill;
        public RecyclerView rv_seckill;
        public Context mContext;

        public SeckillViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);
            rv_seckill = itemView.findViewById(R.id.rv_seckill);
            tv_time_seckill = (TextView) itemView.findViewById(R.id.tv_time_seckill);
            tv_more_seckill = (TextView) itemView.findViewById(R.id.tv_more_seckill);
            this.mContext = mContext;
        }

        public void setData(final HomepageBean.ResultBean.SeckillInfoBean seckill_info) {
            //设置时间
            if (isFirst) {
                dt = (int) (Integer.parseInt(seckill_info.getEnd_time()) - (Integer.parseInt(seckill_info.getStart_time())));
                isFirst = false;
            }

            //设置RecyclerView
            rv_seckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            SeckillRecyclerViewAdapter adapter = new SeckillRecyclerViewAdapter(mContext, seckill_info);
            rv_seckill.setAdapter(adapter);

            //倒计时
            handler.sendEmptyMessageDelayed(0, 1000);

            //点击事件
            adapter.setOnSeckillRecyclerView(new SeckillRecyclerViewAdapter.OnSeckillRecyclerView() {
                @Override
                public void onClick(int position) {
                    HomepageBean.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info.getList().get(position);
                    String name = listBean.getName();
                    String cover_price = listBean.getCover_price();
                    String figure = listBean.getFigure();
                    String product_id = listBean.getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);

                    // Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    //Recommend
    class RecommendViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_more_recommend;
        public GridView gv_recommend;
        public Context mContext;

        public RecommendViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);

            tv_more_recommend = itemView.findViewById(R.id.tv_more_recommend);
            gv_recommend = itemView.findViewById(R.id.gv_recommend);
            this.mContext = mContext;

        }

        public void setData(final List<HomepageBean.ResultBean.RecommendInfoBean> recommend_info) {
            RecommendGridViewAdapter adapter = new RecommendGridViewAdapter(mContext, recommend_info);
            gv_recommend.setAdapter(adapter);

            //点击事件
            gv_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
                    String cover_price = recommend_info.get(position).getCover_price();
                    String name = recommend_info.get(position).getName();
                    String figure = recommend_info.get(position).getFigure();
                    String product_id = recommend_info.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    //Hot
    class HotViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_more_hot;
//        public GridView gv_hot;
        public RecyclerView rv_hot;
        public Context mContext;

        public HotViewHolder(@NonNull View itemView, Context mContext) {
            super(itemView);
            tv_more_hot = itemView.findViewById(R.id.tv_more_hot);
//            gv_hot = itemView.findViewById(R.id.gv_hot);
            rv_hot = itemView.findViewById(R.id.rv_hot);
            rv_hot.setLayoutManager(new GridLayoutManager(mContext,2));
            this.mContext = mContext;

        }

        public void setData(final List<HomepageBean.ResultBean.HotInfoBean> hot_info) {
//            HotGridViewAdapter adapter = new HotGridViewAdapter(mContext, hot_info);
//            gv_hot.setAdapter(adapter);

            HotRecyclerAdapter hotRecyclerAdapter = new HotRecyclerAdapter(mContext, hot_info);
            rv_hot.setAdapter(hotRecyclerAdapter);

            hotRecyclerAdapter.setListelist(new HotRecyclerAdapter.Likeliest() {
                @Override
                public void getListelist(HotRecyclerAdapter.ViewHolder holder, int position) {
                    String cover_price = hot_info.get(position).getCover_price();
                    String name = hot_info.get(position).getName();
                    String figure = hot_info.get(position).getFigure();
                    String product_id = hot_info.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);

                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });

            //点击事件
//            gv_hot.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                    // Toast.makeText(mContext, "position:" + position, Toast.LENGTH_SHORT).show();
//                    String cover_price = hot_info.get(position).getCover_price();
//                    String name = hot_info.get(position).getName();
//                    String figure = hot_info.get(position).getFigure();
//                    String product_id = hot_info.get(position).getProduct_id();
//                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
//
//                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
//                    intent.putExtra(GOODS_BEAN, goodsBean);
//                    mContext.startActivity(intent);
//                }
//            });
        }
    }

}
