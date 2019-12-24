package com.example.dimensionleague.activity

import android.content.Intent
import androidx.fragment.app.Fragment
import android.view.KeyEvent
import android.widget.Toast
import com.example.dimensionleague.R
import com.example.buy.ShopCartFragment
import com.example.common.view.MyToast
import com.example.dimensionleague.find.FindFragment
import com.example.framework.manager.AccountManager
import com.example.dimensionleague.home.HomeFragment
import com.example.dimensionleague.mine.MineFragment
import com.example.dimensionleague.type.TypeFragment
import kotlinx.android.synthetic.main.activity_main.*
import com.example.framework.base.BaseNetConnectActivity
import com.example.framework.listener.OnShopCartListener
import com.example.buy.CartManager
import com.example.dimensionleague.JobService

import kotlin.system.exitProcess
import com.example.dimensionleague.login.activity.LoginActivity

class MainActivity : BaseNetConnectActivity() {

    private lateinit var listener: OnShopCartListener

    private var exitTime = 0L
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
        val isAutoLogin = bundle!!.getBoolean("isAutoLogin")
        if (!isAutoLogin) {
            Toast.makeText(this, resources.getString(R.string.timeout), Toast.LENGTH_SHORT).show()
            AccountManager.getInstance().logout()
            AccountManager.getInstance().notifyLogout()
        } else {
            AccountManager.getInstance().notifyLogin()
        }

        if (isNetType == getString(R.string.ascend)) {
            MyToast.showToast(this, getString(R.string.ascend_messenger), null, Toast.LENGTH_LONG)
        }
        list.add(HomeFragment())
        list.add(TypeFragment())
        list.add(FindFragment())
        list.add(ShopCartFragment())
        list.add(MineFragment())
    }

    override fun initDate() {
        super.init()
        startService(Intent(this@MainActivity,JobService::class.java))
        main_easy.selectTextColor(R.color.colorGradualPurple)
            .normalTextColor(R.color.colorMainNormal)
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
            .titleItems(
                arrayOf(
                    getString(R.string.home),
                    getString(R.string.type),
                    getString(R.string.find),
                    getString(R.string.shopping_cart),
                    getString(R.string.mine)
                )).onTabClickListener { v, position ->
                if (position == 3) {
                    if (!AccountManager.getInstance().isLogin){
                        toast(this,"请先登录")
                        startActivity(LoginActivity::class.java,null)
                        return@onTabClickListener true
                    }else{
                        return@onTabClickListener false
                    }
                }
                return@onTabClickListener false

            }
            .build()
        //注册监听,监听购物车数量
        listener = OnShopCartListener { num ->
            main_easy.setMsgPointCount(3, num)
        }
        CartManager.getInstance().registerListener(listener)
    }

    override fun onDestroy() {
        super.onDestroy()
        CartManager.getInstance().unregister(listener)
    }

    //调用moveTaskToBack可以让程序退出到后台运行，false表示只对主界面生效，true表示任何界面都可以生效。
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        moveTaskToBack(false)

    }


    private fun exit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(
                applicationContext, "再按一次退出程序",
                Toast.LENGTH_SHORT
            ).show()
            exitTime = System.currentTimeMillis()
        } else {
            finish()
            exitProcess(0)
        }
    }
}

