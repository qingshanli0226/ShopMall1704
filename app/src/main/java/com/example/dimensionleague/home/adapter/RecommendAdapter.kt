package com.example.dimensionleague.home.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.bumptech.glide.Glide
import com.example.buy.activity.GoodsActivity
import com.example.buy.databeans.GoodsBean
import com.example.common.utils.IntentUtil
import com.example.dimensionleague.R
import com.example.common.HomeBean
import com.example.net.AppNetConfig
import kotlinx.android.synthetic.main.home_recommend_item.view.*

class RecommendAdapter(
    recommendInfo:List<HomeBean.ResultBean.SeckillInfoBean.ListBean>
) : BaseAdapter() {
    private var recommendInfo:List<HomeBean.ResultBean.SeckillInfoBean.ListBean>? = null
    init {
        this.recommendInfo = recommendInfo
    }
    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val views:View =
            LayoutInflater.from(parent!!.context).inflate(R.layout.home_recommend_item,parent,false)
        lateinit var holder:ViewHolder
        holder = ViewHolder(views)
        views.tag = holder
        Glide.with(parent.context).load("${AppNetConfig.BASE_URl_IMAGE}${recommendInfo!![position].figure}").into(holder.ivRecommend)
        holder.tvName.text = recommendInfo?.get(position)?.name
        holder.tvPrice.text = "${recommendInfo?.get(position)?.cover_price}￥"
        //跳转
        views.setOnClickListener { v->
            val intent = Intent(v.context, GoodsActivity::class.java)
            intent.putExtra(IntentUtil.GOTO_GOOD, GoodsBean(
                recommendInfo!![position].product_id,
                0, recommendInfo!![position].name,
                recommendInfo!![position].figure,
                recommendInfo!![position].cover_price))
            v.context.startActivity(intent, null)
        }
        return views
    }

    override fun getItem(position: Int): Any {
        return recommendInfo!![position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return recommendInfo!!.size
    }

    inner class ViewHolder(itemView: View){
        var ivRecommend = itemView.home_recommend_iv_recommend!!
        var tvName = itemView.home_recommend_tv_name!!
        var tvPrice = itemView.home_recommend_tv_price!!
    }
}