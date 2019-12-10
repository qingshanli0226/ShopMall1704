package com.example.dimensionleague.home.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dimensionleague.businessbean.HomeBean
import com.example.dimensionleague.home.adapter.HotAdapter
import kotlinx.android.synthetic.main.home_hot.view.*
import kotlinx.android.synthetic.main.home_hot_item.view.*

class HotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setData(hotInfo: List<HomeBean.ResultBean.HotInfoBean>?){
        if(hotInfo==null){
            return
        }
        with(itemView){
            home_hot.adapter = HotAdapter(hotInfo)
        }
    }

}