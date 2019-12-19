package com.example.dimensionleague.home.holder


import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.dimensionleague.R
import com.example.common.HomeBean
import com.example.net.AppNetConfig
import com.youth.banner.Banner
import com.youth.banner.BannerConfig
import com.youth.banner.loader.ImageLoader

class BannerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun setDate(banner_info: List<HomeBean.ResultBean.BannerInfoBean>?) {
        if (banner_info == null) {
            return
        }

        var imageList: ArrayList<String> = ArrayList()
        for (i in banner_info) {
            imageList.add("${AppNetConfig.BASE_URl_IMAGE}${i.image}")
        }
        val banner = itemView.findViewById<Banner>(R.id.home_banner)
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
            .setImages(imageList)
            .setImageLoader(GlideImageLoader())
            .setDelayTime(2000)
            .start()

    }

    class GlideImageLoader : ImageLoader() {
        override fun displayImage(context: Context?, path: Any?, imageView: ImageView?) {
            Glide.with(context!!).load(path).apply(
                RequestOptions.bitmapTransform(
                    RoundedCorners(30)
                )
            ).into(imageView!!)
        }
    }
}