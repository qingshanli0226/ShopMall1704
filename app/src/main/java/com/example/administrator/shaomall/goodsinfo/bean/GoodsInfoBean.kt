package com.example.administrator.shaomall.goodsinfo.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GoodsInfoBean(var productId: String, var goodsName: String = "默认", var goodsPrice: String = "0.0", var goodsDescribe: String? = null, var pic: String) : Parcelable