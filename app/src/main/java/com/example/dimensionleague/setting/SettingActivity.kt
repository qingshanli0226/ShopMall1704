package com.example.dimensionleague.setting

import android.annotation.SuppressLint
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
import android.graphics.Color
import android.util.Log
import com.example.common.code.Constant
import com.example.common.view.LogoutDialog
import com.example.dimensionleague.R
import com.example.dimensionleague.activity.MainActivity
import com.example.dimensionleague.address.AddressActivity
import com.example.net.AppNetConfig

class SettingActivity : BaseActivity(), IAccountCallBack {


    private var list: MutableList<SettingBean> = mutableListOf()
    private lateinit var heanImg: ImageView
    private lateinit var heanName: TextView
    private lateinit var heanTitle: TextView
    private lateinit var headView: View
    private lateinit var foodView: View
    private lateinit var foodButton: Button
    private lateinit var adapter: MySettingAdapter
    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }


    override fun init() {
        setting_toolbar.init(Constant.OTHER_STYLE)
        setting_toolbar.background = resources.getDrawable(R.drawable.toolbar_style, null)
        setting_toolbar.other_back.setImageResource(R.drawable.back3)
        setting_toolbar.other_title.setTextColor(Color.WHITE)
        setting_toolbar.other_title.text = getString(R.string.user_setting)
        headView = LayoutInflater.from(this).inflate(R.layout.setting_item_head, null)
        foodView = LayoutInflater.from(this).inflate(R.layout.setting_item_food, null)
        foodButton = foodView.findViewById(R.id.setting_food_out)
        heanImg = headView.findViewById(R.id.setting_head_img)
        heanTitle = headView.findViewById(R.id.setting_head_user_name)
        heanName = headView.findViewById(R.id.setting_head_name)
        AccountManager.getInstance().registerUserCallBack(this)

    }

    override fun initDate() {
        initRecycleView()
    }

    //    添加rv布局
    private fun initRecycleView() {
        list.add(SettingBean(getString(R.string.setting_address)))
        list.add(SettingBean(getString(R.string.setting_security)))
        list.add(SettingBean(getString(R.string.setting_payment)))
        list.add(SettingBean(getString(R.string.setting_qualification)))
        list.add(SettingBean(getString(R.string.setting_my_files)))
        list.add(SettingBean(getString(R.string.setting_my_custom)))
        list.add(SettingBean(getString(R.string.setting_common)))
        list.add(SettingBean(getString(R.string.setting_union)))
        list.add(SettingBean(getString(R.string.setting_hall)))
        list.add(
            SettingBean(
                getString(R.string.setting_about),
                packageManager.getPackageInfo(packageName, 0).versionName
            )
        )
        //        判断是否登录
        if (AccountManager.getInstance().isLogin) {

            heanTitle.text = (AccountManager.getInstance().user.name.toString())
            heanName.text =
                (getString(R.string.setting_user) + AccountManager.getInstance().user.name.toString())
            Log.d("lhf--welcome--Setting", AccountManager.getInstance().getUser().toString())

            foodView.visibility = View.VISIBLE
            if (AccountManager.getInstance().user.avatar != null) {
                Glide.with(this)
                    .load(AppNetConfig.BASE_URL + AccountManager.getInstance().user.avatar)
                    .apply(RequestOptions().circleCrop()).into(heanImg)
            }

        } else {
            foodView.visibility = View.GONE
        }
        setting_rv.layoutManager = LinearLayoutManager(this)
        adapter = MySettingAdapter(R.layout.setting_item, list)
        adapter.addHeaderView(headView)
        adapter.addFooterView(foodView)
        setting_rv.adapter = adapter
        initListener()

    }

    private fun initListener() {
//        全部监听
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when (position) {
                0 -> {
                    startActivity(AddressActivity::class.java, null)
                }
                else -> toast(this, list[position].title + getString(R.string.contact))
            }

        }
        foodButton.setOnClickListener {
            val logoutDialog = LogoutDialog(this@SettingActivity)
            logoutDialog.init(R.layout.logout_dialog)
            //TODO 背景透明
            logoutDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
            logoutDialog.setCanceledOnTouchOutside(false)
            logoutDialog.show()
            val notarizeBtn = logoutDialog.findViewById<Button>(R.id.notarize)
            val cancelBtn = logoutDialog.findViewById<Button>(R.id.cancel)
            cancelBtn.setOnClickListener {
                logoutDialog.dismiss()
            }
            notarizeBtn.setOnClickListener {
                AccountManager.getInstance().logout()
                AccountManager.getInstance().notifyLogout()
                logoutDialog.dismiss()
                toast(this, getString(R.string.login_out))
                finishActivity()
            }
        }
        headView.setOnClickListener {
            if (heanTitle.text == getString(R.string.mine_login)) {
//                登录注册跳转
                startActivity(LoginActivity::class.java, null)
            } else {
//                跳转到个人信息
                startActivity(UserMassagesActivity::class.java, null)
            }
        }
        setting_toolbar.other_back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            boundActivity(intent)
            finish()
        }

    }

    override fun onRegisterSuccess() {}
    //    登录
    override fun onLogin() {}

    //    退出登录
    override fun onLogout() {}

    //    更新头像
    override fun onAvatarUpdate(url: String?) {
        Glide.with(this).load(url).apply(RequestOptions().circleCrop()).into(heanImg)
    }

    override fun onDestroy() {
        super.onDestroy()
        AccountManager.getInstance().unRegisterUserCallBack(this)
    }
}
