package com.example.dimensionleague.home.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.dimensionleague.R
import com.example.common.HomeBean
import com.example.net.AppNetConfig
import kotlinx.android.synthetic.main.home_channel_item.view.*

class ChannelAdapter(
    channelInfo: List<HomeBean.ResultBean.ChannelInfoBean>
) : BaseAdapter() {
    private var channelInfo:List<HomeBean.ResultBean.ChannelInfoBean>? = null
    init {
        this.channelInfo = channelInfo
    }
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val views:View =
            LayoutInflater.from(parent!!.context).inflate(R.layout.home_channel_item,parent,false)
        lateinit var holder:ViewHolder
        holder = ViewHolder(views)
        views.tag = holder
        Glide.with(parent.context).load("${AppNetConfig.BASE_URl_IMAGE}${channelInfo!![position].image}").into(holder.ivChanner)
        holder.tvChanner.text = channelInfo!![position].channel_name
        return views
    }

    override fun getItem(position: Int): Any {
        return channelInfo!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return channelInfo!!.size
    }

    inner class ViewHolder(itemView: View){
        var ivChanner = itemView.channel_iv!!
        var tvChanner = itemView.channel_tv!!
    }
}