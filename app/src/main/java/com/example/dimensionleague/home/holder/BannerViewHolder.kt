package com.example.dimensionleague.home.holder


import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dimensionleague.R
import com.example.common.HomeBean
import com.example.net.AppNetConfig
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader

class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setDate(banner_info: List<HomeBean.ResultBean.BannerInfoBean>? ){
        if(banner_info==null){

            return
        }

        var imageList:ArrayList<String> = ArrayList()
        for (i in banner_info){
            imageList.add("${AppNetConfig.BASE_URl_IMAGE}${i.image}")
        }
        val banner = itemView.findViewById<Banner>(R.id.home_banner)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
        banner.setImages(imageList)
        banner.setImageLoader(GlideImageLoader())
        banner.setDelayTime(2000)
        banner.start()

    }
    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context!!).load(path).into(imageView!!)
        }
    }
}