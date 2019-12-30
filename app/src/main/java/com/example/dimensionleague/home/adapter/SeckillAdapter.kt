package com.example.dimensionleague.home.adapter

import android.content.Intent
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.buy.activity.GoodsActivity
import com.example.buy.databeans.GoodsBean
import com.example.common.HomeBean
import com.example.common.utils.IntentUtil
import com.example.dimensionleague.R
import com.example.net.AppNetConfig
import kotlinx.android.synthetic.main.home_seckill_item.view.*

class SeckillAdapter(
    list: List<HomeBean.ResultBean.SeckillInfoBean.ListBean>
): RecyclerView.Adapter<SeckillAdapter.RecyclerViewHolder>() {
    var list: List<HomeBean.ResultBean.SeckillInfoBean.ListBean>? = null
    init {
        this.list = list
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        return RecyclerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.home_seckill_item,parent,false))
    }

    override fun getItemCount(): Int {
        return if(list== null) 0 else list!!.size
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        with(holder.itemView){
            Glide.with(holder.itemView.context).load("${AppNetConfig.BASE_URl_IMAGE}${list!![position].figure}").into(home_seckill_iv_figure)
            home_seckill_tv_cover_price.text = "${list!![position].cover_price}￥"
            home_seckill_tv_origin_price.text = "${list!![position].origin_price}￥"
            home_seckill_tv_origin_price.paintFlags=Paint.STRIKE_THRU_TEXT_FLAG
            this.setOnClickListener { v->

                val intent = Intent(context, GoodsActivity::class.java)
                intent.putExtra(IntentUtil.GOTO_GOOD, GoodsBean(
                        list!![position].product_id,
                        0, list!![position].name,
                        list!![position].figure,
                    list!![position].cover_price))
                context.startActivity(intent, null)
            }
        }
    }

    inner class RecyclerViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)
}