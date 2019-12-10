package com.example.buy.databeans;

public class ShowGoodsBean {
    @Override
    public String toString() {
        return "ShowGoodsBean{" +
                "brand_id='" + brand_id + '\'' +
                ", brief='" + brief + '\'' +
                ", channel_id='" + channel_id + '\'' +
                ", cover_price='" + cover_price + '\'' +
                ", figure='" + figure + '\'' +
                ", name='" + name + '\'' +
                ", origin_price='" + origin_price + '\'' +
                ", p_catalog_id='" + p_catalog_id + '\'' +
                ", product_id='" + product_id + '\'' +
                ", sell_time_end='" + sell_time_end + '\'' +
                ", sell_time_start='" + sell_time_start + '\'' +
                ", supplier_code='" + supplier_code + '\'' +
                ", supplier_type='" + supplier_type + '\'' +
                '}';
    }

    /**
     * brand_id : 3
     * brief :
     * channel_id : 4
     * cover_price : 13.20
     * figure : /1449137495482.jpg
     * name : [砚池工作室] 剑网3 剑三周边门派古风原创独家礼物 门派衍生金属书签
     * origin_price : 15.00
     * p_catalog_id : 26
     * product_id : 3211
     * sell_time_end : 1449676800
     * sell_time_start : 1478772000
     * supplier_code : 802001
     * supplier_type : 2
     */

    private String brand_id;
    private String brief;
    private String channel_id;
    private String cover_price;
    private String figure;
    private String name;
    private String origin_price;
    private String p_catalog_id;
    private String product_id;
    private String sell_time_end;
    private String sell_time_start;
    private String supplier_code;
    private String supplier_type;

    public String getBrand_id() {
        return brand_id;
    }

    public void setBrand_id(String brand_id) {
        this.brand_id = brand_id;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getCover_price() {
        return cover_price;
    }

    public void setCover_price(String cover_price) {
        this.cover_price = cover_price;
    }

    public String getFigure() {
        return figure;
    }

    public void setFigure(String figure) {
        this.figure = figure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrigin_price() {
        return origin_price;
    }

    public void setOrigin_price(String origin_price) {
        this.origin_price = origin_price;
    }

    public String getP_catalog_id() {
        return p_catalog_id;
    }

    public void setP_catalog_id(String p_catalog_id) {
        this.p_catalog_id = p_catalog_id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getSell_time_end() {
        return sell_time_end;
    }

    public void setSell_time_end(String sell_time_end) {
        this.sell_time_end = sell_time_end;
    }

    public String getSell_time_start() {
        return sell_time_start;
    }

    public void setSell_time_start(String sell_time_start) {
        this.sell_time_start = sell_time_start;
    }

    public String getSupplier_code() {
        return supplier_code;
    }

    public void setSupplier_code(String supplier_code) {
        this.supplier_code = supplier_code;
    }

    public String getSupplier_type() {
        return supplier_type;
    }

    public void setSupplier_type(String supplier_type) {
        this.supplier_type = supplier_type;
    }
}
