package com.example.administrator.shaomall.goodsinfo

import android.animation.Animator
import android.animation.ValueAnimator
import android.graphics.Path
import android.graphics.PathMeasure
import android.view.animation.LinearInterpolator
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
            toClass(MainActivity::class.java, 3) //跳转到购物车
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
//                setBezierCurveAnimation() //设置贝塞尔曲线动画
            }
        }
    }

    /**
     * 设置贝塞尔曲线动画
     */
    private fun setBezierCurveAnimation() {
        //      一、创造出执行动画的主题---imageview
        //代码new一个imageview，图片资源是上面的imageview的图片
        // (这个图片就是执行动画的图片，从开始位置出发，经过一个抛物线（贝塞尔曲线），移动到购物车里)
        val goods = ImageView(this)
        goods.setImageResource(R.drawable.add_cart_bg_selector)
        val params = RelativeLayout.LayoutParams(100, 100)
        ll.addView(goods, params)

        //计算动画开始/ 结束点的坐标的准备工作
        //得到父布局的起始点坐标（用于辅助计算动画开始/结束时的点的坐标）
        val parentLocation = IntArray(2)
        ll.getLocationInWindow(parentLocation)

        //得到商品图片的坐标（用于计算动画开始的坐标）
        val startLoc = IntArray(2)
        mBtnGoodInfoAddcart.getLocationInWindow(startLoc)

        //得到购物车图片的坐标(用于计算动画结束后的坐标)
        val endLoc = IntArray(2)
        mTvFGoodInfoCart.getLocationInWindow(endLoc)

        //        三、正式开始计算动画开始/结束的坐标
        //开始掉落的商品的起始点：商品起始点-父布局起始点+该商品图片的一半
        val startX = (startLoc[0] - parentLocation[0] + mBtnGoodInfoAddcart.getWidth() / 2).toFloat()
        val startY = (startLoc[1] - parentLocation[1] + mBtnGoodInfoAddcart.getHeight() / 2).toFloat()

        //商品掉落后的终点坐标：购物车起始点-父布局起始点+购物车图片的1/5
        val toX = (endLoc[0] - parentLocation[0] + mBtnGoodInfoAddcart.getWidth() / 5).toFloat()
        val toY = (endLoc[1] - parentLocation[1]).toFloat()

        //四、计算中间动画的插值坐标（贝塞尔曲线）（其实就是用贝塞尔曲线来完成起终点的过程）
        //开始绘制贝塞尔曲线
        val path = Path()
        //移动到起始点（贝塞尔曲线的起点）
        path.moveTo(startX, startY)
        //使用二次萨贝尔曲线：注意第一个起始坐标越大，贝塞尔曲线的横向距离就会越大，一般按照下面的式子取即可
        path.quadTo((startX + toX) / 2, startY, toX, toY)
        //mPathMeasure用来计算贝塞尔曲线的曲线长度和贝塞尔曲线中间插值的坐标，
        // 如果是true，path会形成一个闭环
        mPathMeasure = PathMeasure(path, true)


        //★★★属性动画实现（从0到贝塞尔曲线的长度之间进行插值计算，获取中间过程的距离值）
        val valueAnimator = ValueAnimator.ofFloat(0f, mPathMeasure.length)
        valueAnimator.duration = 1000
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener { animation ->
            // 当插值计算进行时，获取中间的每个值，
            // 这里这个值是中间过程中的曲线长度（下面根据这个值来得出中间点的坐标值）
            val value = animation.animatedValue as Float
            // ★★★★★获取当前点坐标封装到mCurrentPosition
            // boolean getPosTan(float distance, float[] pos, float[] tan) ：
            // 传入一个距离distance(0<=distance<=getLength())，然后会计算当前距
            // 离的坐标点和切线，pos会自动填充上坐标，这个方法很重要。
            mPathMeasure.getPosTan(value, mCurrentPosition, null) //mCurrentPosition此时就是中间距离点的坐标值
            // 移动的商品图片（动画图片）的坐标设置为该中间点的坐标
            goods.setTranslationX(mCurrentPosition[0])
            goods.setTranslationY(mCurrentPosition[1])
        }

        //      五、 开始执行动画
        valueAnimator.start()

        //      六、动画结束后的处理
        valueAnimator.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(animation: Animator?) {


            }

            override fun onAnimationEnd(animation: Animator?) {
                // 把移动的图片imageview从父布局里移除
                ll.removeView(goods)
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
