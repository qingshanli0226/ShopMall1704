package com.example.dimensionleague.home.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.dimensionleague.R
import com.example.common.HomeBean
import com.example.dimensionleague.home.holder.*

class HomeAdapter(
    var bean: HomeBean.ResultBean,
    var mContext: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    /**
     * 横幅广告
     */
    val BANNER = 0
    /**
     * 频道
     */
    val CHANNEL = 1
    /**
     * 活动
     */
    val ACT = 2
    /**
     * 推荐
     */
    val SECKILL = 4
    /**
     * 秒杀
     */
    val RECOMMEND = 3
    /**
     * 热卖
     */
    val HOT = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            BANNER -> BannerViewHolder(mLayoutInflater.inflate(R.layout.home_banner, parent, false))
            CHANNEL -> ChannelHolder(mLayoutInflater.inflate(R.layout.home_channel, parent, false))
            ACT -> ActViewHolder(mLayoutInflater.inflate(R.layout.home_act, parent, false))
            SECKILL -> SeckillHolder(mLayoutInflater.inflate(R.layout.home_seckill, parent, false))
            RECOMMEND -> RecommendHolder(mLayoutInflater.inflate(R.layout.home_recommend, parent, false))
            HOT -> HotViewHolder(mLayoutInflater.inflate(R.layout.home_hot, parent, false))
            else -> HotViewHolder(mLayoutInflater.inflate(R.layout.home_hot, parent, false))
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            BANNER -> (holder as BannerViewHolder).setDate(bean.banner_info)
            CHANNEL -> (holder as ChannelHolder).setDate(bean.channel_info)
            ACT -> (holder as ActViewHolder).setData(bean.act_info)
            SECKILL -> (holder as SeckillHolder).setData(bean.seckill_info)
            RECOMMEND -> (holder as RecommendHolder).setData(bean.recommend_info)
            HOT -> (holder as HotViewHolder).setData((bean.hot_info))
        }

    }
}