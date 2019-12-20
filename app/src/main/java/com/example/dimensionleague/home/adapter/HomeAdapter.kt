package com.example.dimensionleague.home.adapter

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.dimensionleague.R
import com.example.common.HomeBean
import com.example.dimensionleague.home.holder.*

class HomeAdapter(
    private var bean: HomeBean.ResultBean,
    mContext: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)

    /**
     * 横幅广告
     */
    private val banner = 0
    /**
     * 频道
     */
    private val channal = 1
    /**
     * 活动
     */
    private val act = 2
    /**
     * 推荐
     */
    private val seckill = 4
    /**
     * 秒杀
     */
    private val recommend = 3
    /**
     * 热卖
     */
    private val hot = 5

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            banner -> BannerViewHolder(mLayoutInflater.inflate(R.layout.home_banner, parent, false))
            channal -> ChannelHolder(mLayoutInflater.inflate(R.layout.home_channel, parent, false))
            act -> ActViewHolder(mLayoutInflater.inflate(R.layout.home_act, parent, false))
            seckill -> SeckillHolder(mLayoutInflater.inflate(R.layout.home_seckill, parent, false))
            recommend -> RecommendHolder(mLayoutInflater.inflate(R.layout.home_recommend, parent, false))
            hot -> HotViewHolder(mLayoutInflater.inflate(R.layout.home_hot, parent, false))
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
            banner -> (holder as BannerViewHolder).setDate(bean.banner_info)
            channal -> (holder as ChannelHolder).setDate(bean.channel_info)
            act -> (holder as ActViewHolder).setData(bean.act_info)
            seckill -> (holder as SeckillHolder).setData(bean.seckill_info)
            recommend -> (holder as RecommendHolder).setData(bean.recommend_info)
            hot -> (holder as HotViewHolder).setData((bean.hot_info))
        }

    }
}