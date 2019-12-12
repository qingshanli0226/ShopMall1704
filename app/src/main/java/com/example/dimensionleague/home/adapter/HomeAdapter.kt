package com.example.dimensionleague.home.adapter


import android.content.Context
import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import com.example.dimensionleague.R
import com.example.common.HomeBean
import com.example.dimensionleague.home.holder.*


class HomeAdapter(
    bean: HomeBean.ResultBean?,
    mContext: Context?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var bean: HomeBean.ResultBean? = null
    var mContext: Context
    var mLayoutInflater: LayoutInflater? = null

    init {

        this.bean = bean
        this.mContext = mContext!!
        mLayoutInflater = LayoutInflater.from(mContext)
    }

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
     * 秒杀
     */
    val SECKILL = 3
    /**
     * 推荐
     */
    val RECOMMEND = 4
    /**
     * 热卖
     */
    val HOT = 5

    /**
     * 当前类型
     */
    var currentType = BANNER

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var Holder:RecyclerView.ViewHolder? = null

        return when(viewType){
            BANNER -> BannerViewHolder(mLayoutInflater!!.inflate(R.layout.home_banner, parent, false))
            CHANNEL -> ChannelHolder(mLayoutInflater!!.inflate(R.layout.home_channel,parent,false))
            ACT-> ActViewHolder(mLayoutInflater!!.inflate(R.layout.home_act,parent,false))
            SECKILL-> SeckillHolder(mLayoutInflater!!.inflate(R.layout.home_seckill,parent,false))
            RECOMMEND-> RecommendHolder(mLayoutInflater!!.inflate(R.layout.home_recommend,parent,false))
            HOT -> HotViewHolder(mLayoutInflater!!.inflate(R.layout.home_hot,null))
            else ->ChannelHolder(mLayoutInflater!!.inflate(R.layout.home_channel,parent,false))
        }
    }

    override fun getItemCount(): Int {
        return 6
    }

    override fun getItemViewType(position: Int): Int {
        Log.d("SSS","position:--"+position.toString())
        when(position){
            BANNER -> currentType = BANNER
            CHANNEL -> currentType = CHANNEL
            ACT -> currentType = ACT
            SECKILL -> currentType = SECKILL
            RECOMMEND -> currentType = RECOMMEND
            HOT -> currentType = HOT
        }
        Log.d("SSS","currenType"+currentType.toString())
        return currentType
    }
    
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        println("lhf holder--${holder is ChannelHolder}")
        if (getItemViewType(position)==BANNER) {
            val bannerViewHolder = holder as BannerViewHolder
            bannerViewHolder.setDate(bean?.banner_info)
        }else if(getItemViewType(position)==CHANNEL){
            val channelHolder = holder as ChannelHolder
            channelHolder.setDate(bean?.channel_info)
        }else if(getItemViewType(position)==ACT){
            val actViewHolder = holder as ActViewHolder
            actViewHolder.setData(bean?.act_info)
        }
        else if(getItemViewType(position)==SECKILL){
            val seckillHolder = holder as SeckillHolder
            seckillHolder.setData(bean?.seckill_info)
        }else if(getItemViewType(position)==RECOMMEND){
            val recommendHolder = holder as RecommendHolder
            recommendHolder.setData(bean?.recommend_info)
        }else if(getItemViewType(position)==HOT){
            val hotViewHolder = holder as HotViewHolder
            hotViewHolder.setData((bean?.hot_info))
        }

    }
}