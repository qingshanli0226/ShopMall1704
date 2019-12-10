package com.example.dimensionleague.home.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.dimensionleague.R
import com.example.dimensionleague.businessbean.HomeBean
import com.example.net.AppNetConfig
import kotlinx.android.synthetic.main.home_recommend_item.view.*

class RecommendAdapter(
    recommendInfo:List<HomeBean.ResultBean.RecommendInfoBean>
) : BaseAdapter() {
    var recommendInfo:List<HomeBean.ResultBean.RecommendInfoBean>? = null
    init {
        this.recommendInfo = recommendInfo
    }
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        lateinit var views:View
        lateinit var holder:ViewHolder
            views = LayoutInflater.from(parent!!.context).inflate(R.layout.home_recommend_item,parent,false)
            holder = ViewHolder(views)
            views.setTag(holder)
        Glide.with(parent!!.context).load("${AppNetConfig.BASE_URl_IMAGE}${recommendInfo!!.get(position).figure}").into(holder.iv_recommend)
        holder.tv_name.text = recommendInfo?.get(position)?.name
        holder.tv_price.text = "${recommendInfo?.get(position)?.cover_price}￥"
        return views
    }

    override fun getItem(position: Int): Any {
        return recommendInfo!!.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return recommendInfo!!.size
    }

    inner class ViewHolder(itemView: View){
        var iv_recommend = itemView.home_recommend_iv_recommend
        var tv_name = itemView.home_recommend_tv_name
        var tv_price = itemView.home_recommend_tv_price
    }
}