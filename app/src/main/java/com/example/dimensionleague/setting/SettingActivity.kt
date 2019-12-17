package com.example.dimensionleague.setting

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.common.port.IAccountCallBack
import com.example.dimensionleague.login.activity.LoginActivity
import com.example.framework.base.BaseActivity
import com.example.framework.manager.AccountManager
import kotlinx.android.synthetic.main.activity_setting.*
import android.content.DialogInterface
import androidx.appcompat.app.AlertDialog
import com.example.dimensionleague.R
import com.example.net.AppNetConfig


class SettingActivity : BaseActivity(),IAccountCallBack {


    var list:MutableList<SettingBean> = mutableListOf()
    lateinit var heanImg:ImageView
    lateinit var heanName:TextView
    lateinit var heanTitle:TextView
    lateinit var headView: View
    lateinit var foodView: View
    lateinit var foodButton:Button
    lateinit var adapter:MySettingAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun init() {
        headView = LayoutInflater.from(this).inflate(R.layout.setting_item_head, null)
        foodView = LayoutInflater.from(this).inflate(R.layout.setting_item_food, null)
        foodButton=foodView.findViewById<Button>(R.id.setting_food_out)
        heanImg=headView.findViewById<ImageView>(R.id.setting_head_img)
        heanTitle=headView.findViewById<TextView>(R.id.setting_head_user_name)
        heanName=headView.findViewById<TextView>(R.id.setting_head_name)
        AccountManager.getInstance().registerUserCallBack(this)


    }

    override fun initDate() {
        initRecycleView()



    }
//    添加rv布局
    private fun initRecycleView() {
        list.add(SettingBean("地址管理",""))
        list.add(SettingBean("账户与安全","免费领取百万帐户险"))
        list.add(SettingBean("支付设置",""))
        list.add(SettingBean("增票资质","添加增票资质"))
        list.add(SettingBean("我的档案","添加爱宠/神颜"))
        list.add(SettingBean("我的定制","个性化定制各种周边"))
        list.add(SettingBean("通用","清除本地缓存"))
        list.add(SettingBean("联盟会员","免费试用 立领一折优惠卷"))
        list.add(SettingBean("功能体验馆","最新功能提前体验"))
        list.add(SettingBean("功能反馈",""))
        list.add(SettingBean("关于联盟","v1.0"))
    //        判断是否登录
    if (AccountManager.getInstance().isLogin){
        heanTitle.setText(""+AccountManager.getInstance().user.name)
        heanName.setText("用户名 : "+AccountManager.getInstance().user.name)
        foodView.visibility=View.VISIBLE
        if (AccountManager.getInstance().user.avatar!=null){
            Glide.with(this).load(""+ AppNetConfig.BASE_URL+AccountManager.getInstance().user.avatar).apply(RequestOptions().circleCrop()).into(heanImg)
        }

    }else{
        foodView.visibility=View.GONE
    }
        setting_rv.layoutManager=LinearLayoutManager(this)
        adapter= MySettingAdapter(R.layout.setting_item,list)
        adapter.addHeaderView(headView)
        adapter.addFooterView(foodView)
        setting_rv.adapter=adapter;
        initListener()

    }

    private fun initListener() {
//        全部监听
        adapter.setOnItemChildClickListener { adapter, view, position ->
            toast(this,list.get(position).title+"功能暂未开发完全,如有疑问请联系小柚")
        }
        foodButton.setOnClickListener {
            println("SSSSS退出登录")
            AlertDialog.Builder(this)
                .setMessage("是否确认退出账号登陆?")
                .setPositiveButton("确定",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        AccountManager.getInstance().logout()
                        AccountManager.getInstance().notifyLogout()
                        finish()
                    }
                }).show()
        }
        headView.setOnClickListener {
            if (("登录/注册".equals(heanTitle.text.toString()))){
//                登录注册跳转
                startActivity(LoginActivity::class.java,null)
            }else{
//                跳转到个人信息
                startActivity(UserMassagesActivity::class.java,null)
            }
        }

    }

    override fun onRegisterSuccess() {}
    //    登录
    override fun onLogin() {}
    //    退出登录
    override fun onLogout() {}
    //    更新头像
    override fun onAvatarUpdate(url: String?) {
        Glide.with(this).load(""+AppNetConfig.BASE_URL+url).apply(RequestOptions().circleCrop()).into(heanImg)
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.getInstance().unRegisterUserCallBack(this)
    }
}
