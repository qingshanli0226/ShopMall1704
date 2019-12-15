package com.example.dimensionleague.home.holder

import android.content.Intent
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.buy.activity.GoodsActiviy
import com.example.common.HomeBean
import com.example.common.IntentUtil
import com.example.dimensionleague.R
import com.example.framework.base.BaseRecyclerAdapter
import com.example.framework.base.BaseViewHolder
import com.example.net.AppNetConfig
import kotlinx.android.synthetic.main.home_hot.view.*

class HotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setData(hotInfo: List<HomeBean.ResultBean.SeckillInfoBean.ListBean>?){
        if(hotInfo==null){
            return
        }
        with(itemView){
            home_hot.layoutManager=GridLayoutManager(context,2)
            home_hot.adapter = object : BaseRecyclerAdapter<HomeBean.ResultBean.SeckillInfoBean.ListBean>(R.layout.home_hot_item,hotInfo){
                override fun onBind(holder: BaseViewHolder?, position: Int) {
                    holder!!.getImageView(R.id.home_hot_iv_hot,AppNetConfig.BASE_URl_IMAGE + hotInfo[position].figure)
                        .setOnClickListener { v->
                            val intent = Intent(context, GoodsActiviy::class.java)
                            intent.putExtra(IntentUtil.SHOW_GOOD, hotInfo[position])
                            context.startActivity(intent)
                        }
                    holder.getTextView(R.id.home_hot_tv_name,hotInfo[position].name)
                    holder.getTextView(R.id.home_hot_tv_price,"${hotInfo[position].cover_price}￥")
                }
            }
        }
    }

}