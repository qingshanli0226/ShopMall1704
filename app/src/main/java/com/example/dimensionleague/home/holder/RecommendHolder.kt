package com.example.dimensionleague.home.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.common.HomeBean
import com.example.dimensionleague.home.adapter.RecommendAdapter
import kotlinx.android.synthetic.main.home_recommend.view.*

class RecommendHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun setData(recommendInfo: List<HomeBean.ResultBean.SeckillInfoBean.ListBean>?){
        if(recommendInfo==null){
            return
        }
        with(itemView){
            home_recommend_gv.adapter= RecommendAdapter(recommendInfo)
        }
    }
}