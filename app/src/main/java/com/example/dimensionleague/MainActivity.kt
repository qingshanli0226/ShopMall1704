package com.example.dimensionleague

import android.os.Handler
import android.widget.Toast
import com.example.framework.base.BaseNetConnectActivity

class MainActivity : BaseNetConnectActivity() {
    override fun getRelativeLayout(): Int {
        return R.id.main_relative
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        super.init()
        showEmpty()
    }
}
