package com.example.dimensionleague

import androidx.fragment.app.Fragment
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    lateinit var rv :RecyclerView ;
    var list:MutableList<Fragment> =mutableListOf(HomeFragment(),HomeFragment(),HomeFragment(),HomeFragment(),HomeFragment());
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        main_easy.selectTextColor(Color.parseColor("#d3217b"))
            .normalTextColor(Color.parseColor("#707070"))
            .selectIconItems(intArrayOf(
                R.drawable.home,
                R.drawable.vertical_list,
                R.drawable.find,
                R.drawable.shopping_cart,
                R.drawable.my
            ))
            .normalIconItems(intArrayOf(
                R.drawable.home_no,
                R.drawable.vertical_list_no,
                R.drawable.find_no,
                R.drawable.shopping_cart_no,
                R.drawable.my_no
            ))
            .fragmentManager(supportFragmentManager)
            .fragmentList(list)
            .titleItems(arrayOf("首页","分类","发现","购物车","我的"))
            .canScroll(true)
            .build()

    }
}
