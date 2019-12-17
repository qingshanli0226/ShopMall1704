package com.example.administrator.shaomall.goodsinfo

import android.webkit.WebViewClient
import com.alibaba.fastjson.JSONObject
import com.bumptech.glide.Glide
import com.example.administrator.shaomall.R
import com.example.administrator.shaomall.activity.MainActivity
import com.example.administrator.shaomall.goodsinfo.bean.GoodsInfoBean
import com.example.administrator.shaomall.login.LoginActivity
import com.example.commen.util.ShopMailError
import com.example.net.AppNetConfig
import com.shaomall.framework.base.BaseMVPActivity
import com.shaomall.framework.base.presenter.IBasePresenter
import com.shaomall.framework.manager.ActivityInstanceManager
import com.shaomall.framework.manager.UserInfoManager
import kotlinx.android.synthetic.main.activity_commodity.*

class GoodsInfoActivity : BaseMVPActivity<String>() {
    var iBasePresenter: IBasePresenter<String>? = null

    override fun setLayoutId(): Int = R.layout.activity_commodity

    override fun initView() {
        //点击关闭
        mIbGoodInfoBack.setOnClickListener {
            ActivityInstanceManager.removeActivity(this)
        }

        //获取intent
        val intent = intent
        val goodsInfo = intent.getParcelableExtra<GoodsInfoBean>(GOODS_INFO)

        //展示图片
        Glide.with(this).load(AppNetConfig.BASE_URl_IMAGE + goodsInfo.pic).into(mIvGoodInfoImage)
        //商品名称
        mTvGoodInfoName.text = goodsInfo.goodsName
        //商品详情
        val goodsDescribe = goodsInfo.goodsDescribe
        if (goodsDescribe != null) {
            mTvGoodInfoDesc.text = goodsDescribe
        }

        //商品价格
        mTvGoodInfoPrice.text = "￥ " + goodsInfo.goodsPrice

        //展示商品详情
        mWbGoodInfoMore.loadUrl(AppNetConfig.BASE_URl_IMAGE+goodsInfo.pic)
        mWbGoodInfoMore.webViewClient = WebViewClient()
        val settings = mWbGoodInfoMore.settings
        settings.javaScriptEnabled = true //允许使用js


        //点击进入购物车
        mTvFGoodInfoCart.setOnClickListener {
            toClass(MainActivity::class.java, 3)//跳转到购物车
        }






        //点击加入购物车
        mBtnGoodInfoAddcart.setOnClickListener {
            if (!UserInfoManager.getInstance().isLogin) {

                toast("请登录", false)
                toClass(LoginActivity::class.java)
            } else {
                if (iBasePresenter == null) {
                    iBasePresenter = AddCartPresenter()
                    iBasePresenter!!.attachView(this)
                }
                //设置请求参数
                val objects = JSONObject()
                objects.put("productId", goodsInfo.productId)
                objects.put("productNum", 1)
                objects.put("productName", goodsInfo.goodsName)
                objects.put("productPrice", goodsInfo.goodsPrice)
                objects.put("url", AppNetConfig.BASE_URl_IMAGE + goodsInfo.pic)
                (iBasePresenter as AddCartPresenter).setJsonObject(objects)

                //将商品添加进入购物车
                iBasePresenter!!.doJsonPostHttpRequest()
            }
        }
    }


    /**
     * 网络请求成功回调
     */
    override fun onRequestHttpDataSuccess(message: String?, data: String?) {
        toast("$data", false)
    }

    /**
     * 网络请求失败回调
     */
    override fun onRequestHttpDataFailed(error: ShopMailError?) {
        if (error != null) {
            if(error.errorCode == ShopMailError.ERROR_NOT_LOGIN.errorCode){
                UserInfoManager.getInstance().unLogout()
            }
        }
        toast("${error!!.errorMessage}", false)
        println("1233: ${UserInfoManager.getInstance().token}")
    }

    companion object {
        val GOODS_INFO = "goodsInfo"
    }
}
