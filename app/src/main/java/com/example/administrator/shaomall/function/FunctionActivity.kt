package com.example.administrator.shaomall.function

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.administrator.shaomall.R
import com.example.administrator.shaomall.function.adapter.FindForAdapter
import com.example.commen.util.ShopMailError
import com.shaomall.framework.bean.FindForBean
import com.example.net.AppNetConfig
import com.shaomall.framework.base.BaseMVPActivity
import com.shaomall.framework.manager.ActivityInstanceManager
import kotlinx.android.synthetic.main.activity_function.*

class FunctionActivity : BaseMVPActivity<FindForBean>() {
    private var listOf = mutableListOf<FindForBean>()
    private lateinit var forAdapter: FindForAdapter
    private var forPresenter: FindForPresenter? = null
    private var bundle: Bundle? = null
    override fun setLayoutId(): Int = R.layout.activity_function
    override fun initView() {
        bundle = intent.extras



        //更新标题
        val stringExtra = bundle!!.getString("title")
        if (stringExtra != null) {
            mTvTitle.text = stringExtra
        }
        //点击返回
        mIvBack.setOnClickListener {
            ActivityInstanceManager.removeActivity(this)
        }

        mRv.layoutManager = LinearLayoutManager(this)
        forAdapter = FindForAdapter(listOf)
        mRv.adapter = forAdapter
    }

    override fun initData() {
        if (forPresenter == null) {
            forPresenter = FindForPresenter()
            forPresenter!!.attachView(this)
        }
        if ("待支付" == bundle!!.getString("type")) {
            findForPayData() //待支付
        } else if ("待发货" == bundle!!.getString("type")) {
            findForSendData() //待发货
        }

        //进行网络请求
        forPresenter!!.doGetHttpRequest()
    }

    override fun onRequestHttpDataListSuccess(message: String?, data: MutableList<FindForBean>?) {
        if (data != null) {
            listOf.clear()
            listOf.addAll(data)
            forAdapter.notifyDataSetChanged()
        }
    }


    override fun onRequestHttpDataFailed(error: ShopMailError?) {
        if (error != null) {
            toast(error.errorMessage, false)
        }
    }

    /**
     * 代发货
     */
    private fun findForSendData() {
        forPresenter!!.path = AppNetConfig.FIND_FOR_SEND //待发货
    }

    /**
     * 待支付
     */
    private fun findForPayData() {
        forPresenter!!.path = AppNetConfig.FIND_FOR_PAY
    }


}
