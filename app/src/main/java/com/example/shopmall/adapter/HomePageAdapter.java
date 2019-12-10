package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomePageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final String GOODS_BEAN = "goods_bean";

    //上下文
    private Context context;

    //数据Bean对象
    private HomepageBean homepage_bean;

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
    public int currentType = BANNER;
    private final LayoutInflater mLayoutInflater;

    public HomePageAdapter(Context context, HomepageBean homepage_bean) {
        this.context = context;
        this.homepage_bean = homepage_bean;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        switch (position){
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

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER){
            return new BannerViewHolder(mLayoutInflater.inflate(R.layout.layout_banner,parent,false));
        }else if (viewType == CHANNEL){
            return new ChannelViewHolder(mLayoutInflater.inflate(R.layout.item_channel,parent,false));
        }else if (viewType == ACT){
            return new ActViewHolder(mLayoutInflater.inflate(R.layout.item_act,parent,false));
        }else if (viewType == SECKILL){
            return new SeckillViewHolder(mLayoutInflater.inflate(R.layout.item_seckill,parent,false));
        }else if (viewType == RECOMMEND){
            return new RecommendViewHolder(mLayoutInflater.inflate(R.layout.item_recommend,parent,false));
        }else if (viewType == HOT){
            return new HotViewHolder(mLayoutInflater.inflate(R.layout.item_hot,parent,false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == BANNER){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(homepage_bean.getResult().getBanner_info());
        }else if (getItemViewType(position) == CHANNEL){
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(homepage_bean.getResult().getChannel_info());
        }else if (getItemViewType(position) == ACT){
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(homepage_bean.getResult().getAct_info());
        }else if (getItemViewType(position) == SECKILL){
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(homepage_bean.getResult().getSeckill_info());
        }else if (getItemViewType(position) == RECOMMEND){
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(homepage_bean.getResult().getRecommend_info());
        }else if (getItemViewType(position) == HOT){
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(homepage_bean.getResult().getHot_info());
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    //Banner
    class BannerViewHolder extends RecyclerView.ViewHolder{

        private Banner bn_ner;

        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);

            bn_ner = itemView.findViewById(R.id.bn_ner);
        }

        public void setData(final List<HomepageBean.ResultBean.BannerInfoBean> banner_info_bean) {
            bn_ner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            ArrayList<String> images = new ArrayList<>();
            for (int i = 0; i < banner_info_bean.size(); i++) {
                images.add(Constant.BASE_URL_IMAGE + banner_info_bean.get(i).getImage());
            }
            bn_ner.setImageLoader(new ImageLoad());
            bn_ner.setImages(images);
            bn_ner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (position - 1 < banner_info_bean.size()){
                        int option = banner_info_bean.get(position - 1).getOption();
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
            bn_ner.start();
        }
    }

    class ImageLoad extends ImageLoader{
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load((String) path).into(imageView);
        }
    }

    //Channel
    class ChannelViewHolder extends RecyclerView.ViewHolder {

        public RecyclerView rv_channel;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);

            rv_channel = itemView.findViewById(R.id.rv_channel);

            rv_channel.setLayoutManager(new GridLayoutManager(context,5));

        }

        public void setData(List<HomepageBean.ResultBean.ChannelInfoBean> channel_info_bean) {
            ChannelItemAdapter channel_item_adapter = new ChannelItemAdapter(context,channel_info_bean);
            rv_channel.setAdapter(channel_item_adapter);

            channel_item_adapter.setLikeliest(new ChannelItemAdapter.Likeliest() {
                @Override
                public void getLikeliest(int position) {
                    Log.d("####", "onItemClick: " + position);
                    if (position <= 8) {
                        Intent intent = new Intent(context, GoodsListActivity.class);
                        intent.putExtra("position", position);
                        context.startActivity(intent);
                    }
                }
            });
        }
    }

    //Act ViewPager切换
    class ActViewHolder extends RecyclerView.ViewHolder {

        public ViewPager vp_act;
        public ActViewHolder(@NonNull View itemView) {
            super(itemView);

            vp_act = itemView.findViewById(R.id.vp_act);
        }

        public void setData(final List<HomepageBean.ResultBean.ActInfoBean> act_info_bean) {
            vp_act.setPageMargin(20);
            vp_act.setOffscreenPageLimit(3);
            vp_act.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));

            ActItemAdapter act_adapter = new ActItemAdapter(context,act_info_bean);

            vp_act.setAdapter(act_adapter);
        }
    }

    private TextView tv_seckill_time;
    private boolean isFirst = true;
    private int dt;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                dt = dt - 1000;
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                tv_seckill_time.setText(sd.format(new Date(dt)));

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

        public TextView tv_seckill_more;
        public RecyclerView rv_seckill;

        public SeckillViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_seckill_time = itemView.findViewById(R.id.tv_seckill_time);
            tv_seckill_more = itemView.findViewById(R.id.tv_seckill_more);
            rv_seckill = itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(final HomepageBean.ResultBean.SeckillInfoBean seckill_info_bean) {
            //设置时间
            if (isFirst) {
                dt = (int) (Integer.parseInt(seckill_info_bean.getEnd_time()) - (Integer.parseInt(seckill_info_bean.getStart_time())));
                isFirst = false;
            }

            //设置RecyclerView
            rv_seckill.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            SeckillItemAdapter seckill_item_adapter = new SeckillItemAdapter(context, seckill_info_bean.getList());
            rv_seckill.setAdapter(seckill_item_adapter);

            //倒计时
            handler.sendEmptyMessageDelayed(0, 1000);

            //点击事件
            seckill_item_adapter.setLikeliest(new SeckillItemAdapter.Likeliest() {
                @Override
                public void getLikeliest(int position) {
                    HomepageBean.ResultBean.SeckillInfoBean.ListBean listBean = seckill_info_bean.getList().get(position);
                    String name = listBean.getName();
                    String cover_price = listBean.getCover_price();
                    String figure = listBean.getFigure();
                    String product_id = listBean.getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    //Recommend
    class RecommendViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_recommend_more;
        private RecyclerView rv_recommend;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_recommend_more = itemView.findViewById(R.id.tv_recommend_more);
            rv_recommend = itemView.findViewById(R.id.rv_recommend);

            rv_recommend.setLayoutManager(new GridLayoutManager(context,3));

        }

        private void setData(final List<HomepageBean.ResultBean.RecommendInfoBean> recommend_info_bean) {
            RecommendItemAdapter recommend_item_adapter = new RecommendItemAdapter(context, recommend_info_bean);
            rv_recommend.setAdapter(recommend_item_adapter);

            //点击事件
            recommend_item_adapter.setLikeliest(new RecommendItemAdapter.Likeliest() {
                @Override
                public void getLikeliest(int position) {
                    String cover_price = recommend_info_bean.get(position).getCover_price();
                    String name = recommend_info_bean.get(position).getName();
                    String figure = recommend_info_bean.get(position).getFigure();
                    String product_id = recommend_info_bean.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

    //Hot
    class HotViewHolder extends RecyclerView.ViewHolder {

        public TextView tv_more_hot;
        public RecyclerView rv_hot;

        public HotViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_more_hot = itemView.findViewById(R.id.tv_more_hot);
            rv_hot = itemView.findViewById(R.id.rv_hot);
            rv_hot.setLayoutManager(new GridLayoutManager(context,2));

        }

        public void setData(final List<HomepageBean.ResultBean.HotInfoBean> hot_info) {
            HotItemAdapter hot_item_adapter = new HotItemAdapter(context, hot_info);
            rv_hot.setAdapter(hot_item_adapter);

            hot_item_adapter.setLikeliest(new HotItemAdapter.Likeliest() {
                @Override
                public void getLikeliest(HotItemAdapter.ViewHolder holder, int position) {
                    String cover_price = hot_info.get(position).getCover_price();
                    String name = hot_info.get(position).getName();
                    String figure = hot_info.get(position).getFigure();
                    String product_id = hot_info.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(context, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    context.startActivity(intent);
                }
            });
        }
    }

}
