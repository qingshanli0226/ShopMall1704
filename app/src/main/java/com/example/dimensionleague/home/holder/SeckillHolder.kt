package com.example.dimensionleague.home.holder

import android.os.Handler
import android.os.Message
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.HomeBean
import com.example.dimensionleague.home.adapter.SeckillAdapter
import kotlinx.android.synthetic.main.home_seckill.view.*
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.util.*

class SeckillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private var handler=MyHandler(this)
    private var isBegin:Boolean = true
    var dt:Int = 0

    fun setData(seckillInfo: HomeBean.ResultBean.SeckillInfoBean?){
        if(seckillInfo==null){
            return
        }
        if(isBegin){
            val startTime = seckillInfo.start_time
            val endTime = seckillInfo.end_time
            dt = endTime.toInt()-startTime.toInt()
            isBegin = false
        }

        handler.sendEmptyMessageDelayed(1,1000)
        val list = seckillInfo.list
        with(itemView){
            home_seckill.layoutManager = LinearLayoutManager(itemView.context,LinearLayoutManager.HORIZONTAL,false)
            home_seckill.adapter = SeckillAdapter(list)
        }
    }

    class MyHandler constructor(
        holder:SeckillHolder
    ):Handler(){
        private var mWeakReference= WeakReference<SeckillHolder>(holder)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            var seckillHolder = mWeakReference.get()
            if(msg.what==1){
                seckillHolder!!.dt-=1000
                var sdf = SimpleDateFormat("HH:mm:ss")
                seckillHolder!!.itemView.home_seckill_tv_time_seckill.text = sdf.format(Date(seckillHolder.dt.toLong()))

                removeMessages(1)
                sendEmptyMessageDelayed(1, 1000)
                if (seckillHolder.dt == 0) {
                    removeMessages(1)
                }
            }
        }
    }

}