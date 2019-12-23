package com.shaomall.framework.bean;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;

@SuppressLint("ParcelCreator")
public class ShoppingCartBean implements Parcelable {
    /**
     * productId : 3571
     * productNum : 4
     * productName : 【INFANTA.婴梵塔】学院风尖领外套/大衣
     * url : http://www.baidu.com
     * productPrice : 98.00
     */
    private boolean isSelect = false;
    private String productId;
    private String productNum;
    private String productName;
    private String url;
    private String productPrice;

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<ShoppingCartBean> CREATOR = new Creator<ShoppingCartBean>() {
        @Override
        public ShoppingCartBean createFromParcel(Parcel parcel) {
            ShoppingCartBean shoppingCartBean = new ShoppingCartBean();
            shoppingCartBean.productId = parcel.readString();
            shoppingCartBean.productNum = parcel.readString();
            shoppingCartBean.productName = parcel.readString();
            shoppingCartBean.url = parcel.readString();
            shoppingCartBean.productPrice = parcel.readString();
            return shoppingCartBean;
        }

        @Override
        public ShoppingCartBean[] newArray(int i) {
            return new ShoppingCartBean[i];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(productId);
        parcel.writeString(productNum);
        parcel.writeString(productName);
        parcel.writeString(url);
        parcel.writeString(productPrice);
    }
}
