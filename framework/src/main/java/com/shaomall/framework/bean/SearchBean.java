package com.shaomall.framework.bean;


import java.util.List;

public class SearchBean {

    /**
     * child : [{"is_deleted":"0","name":"服装","p_catalog_id":"18","parent_id":"4","pic":"/product_catalog/1446016631439.jpg"},{"is_deleted":"0","name":"假发","p_catalog_id":"19","parent_id":"4","pic":"/product_catalog/1446016650875.jpg"},{"is_deleted":"0","name":"道具","p_catalog_id":"20","parent_id":"4","pic":"/product_catalog/1446016673365.jpg"},{"is_deleted":"0","name":"妆面","p_catalog_id":"21","parent_id":"4","pic":"/product_catalog/1446016713541.jpg"}]
     * hot_product_list : [{"brand_id":"5","brief":"现货~尚硅谷有售道具原材料哦~","channel_id":"5","cover_price":"65.00","figure":"/1448622974578.jpg","name":"【大公制作】COSPLAY道具制作专业教程 切割粘合上色贴皮养护方法技巧制作步骤详解 ","p_catalog_id":"20","product_id":"3088","sell_time_end":"1449158400","sell_time_start":"1448553600","supplier_code":"0","supplier_type":"1"},{"brand_id":"5","brief":"","channel_id":"5","cover_price":"19.00","figure":"/1446607168578.jpg","name":"【主宰者】透气隐形 束胸 裹胸 cos汉子必备","p_catalog_id":"18","product_id":"2573","sell_time_end":"1447171200","sell_time_start":"1446566400","supplier_code":"1607002","supplier_type":"2"},{"brand_id":"436","brief":"","channel_id":"5","cover_price":"94.50","figure":"/supplier/1471315793182.jpg","name":"现货【桂】苏葉 洛丽塔空气刘海 青灰色日常软妹lolita原宿假发","p_catalog_id":"19","product_id":"8759","sell_time_end":"0","sell_time_start":"1478772000","supplier_code":"1601009","supplier_type":"2"},{"brand_id":"436","brief":"精灵之舞之幻雾 空气刘海 长发及腰卷发 深灰色 长70cm","channel_id":"5","cover_price":"79.00","figure":"/supplier/1469436115002.jpg","name":"现货【桂】幻雾 洛丽塔空气刘海奶奶灰 及腰长卷发lolita日系假发","p_catalog_id":"19","product_id":"8351","sell_time_end":"0","sell_time_start":"1478772000","supplier_code":"1601009","supplier_type":"2"},{"brand_id":"286","brief":"3-5天发货 随机保税仓直发。可以试试哦，咬唇效果很好的。染色是樱花提取物，不小心吃。这个是咬唇妆哦，不会化的，或者觉得平时用的麻烦的亲们 可以试试哦，咬唇效果很好的。染色是樱花提取物可以食用， 染色相对更均匀， 而且使用后不会觉得干，很滋润，非常容 易上色，使用后颜色很好看，非常嫩~","channel_id":"5","cover_price":"39.00","figure":"/1455766294751.jpg","name":"【海鸟跨境】  泰国mistine双头咬唇唇彩","p_catalog_id":"21","product_id":"4638","sell_time_end":"1456329600","sell_time_start":"1455724800","supplier_code":"1111001","supplier_type":"2"},{"brand_id":"436","brief":"精灵之舞系列之绿林 渐变梨花头 收脸鬓角 发尾自然内扣 爱不释手~ 自留款~","channel_id":"5","cover_price":"69.00","figure":"/supplier/1469436287434.jpg","name":"现货【桂】绿林 梨花头内扣Lolita日常绿色渐变原宿 洛丽塔假发","p_catalog_id":"19","product_id":"8352","sell_time_end":"0","sell_time_start":"1478772000","supplier_code":"1601009","supplier_type":"2"}]
     * is_deleted : 0
     * name : 装扮
     * p_catalog_id : 4
     * parent_id : 0
     * pic :
     */

    private String is_deleted;
    private String name;
    private String p_catalog_id;
    private String parent_id;
    private String pic;
    private List<ChildBean> child;
    private List<HotProductListBean> hot_product_list;

    public String getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(String is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getP_catalog_id() {
        return p_catalog_id;
    }

    public void setP_catalog_id(String p_catalog_id) {
        this.p_catalog_id = p_catalog_id;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public List<ChildBean> getChild() {
        return child;
    }

    public void setChild(List<ChildBean> child) {
        this.child = child;
    }

    public List<HotProductListBean> getHot_product_list() {
        return hot_product_list;
    }

    public void setHot_product_list(List<HotProductListBean> hot_product_list) {
        this.hot_product_list = hot_product_list;
    }

    public static class ChildBean {
        /**
         * is_deleted : 0
         * name : 服装
         * p_catalog_id : 18
         * parent_id : 4
         * pic : /product_catalog/1446016631439.jpg
         */

        private String is_deleted;
        private String name;
        private String p_catalog_id;
        private String parent_id;
        private String pic;

        public String getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(String is_deleted) {
            this.is_deleted = is_deleted;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getP_catalog_id() {
            return p_catalog_id;
        }

        public void setP_catalog_id(String p_catalog_id) {
            this.p_catalog_id = p_catalog_id;
        }

        public String getParent_id() {
            return parent_id;
        }

        public void setParent_id(String parent_id) {
            this.parent_id = parent_id;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }
    }

    public static class HotProductListBean {
        /**
         * brand_id : 5
         * brief : 现货~尚硅谷有售道具原材料哦~
         * channel_id : 5
         * cover_price : 65.00
         * figure : /1448622974578.jpg
         * name : 【大公制作】COSPLAY道具制作专业教程 切割粘合上色贴皮养护方法技巧制作步骤详解
         * p_catalog_id : 20
         * product_id : 3088
         * sell_time_end : 1449158400
         * sell_time_start : 1448553600
         * supplier_code : 0
         * supplier_type : 1
         */

        private String brand_id;
        private String brief;
        private String channel_id;
        private String cover_price;
        private String figure;
        private String name;
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
}
