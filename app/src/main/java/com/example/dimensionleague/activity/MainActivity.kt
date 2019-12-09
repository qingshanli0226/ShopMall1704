package com.example.dimensionleague.activity

import androidx.fragment.app.Fragment
import android.graphics.Color
import com.example.dimensionleague.R
import com.example.dimensionleague.home.HomeFragment
import com.example.dimensionleague.type.TypeFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.example.framework.base.BaseNetConnectActivity

class MainActivity : BaseNetConnectActivity() {
    override fun getRelativeLayout(): Int {
        return R.id.main_relative
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    var list: MutableList<Fragment> = mutableListOf()

    override fun init() {
        super.init()
        list.add(HomeFragment())
        list.add(TypeFragment())
        list.add(HomeFragment())
        list.add(HomeFragment())
        list.add(HomeFragment())
    }
    override fun initDate() {
        main_easy.selectTextColor(Color.parseColor("#d3217b"))
            .normalTextColor(Color.parseColor("#707070"))
            .selectIconItems(
                intArrayOf(
                    R.drawable.home,
                    R.drawable.vertical_list,
                    R.drawable.find,
                    R.drawable.shopping_cart,
                    R.drawable.my
                )
            )
            .normalIconItems(
                intArrayOf(
                    R.drawable.home_no,
                    R.drawable.vertical_list_no,
                    R.drawable.find_no,
                    R.drawable.shopping_cart_no,
                    R.drawable.my_no
                )
            )
            .fragmentManager(supportFragmentManager)
            .fragmentList(list)
            .titleItems(arrayOf("首页", "分类", "发现", "购物车", "我的"))
            .canScroll(true)
            .build()

    }
}

