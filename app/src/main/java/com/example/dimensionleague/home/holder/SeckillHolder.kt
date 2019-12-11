package com.example.dimensionleague.home.holder


import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.common.HomeBean
import com.example.dimensionleague.home.adapter.SeckillAdapter
import kotlinx.android.synthetic.main.home_seckill.view.*
import java.text.SimpleDateFormat
import java.util.*

class SeckillHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var handler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message) {
            if(msg.what==1){
                dt-=1000
                var sdf:SimpleDateFormat = SimpleDateFormat("HH:mm:ss")
                itemView.home_seckill_tv_time_seckill.text = sdf.format(Date(dt.toLong()))

                removeMessages(1)
                sendEmptyMessageDelayed(1, 1000)
                if (dt == 0) {
                    removeMessages(1)
                }
            }
        }
    }
    var isBegin:Boolean = true
    var dt:Int = 0
    @SuppressLint("WrongConstant")
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
            home_seckill.layoutManager = LinearLayoutManager(itemView.context,LinearLayout.HORIZONTAL,false)
            home_seckill.adapter = SeckillAdapter(list)
        }
    }
}