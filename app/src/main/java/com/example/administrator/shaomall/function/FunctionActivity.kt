package com.example.administrator.shaomall.function

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.example.administrator.shaomall.R
import com.example.administrator.shaomall.function.adapter.FunctionAdaptor
import com.example.commen.util.PageUtil
import com.example.shoppingcart.OrderFormActivity
import com.shaomall.framework.base.BaseActivity
import com.shaomall.framework.bean.FunctionBean
import com.shaomall.framework.base.presenter.IBasePresenter
import com.shaomall.framework.manager.ActivityInstanceManager
import kotlinx.android.synthetic.main.activity_commodity.*
import kotlinx.android.synthetic.main.activity_function.*

class FunctionActivity : BaseActivity() {
    lateinit var presenter: IBasePresenter<FunctionBean>
    private var bundle: Bundle? = null

    private lateinit var functionAdaptor: FunctionAdaptor   //适配器
    private lateinit var functionViewModel: FunctionViewModel //ViewModel 网络请求

    internal lateinit var pageUtil: PageUtil
    override fun setLayoutId(): Int = R.layout.activity_function
    override fun initView() {
        bundle = intent.extras
        //更新标题
        val title = bundle!!.getString("title")

        if (!title.isNullOrBlank()) {
            mToolBarCustom.title = title
        }

        //点击返回
        mToolBarCustom.setLeftBackImageViewOnClickListener {
            ActivityInstanceManager.removeActivity(this)
        }


        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true //列表再底部开始展示，反转后由上面开始展示
        linearLayoutManager.reverseLayout = true //列表翻转
        mRv.layoutManager = linearLayoutManager
        mRv.smoothScrollBy(0, 0)
        functionAdaptor = FunctionAdaptor(this)
        mRv.adapter = functionAdaptor
    }

    override fun initData() {

        pageUtil = PageUtil(this)
        pageUtil.review =  goodsInfoRelativeLayout

        //使用ViewModel提供者,获取ViewModel的实例
        functionViewModel = ViewModelProviders.of(this).get(FunctionViewModel::class.java)
        functionViewModel.liveData.observe(this, Observer<List<FunctionBean>> { t ->
            functionAdaptor.upDateData(t) //赋值
        })

        val string = bundle!!.getString("type")
        if ("待支付" == string && !string.isNullOrBlank()) {
            functionViewModel.findForPay()
        } else if ("待发货" == string && !string.isNullOrBlank()) {
            functionViewModel.findForSend()
        }
    }


    /**
     * 点击事件监听
     */
    fun itemViewClick(functionBean: FunctionBean, postion: Int) {
        if ("待发货" == mToolBarCustom.title) {
            return
        }

        //再次调起支付功能
        val orderInfo = functionBean.orderInfo as String
        val tradeNo = functionBean.tradeNo //订单号
        val bundle = Bundle()
        bundle.putString("orderInfo", orderInfo)
        bundle.putString("tradeNo", tradeNo)
        toClass(OrderFormActivity::class.java, bundle)
    }


    override fun onDestroy() {
        //解除所有订阅者
        functionViewModel.clearDisposable()
        super.onDestroy()
    }

}
