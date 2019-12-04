package com.example.dimensionleague.home.holder


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dimensionleague.businessbean.HomeBean
import com.example.dimensionleague.home.adapter.ChannelAdapter
import kotlinx.android.synthetic.main.home_channel.view.*

class ChannelHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setDate(channelInfo: List<HomeBean.ResultBean.ChannelInfoBean>?){
        if(channelInfo==null){
            return
        }
        with(itemView){
            home_channel.adapter = ChannelAdapter(channelInfo)
        }
    }
}