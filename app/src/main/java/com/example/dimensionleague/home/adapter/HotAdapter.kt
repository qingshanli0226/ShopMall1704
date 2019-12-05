package com.example.dimensionleague.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.dimensionleague.R
import com.example.dimensionleague.businessbean.HomeBean
import com.example.net.AppNetConfig
import kotlinx.android.synthetic.main.home_hot_item.view.*

class HotAdapter (
    hotInfo: List<HomeBean.ResultBean.HotInfoBean>
): BaseAdapter() {
    var hotInfo:List<HomeBean.ResultBean.HotInfoBean>? = null
    init {
        this.hotInfo = hotInfo
    }
    override fun getView(posion: Int, view: View?, constan: ViewGroup?): View {
        lateinit var view: View
        lateinit var viewholder:ViewHolder
            view = LayoutInflater.from(constan!!.context).inflate(R.layout.home_hot_item,constan,false)
            viewholder = ViewHolder(view)
            view.setTag(viewholder)
        Glide.with(constan!!.context).load(AppNetConfig.BASE_URl_IMAGE+hotInfo?.get(posion)?.figure).into(viewholder.iv_hot)
        viewholder.tv_name.text = hotInfo?.get(posion)?.name
        viewholder.tv_price.text = "${hotInfo?.get(posion)?.cover_price}￥"
        return view
    }

    override fun getItem(posion: Int): Any {
        return hotInfo!!.get(posion)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getCount(): Int {
        return hotInfo!!.size
    }

    class ViewHolder(itemView: View){
        var tv_price = itemView.home_hot_tv_price
        var iv_hot = itemView.home_hot_iv_hot
        var tv_name = itemView.home_hot_tv_name
    }
}