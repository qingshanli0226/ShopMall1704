package com.example.administrator.shaomall.function

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.administrator.shaomall.R
import com.example.administrator.shaomall.function.adapter.FunctionAdaptor
import com.shaomall.framework.base.BaseActivity
import com.shaomall.framework.bean.FunctionBean
import com.shaomall.framework.base.presenter.IBasePresenter
import com.shaomall.framework.manager.ActivityInstanceManager
import kotlinx.android.synthetic.main.activity_function.*

class FunctionActivity : BaseActivity() {
    lateinit var presenter: IBasePresenter<FunctionBean>
    private var bundle: Bundle? = null
    private lateinit var functionAdaptor: FunctionAdaptor
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

        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true //列表再底部开始展示，反转后由上面开始展示
        linearLayoutManager.reverseLayout = true //列表翻转
        mRv.layoutManager = linearLayoutManager
        mRv.smoothScrollBy(0, 0)
        functionAdaptor = FunctionAdaptor()
        mRv.adapter = functionAdaptor
    }

    override fun initData() {
        //使用ViewModel提供者,获取ViewModel的实例
        val functionViewModel = ViewModelProviders.of(this).get(FunctionViewModel::class.java)
        functionViewModel.liveData.observe(this, Observer<List<FunctionBean>> { t ->
            functionAdaptor.upDateData(t) //赋值
        })
        if ("待支付" == bundle!!.getString("type")) {
            functionViewModel.findForPay()
        } else if ("待发货" == bundle!!.getString("type")) {
            functionViewModel.findForSend()
        }
    }
}
