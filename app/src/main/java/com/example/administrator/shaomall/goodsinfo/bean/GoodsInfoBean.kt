package com.example.administrator.shaomall.goodsinfo.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GoodsInfoBean(var productId: String,
                         var productName: String = "衬衫",
                         var url: String? = null,
                         var productPrice: String = "0.0",
                         var productDescribe: String? = null,
                         var pic: String) : Parcelable
