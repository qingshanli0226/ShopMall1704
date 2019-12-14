package com.example.shoppingcart.bean;

public class UpDatePrductNumBean {

    /**
     * productId : 1512
     * productNum : 10
     * productName : 衬衫
     * url : http://www.baidu.com
     */

    private boolean isSelect;
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
}
