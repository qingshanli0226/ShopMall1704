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
    var channelInfo:List<HomeBean.ResultBean.ChannelInfoBean>? = null
    init {
        this.channelInfo = channelInfo
    }
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        lateinit var views:View
        lateinit var holder:ViewHolder
//        if(view==null){
             views = LayoutInflater.from(parent!!.context).inflate(R.layout.home_channel_item,parent,false)
            holder = ViewHolder(views)
            views.setTag(holder)
//        }else{
//            views = view
//            holder = views.getTag() as ViewHolder
//        }
        Glide.with(parent!!.context).load("${AppNetConfig.BASE_URl_IMAGE}${channelInfo!!.get(position).image}").into(holder.iv_channer)
        holder.tv_channer.text = channelInfo!!.get(position).channel_name
        return views
    }

    override fun getItem(position: Int): Any {
        return channelInfo!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return channelInfo!!.size
    }

    inner class ViewHolder(itemView: View){
        var iv_channer = itemView.channel_iv
        var tv_channer = itemView.channel_tv
    }
}