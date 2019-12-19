package com.example.shopmall.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.framework.base.BaseAdapter;
import com.example.framework.bean.HomepageBean;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.activity.GoodsInfoActivity;
import com.example.shopmall.activity.GoodsListActivity;
import com.example.shopmall.bean.GoodsBean;
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

/**
 * 首页多布局适配器
 */
public class HomePageAdapter extends BaseAdapter<HomepageBean.ResultBean,RecyclerView.ViewHolder> {

    private static final String GOODS_BEAN = "goods_bean";

    //上下文
    private Context mContext;

    //五种类型
    //横幅广告
    private static final int BANNER = 0;
    //频道
    private static final int CHANNEL = 1;
    //活动
    private static final int ACT = 2;
    //秒杀
    private static final int SECKILL = 3;
    //推荐
    private static final int RECOMMEND = 4;
    //热卖
    private static final int HOT = 5;

    //当前类型
    private int currentType = BANNER;

    public HomePageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view, int viewType) {
        if (viewType == BANNER){
            return new BannerViewHolder(view);
        }else if (viewType == CHANNEL){
            return new ChannelViewHolder(view);
        }else if (viewType == ACT){
            return new ActViewHolder(view);
        }else if (viewType == SECKILL){
            return new SeckillViewHolder(view);
        }else if (viewType == RECOMMEND){
            return new RecommendViewHolder(view);
        }else if (viewType == HOT){
            return new HotViewHolder(view);
        }

        return null;

    }

    @Override
    protected int getLayout(int viewType) {
        if (viewType == BANNER){
            return R.layout.item_banner;
        }else if (viewType == CHANNEL){
            return R.layout.item_channel;
        }else if (viewType == ACT){
            return R.layout.item_act;
        }else if (viewType == SECKILL){
            return R.layout.item_seckill;
        }else if (viewType == RECOMMEND){
            return R.layout.item_recommend;
        }else if (viewType == HOT){
            return R.layout.item_hot;
        }
        return 0;
    }

    @Override
    protected void onBindHolder(RecyclerView.ViewHolder holder, List<HomepageBean.ResultBean> resultBeans, int position) {
        HomepageBean.ResultBean resultBean = resultBeans.get(0);
        if (getViewType(position) == BANNER){
            BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            bannerViewHolder.setData(resultBean.getBanner_info());
        }else if (getViewType(position) == CHANNEL){
            ChannelViewHolder channelViewHolder = (ChannelViewHolder) holder;
            channelViewHolder.setData(resultBean.getChannel_info());
        }else if (getViewType(position) == ACT){
            ActViewHolder actViewHolder = (ActViewHolder) holder;
            actViewHolder.setData(resultBean.getAct_info());
        }else if (getViewType(position) == SECKILL){
            SeckillViewHolder seckillViewHolder = (SeckillViewHolder) holder;
            seckillViewHolder.setData(resultBean.getSeckill_info());
        }else if (getViewType(position) == RECOMMEND){
            RecommendViewHolder recommendViewHolder = (RecommendViewHolder) holder;
            recommendViewHolder.setData(resultBean.getRecommend_info());
        }else if (getViewType(position) == HOT){
            HotViewHolder hotViewHolder = (HotViewHolder) holder;
            hotViewHolder.setData(resultBean.getHot_info());
        }
    }

    @Override
    protected int getViewType(int position) {
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

        public void setData(final List<HomepageBean.ResultBean.BannerInfoBean> bannerInfoBeans) {
            bn_ner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR);
            ArrayList<String> images = new ArrayList<>();
            for (int i = 0; i < bannerInfoBeans.size(); i++) {
                images.add(Constant.BASE_URL_IMAGE + bannerInfoBeans.get(i).getImage());
            }
            bn_ner.setImageLoader(new ImageLoad());
            bn_ner.setImages(images);
            bn_ner.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    if (position - 1 < bannerInfoBeans.size()){
                        int option = bannerInfoBeans.get(position - 1).getOption();
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

        private RecyclerView rv_channel;

        public ChannelViewHolder(@NonNull View itemView) {
            super(itemView);

            rv_channel = itemView.findViewById(R.id.rv_channel);

            rv_channel.setLayoutManager(new GridLayoutManager(mContext,5));

        }

        public void setData(List<HomepageBean.ResultBean.ChannelInfoBean> channelInfoBeans) {
            ChannelItemAdapter channel_item_adapter = new ChannelItemAdapter(mContext);
            channel_item_adapter.reFresh(channelInfoBeans);
            rv_channel.setAdapter(channel_item_adapter);

            channel_item_adapter.setLikeliest(new ChannelItemAdapter.Likeliest() {
                @Override
                public void getLikeliest(int position) {
                    if (position <= 8) {
                        Intent intent = new Intent(mContext, GoodsListActivity.class);
                        intent.putExtra("position", position);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

    //Act ViewPager切换
    class ActViewHolder extends RecyclerView.ViewHolder {

        private ViewPager vp_act;

        public ActViewHolder(@NonNull View itemView) {
            super(itemView);

            vp_act = itemView.findViewById(R.id.vp_act);
        }

        public void setData(final List<HomepageBean.ResultBean.ActInfoBean> actInfoBeans) {
            vp_act.setPageMargin(20);
            vp_act.setOffscreenPageLimit(3);
            vp_act.setPageTransformer(true, new AlphaPageTransformer(new ScaleInTransformer()));

            ActItemAdapter act_adapter = new ActItemAdapter(mContext,actInfoBeans);

            vp_act.setAdapter(act_adapter);
        }
    }

    private TextView tvSeckillTime;
    private boolean isFirst = true;
    private int dt;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                dt = dt - 1000;
                SimpleDateFormat sd = new SimpleDateFormat("HH:mm:ss");
                tvSeckillTime.setText(sd.format(new Date(dt)));

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

        private TextView tvSeckillMore;
        private RecyclerView rvSeckill;

        public SeckillViewHolder(@NonNull View itemView) {
            super(itemView);

            tvSeckillTime = itemView.findViewById(R.id.tv_seckill_time);
            tvSeckillMore = itemView.findViewById(R.id.tv_seckill_more);
            rvSeckill = itemView.findViewById(R.id.rv_seckill);
        }

        public void setData(final HomepageBean.ResultBean.SeckillInfoBean seckillInfoBean) {
            //设置时间
            if (isFirst) {
                dt = (int) (Integer.parseInt(seckillInfoBean.getEnd_time()) - (Integer.parseInt(seckillInfoBean.getStart_time())));
                isFirst = false;
            }

            //设置RecyclerView
            rvSeckill.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
            SeckillItemAdapter seckill_item_adapter = new SeckillItemAdapter(mContext);
            seckill_item_adapter.reFresh(seckillInfoBean.getList());
            rvSeckill.setAdapter(seckill_item_adapter);

            //倒计时
            handler.sendEmptyMessageDelayed(0, 1000);

            //点击事件
            seckill_item_adapter.setLikeliest(new SeckillItemAdapter.Likeliest() {
                @Override
                public void getLikeliest(int position) {
                    HomepageBean.ResultBean.SeckillInfoBean.ListBean listBean = seckillInfoBean.getList().get(position);
                    String name = listBean.getName();
                    String cover_price = listBean.getCover_price();
                    String figure = listBean.getFigure();
                    String product_id = listBean.getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    //Recommend
    class RecommendViewHolder extends RecyclerView.ViewHolder {

        private TextView tvRecommendMore;
        private RecyclerView rvRecommend;

        public RecommendViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecommendMore = itemView.findViewById(R.id.tv_recommend_more);
            rvRecommend = itemView.findViewById(R.id.rv_recommend);

            rvRecommend.setLayoutManager(new GridLayoutManager(mContext,3));

        }

        private void setData(final List<HomepageBean.ResultBean.RecommendInfoBean> recommendInfoBeans) {
            RecommendItemAdapter recommend_item_adapter = new RecommendItemAdapter(mContext);
            recommend_item_adapter.reFresh(recommendInfoBeans);
            rvRecommend.setAdapter(recommend_item_adapter);

            //点击事件
            recommend_item_adapter.setLikeliest(new RecommendItemAdapter.Likeliest() {
                @Override
                public void getLikeliest(int position) {
                    String cover_price = recommendInfoBeans.get(position).getCover_price();
                    String name = recommendInfoBeans.get(position).getName();
                    String figure = recommendInfoBeans.get(position).getFigure();
                    String product_id = recommendInfoBeans.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }

    //Hot
    class HotViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMoreHot;
        private RecyclerView rvHot;

        public HotViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMoreHot = itemView.findViewById(R.id.tv_more_hot);
            rvHot = itemView.findViewById(R.id.rv_hot);
            rvHot.setLayoutManager(new GridLayoutManager(mContext,2));

        }

        public void setData(final List<HomepageBean.ResultBean.HotInfoBean> hotInfoBeans) {
            HotItemAdapter hotItemAdapter = new HotItemAdapter(mContext);
            hotItemAdapter.reFresh(hotInfoBeans);
            rvHot.setAdapter(hotItemAdapter);

            hotItemAdapter.setLikeliest(new HotItemAdapter.Likeliest() {
                @Override
                public void getLikeliest(int position) {
                    String cover_price = hotInfoBeans.get(position).getCover_price();
                    String name = hotInfoBeans.get(position).getName();
                    String figure = hotInfoBeans.get(position).getFigure();
                    String product_id = hotInfoBeans.get(position).getProduct_id();
                    GoodsBean goodsBean = new GoodsBean(name, cover_price, figure, product_id);
                    Intent intent = new Intent(mContext, GoodsInfoActivity.class);
                    intent.putExtra(GOODS_BEAN, goodsBean);
                    mContext.startActivity(intent);
                }
            });
        }
    }

}
