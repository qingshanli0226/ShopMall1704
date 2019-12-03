package com.example.dimensionleague

import android.os.Handler
import android.widget.Toast
import com.example.framework.base.BaseNetConnectActivity

class MainActivity : BaseNetConnectActivity() {
    override fun getLayoutId(): Int {
        return 0
    }

    override fun init() {
        super.init()
        if(!isConnectStatus){
            Toast.makeText(this,"无网络连接",Toast.LENGTH_SHORT).show()
            Handler().postDelayed(object:Runnable{
                override fun run() {
                    showEmpty()
                }
            },3000)
            return
        }
        Handler().postDelayed(object:Runnable{
            override fun run() {
                hideEmpty()
            }
        },3000)
        showLoading()
        Toast.makeText(this,"网络已连接",Toast.LENGTH_SHORT).show()

    }
}
