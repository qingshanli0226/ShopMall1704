package com.example.dimensionleague.home.holder

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.example.dimensionleague.businessbean.HomeBean
import com.example.dimensionleague.home.adapter.ActAdapter
import kotlinx.android.synthetic.main.home_act.view.*

class ActViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setData(act_info:List<HomeBean.ResultBean.ActInfoBean>?){
        if(act_info==null){
            return
        }
        var imageList:ArrayList<String> = ArrayList()
        for (i in act_info){
//            imageList.add("${Constants.BASE_URl_IMAGE}${i.icon_url}")
        }
        with(itemView){
            home_act_viewpager.pageMargin = 20
            home_act_viewpager.offscreenPageLimit = 3
            home_act_viewpager.adapter  = ActAdapter(imageList,itemView.context)
        }
    }
}