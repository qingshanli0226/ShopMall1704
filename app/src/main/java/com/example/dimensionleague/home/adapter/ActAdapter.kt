package com.example.dimensionleague.home.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide

class ActAdapter(
    image:List<String>,
    context:Context
) : PagerAdapter() {
    var image:List<String>? = null
    var context:Context? = null
    init {
        this.image = image
        this.context = context
    }
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun getCount(): Int {
        return image!!.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = ImageView(context)
        view.scaleType = ImageView.ScaleType.FIT_XY
        //绑定数据
        Glide.with(context!!).load(image?.get(position)).into(view)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }
}