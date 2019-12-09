package com.example.buy.databeans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 商品信息
 *用途: 购物车中商品的子类
 *  请求库不足返回到的子类
 * 请求库存
 *
 * */
public class GoodsBean implements Parcelable {

    public GoodsBean(String productId, int productNum, String productName, String url) {
        this.productId = productId;
        this.productNum = productNum;
        this.productName = productName;
        this.url = url;
    }

    @Override
    public String toString() {
        return "GoodsBean{" +
                "productId='" + productId + '\'' +
                ", productNum=" + productNum +
                ", productName='" + productName + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    /**
     * productId : 1512
     * productNum : 1
     * productName : 衬衫
     * url : http://www.baidu.com
     */

    private String productId;
    private int productNum;
    private String productName;
    private String url;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getProductNum() {
        return productNum;
    }

    public void setProductNum(int productNum) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.productId);
        dest.writeInt(this.productNum);
        dest.writeString(this.productName);
        dest.writeString(this.url);
    }

    protected GoodsBean(Parcel in) {
        this.productId = in.readString();
        this.productNum = in.readInt();
        this.productName = in.readString();
        this.url = in.readString();
    }

    public static final Parcelable.Creator<GoodsBean> CREATOR = new Parcelable.Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel source) {
            return new GoodsBean(source);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };
}
