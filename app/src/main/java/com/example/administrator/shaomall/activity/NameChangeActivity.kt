package com.example.administrator.shaomall.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.administrator.shaomall.R
import com.shaomall.framework.base.BaseActivity
import kotlinx.android.synthetic.main.activity_name_change.*

class NameChangeActivity : BaseActivity() {
    override fun setLayoutId(): Int {
        return R.layout.activity_name_change
    }

    override fun initView() {

    }

    override fun initData() {
        //返回按钮
        changeback.setOnClickListener {
            finish()
        }

        //确认按钮
        changeacc.setOnClickListener {
            finish()
        }


    }


}
