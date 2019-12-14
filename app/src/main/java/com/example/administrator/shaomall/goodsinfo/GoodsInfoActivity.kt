package com.example.administrator.shaomall.goodsinfo

import android.animation.Animator
import android.animation.ObjectAnimator
import android.graphics.PathMeasure
import android.graphics.PointF
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.RelativeLayout
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
    private var iBasePresenter: IBasePresenter<String>? = null
    private lateinit var mPathMeasure: PathMeasure
    /**
     * 贝塞尔曲线中间过程的点的坐标
     */
    private val mCurrentPosition = FloatArray(2)

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
        mWbGoodInfoMore.loadUrl(AppNetConfig.BASE_URl_IMAGE + goodsInfo.pic)
        mWbGoodInfoMore.webViewClient = WebViewClient()
        val settings = mWbGoodInfoMore.settings
        settings.javaScriptEnabled = true //允许使用js


        //点击进入购物车
        mTvFGoodInfoCart.setOnClickListener {
            toClass(MainActivity::class.java,3)

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
                //给添加购物车设置动画, 贝瑟尔曲线
                setBezierCurveAnimation() //设置贝塞尔曲线动画
            }
        }
    }


    private var x = 0f
    private var y = 0f
    /**
     * 设置贝塞尔曲线动画
     */
    private fun setBezierCurveAnimation() {
        x = 0f
        y = 0f

        //      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        val goods = ImageView(this)
        goods.setImageResource(R.drawable.add_cart_bg_selector)
        val params = RelativeLayout.LayoutParams(50, 50)
        rl.addView(goods, params)

        //得到加入购物车按钮的坐标（用于计算动画开始的坐标）
        val startLoc = IntArray(2)
        mBtnGoodInfoAddcart.getLocationInWindow(startLoc)
        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        val endLoc = IntArray(2)
        mTvFGoodInfoCart.getLocationInWindow(endLoc)

        //点击加入购物车的原点坐标
        val p0 = PointF()
        //        p0.x = startLoc[0] - mBtnGoodInfoAddcart.width.toFloat()
        //        p0.y = startLoc[1] - mBtnGoodInfoAddcart.height.toFloat()
        p0.x = startLoc[0].toFloat() + mBtnGoodInfoAddcart.width.toFloat() / 2
        p0.y = startLoc[1].toFloat() - mBtnGoodInfoAddcart.height.toFloat() / 2

        val p1 = PointF()
        p1.x = startLoc[0].toFloat() - 30
        p1.y = startLoc[1].toFloat() - 350

        val p2 = PointF()
        p2.x = endLoc[0].toFloat() + mTvFGoodInfoCart.width.toFloat() / 2
        p2.y = endLoc[1].toFloat() - mTvFGoodInfoCart.height.toFloat() / 2

        println("按钮: ${mBtnGoodInfoAddcart.width}, ${mBtnGoodInfoAddcart.height}  || 购物车:  ${mTvFGoodInfoCart.width}, ${mTvFGoodInfoCart.height}")

        val goInAnim = ObjectAnimator()
        goInAnim.setFloatValues(0f, 1f)
        goInAnim.duration = 1000
        goInAnim.addUpdateListener {
            val t = it.animatedValue as Float
            val oneMinusT = 1.0f - t
            x = oneMinusT * oneMinusT * (p0.x) + 2 * t * oneMinusT * (p1.x) + t * t * (p2.x)
            y = oneMinusT * oneMinusT * (p0.y) + 2 * t * oneMinusT * (p1.y) + t * t * (p2.y)
            goods.x = x
            goods.y = y
        }
        goInAnim.target = 1
        goInAnim.start()

        //动画结束监听
        goInAnim.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {


            }

            override fun onAnimationEnd(animation: Animator?) {
                // 把移动的图片imageview从父布局里移除
                rl.removeView(goods)
            }

            override fun onAnimationCancel(animation: Animator?) {

            }

            override fun onAnimationStart(animation: Animator?) {

            }
        })
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
            if (error.errorCode == ShopMailError.ERROR_NOT_LOGIN.errorCode) {
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
