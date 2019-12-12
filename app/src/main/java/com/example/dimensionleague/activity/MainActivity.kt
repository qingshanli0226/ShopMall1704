package com.example.dimensionleague.activity

import androidx.fragment.app.Fragment
import android.graphics.Color
import android.widget.Toast

import com.example.dimensionleague.R
import com.example.buy.ShopCartFragment

import com.example.dimensionleague.find.FindFragment
import com.example.framework.manager.AccountManager
import com.example.dimensionleague.home.HomeFragment
import com.example.dimensionleague.mine.MineFragment
import com.example.dimensionleague.type.TypeFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.example.framework.base.BaseNetConnectActivity
import com.example.framework.listener.OnShopCartListener
import com.example.framework.manager.CartManager

class MainActivity : BaseNetConnectActivity() {
    lateinit var listener:OnShopCartListener
    override fun getRelativeLayout(): Int {
        return R.id.main_relative
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    var list: MutableList<Fragment> = mutableListOf()

    override fun init() {
        super.init()
        val bundle = intent!!.getBundleExtra("data")
        val isAutoLogin = bundle.getBoolean("isAutoLogin")
        if(!isAutoLogin){
            AccountManager.getInstance().logout()
            Toast.makeText(this,"登录超时,请重新登录",Toast.LENGTH_SHORT).show()
        }else{
            AccountManager.getInstance().notifyLogin()
        }
        list.add(HomeFragment())
        list.add(TypeFragment())
        list.add(FindFragment())
        list.add(ShopCartFragment())
        list.add(MineFragment())
    }
    override fun initDate() {
        super.init()
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
            .build()
        //注册监听,监听购物车数量
        listener= OnShopCartListener { num->
            main_easy.setMsgPointCount(3,num)
        }
        CartManager.getInstance().registerListener(listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        CartManager.getInstance().unregister(listener)
    }
}

