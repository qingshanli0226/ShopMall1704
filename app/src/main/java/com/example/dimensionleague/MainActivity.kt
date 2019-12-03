package com.example.dimensionleague

import android.widget.Toast
import com.example.framework.base.BaseNetConnectActivity

class MainActivity : BaseNetConnectActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun init() {
        super.init()
        if(!isConnectStatus){
            Toast.makeText(this,"无网络连接",Toast.LENGTH_SHORT).show()
            return
        }
        Toast.makeText(this,"网络已连接",Toast.LENGTH_SHORT).show()
    }
}
