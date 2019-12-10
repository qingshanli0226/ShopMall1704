package com.example.common;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class TypeBean implements Parcelable {

    /**
     * code : 200
     * msg : 请求成功
     * result : [{"child":[{"is_deleted":"0","name":"古风","p_catalog_id":"10","parent_id":"3","pic":"/product_catalog/1446016297307.jpg"},{"is_deleted":"0","name":"和风","p_catalog_id":"11","parent_id":"3","pic":"/product_catalog/1446016509141.jpg"},{"is_deleted":"0","name":"lolita","p_catalog_id":"12","parent_id":"3","pic":"/product_catalog/1446016728621.jpg"},{"is_deleted":"0","name":"日常","p_catalog_id":"91","parent_id":"3","pic":"/product_catalog/1446444753067.jpg"}],"hot_product_list":[{"brand_id":"230","brief":"","channel_id":"6","cover_price":"300.00","figure":"/1447239453626.jpg","name":"中华风lolita -山海经 凤凰图  JSK","p_catalog_id":"3","product_id":"2704","sell_time_end":"1447776000","sell_time_start":"1447171200","supplier_code":"0","supplier_type":"1"},{"brand_id":"259","brief":"","channel_id":"8","cover_price":"287.00","figure":"/1450433177397.jpg","name":"【INFANTA.婴梵塔】学院风尖领外套/大衣","p_catalog_id":"12","product_id":"3571","sell_time_end":"1450972800","sell_time_start":"1450368000","supplier_code":"1801005","supplier_type":"2"},{"brand_id":"266","brief":"45天工期","channel_id":"8","cover_price":"403.04","figure":"/1438772068067.jpg","name":"定制【Neverland 原创】直发 烫金lolita 人鱼之歌 高腰JSK连衣裙","p_catalog_id":"12","product_id":"589","sell_time_end":"1439308800","sell_time_start":"1478772000","supplier_code":"1801001","supplier_type":"2"},{"brand_id":"356","brief":"猫咪是神秘的小动物。传说它们能通灵和驱魔，给主人带来好运。可猫咪也被认为是女巫役使的邪恶精灵，是恶魔的化身。萌域为爱美又略微中二的宅MM们，设计了黑猫、白猫主题裙装，不同的风格、一样的可爱。白猫套装穿上性感俏皮，黑色连衣裙让你像猫咪一样优雅又有气质。","channel_id":"8","cover_price":"121.44","figure":"/1463385771450.jpg","name":"【绝对萌域】 黑白猫咪 黑喵咪 短袖连衣裙","p_catalog_id":"91","product_id":"5970","sell_time_end":"1461254400","sell_time_start":"1478772000","supplier_code":"1101004","supplier_type":"2"},{"brand_id":"394","brief":"","channel_id":"6","cover_price":"250.00","figure":"/1457504361484.jpg","name":"【画影】汉元素 古风日常\u2014\u2014 仲夏 ","p_catalog_id":"10","product_id":"5181","sell_time_end":"1458057600","sell_time_start":"1457452800","supplier_code":"1101037","supplier_type":"2"},{"brand_id":"432","brief":"","channel_id":"8","cover_price":"179.00","figure":"/supplier/1467687105197.jpg","name":"现货【TUMO】 雨库洛牌元素 软妹森女系短袖连衣裙","p_catalog_id":"91","product_id":"7729","sell_time_end":"0","sell_time_start":"0","supplier_code":"1601008","supplier_type":"2"}],"is_deleted":"0","name":"小裙子","p_catalog_id":"3","parent_id":"0","pic":""}]
     */

    private int code;
    private String msg;
    private List<ResultBean> result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Parcelable {

        /**
         * child : [{"is_deleted":"0","name":"古风","p_catalog_id":"10","parent_id":"3","pic":"/product_catalog/1446016297307.jpg"},{"is_deleted":"0","name":"和风","p_catalog_id":"11","parent_id":"3","pic":"/product_catalog/1446016509141.jpg"},{"is_deleted":"0","name":"lolita","p_catalog_id":"12","parent_id":"3","pic":"/product_catalog/1446016728621.jpg"},{"is_deleted":"0","name":"日常","p_catalog_id":"91","parent_id":"3","pic":"/product_catalog/1446444753067.jpg"}]
         * hot_product_list : [{"brand_id":"230","brief":"","channel_id":"6","cover_price":"300.00","figure":"/1447239453626.jpg","name":"中华风lolita -山海经 凤凰图  JSK","p_catalog_id":"3","product_id":"2704","sell_time_end":"1447776000","sell_time_start":"1447171200","supplier_code":"0","supplier_type":"1"},{"brand_id":"259","brief":"","channel_id":"8","cover_price":"287.00","figure":"/1450433177397.jpg","name":"【INFANTA.婴梵塔】学院风尖领外套/大衣","p_catalog_id":"12","product_id":"3571","sell_time_end":"1450972800","sell_time_start":"1450368000","supplier_code":"1801005","supplier_type":"2"},{"brand_id":"266","brief":"45天工期","channel_id":"8","cover_price":"403.04","figure":"/1438772068067.jpg","name":"定制【Neverland 原创】直发 烫金lolita 人鱼之歌 高腰JSK连衣裙","p_catalog_id":"12","product_id":"589","sell_time_end":"1439308800","sell_time_start":"1478772000","supplier_code":"1801001","supplier_type":"2"},{"brand_id":"356","brief":"猫咪是神秘的小动物。传说它们能通灵和驱魔，给主人带来好运。可猫咪也被认为是女巫役使的邪恶精灵，是恶魔的化身。萌域为爱美又略微中二的宅MM们，设计了黑猫、白猫主题裙装，不同的风格、一样的可爱。白猫套装穿上性感俏皮，黑色连衣裙让你像猫咪一样优雅又有气质。","channel_id":"8","cover_price":"121.44","figure":"/1463385771450.jpg","name":"【绝对萌域】 黑白猫咪 黑喵咪 短袖连衣裙","p_catalog_id":"91","product_id":"5970","sell_time_end":"1461254400","sell_time_start":"1478772000","supplier_code":"1101004","supplier_type":"2"},{"brand_id":"394","brief":"","channel_id":"6","cover_price":"250.00","figure":"/1457504361484.jpg","name":"【画影】汉元素 古风日常\u2014\u2014 仲夏 ","p_catalog_id":"10","product_id":"5181","sell_time_end":"1458057600","sell_time_start":"1457452800","supplier_code":"1101037","supplier_type":"2"},{"brand_id":"432","brief":"","channel_id":"8","cover_price":"179.00","figure":"/supplier/1467687105197.jpg","name":"现货【TUMO】 雨库洛牌元素 软妹森女系短袖连衣裙","p_catalog_id":"91","product_id":"7729","sell_time_end":"0","sell_time_start":"0","supplier_code":"1601008","supplier_type":"2"}]
         * is_deleted : 0
         * name : 小裙子
         * p_catalog_id : 3
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

        public static class ChildBean implements Parcelable {

            /**
             * is_deleted : 0
             * name : 古风
             * p_catalog_id : 10
             * parent_id : 3
             * pic : /product_catalog/1446016297307.jpg
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.is_deleted);
                dest.writeString(this.name);
                dest.writeString(this.p_catalog_id);
                dest.writeString(this.parent_id);
                dest.writeString(this.pic);
            }

            public ChildBean() {
            }

            protected ChildBean(Parcel in) {
                this.is_deleted = in.readString();
                this.name = in.readString();
                this.p_catalog_id = in.readString();
                this.parent_id = in.readString();
                this.pic = in.readString();
            }

            public static final Creator<ChildBean> CREATOR = new Creator<ChildBean>() {
                @Override
                public ChildBean createFromParcel(Parcel source) {
                    return new ChildBean(source);
                }

                @Override
                public ChildBean[] newArray(int size) {
                    return new ChildBean[size];
                }
            };
        }

        public static class HotProductListBean implements Parcelable {

            /**
             * brand_id : 230
             * brief :
             * channel_id : 6
             * cover_price : 300.00
             * figure : /1447239453626.jpg
             * name : 中华风lolita -山海经 凤凰图  JSK
             * p_catalog_id : 3
             * product_id : 2704
             * sell_time_end : 1447776000
             * sell_time_start : 1447171200
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

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.brand_id);
                dest.writeString(this.brief);
                dest.writeString(this.channel_id);
                dest.writeString(this.cover_price);
                dest.writeString(this.figure);
                dest.writeString(this.name);
                dest.writeString(this.p_catalog_id);
                dest.writeString(this.product_id);
                dest.writeString(this.sell_time_end);
                dest.writeString(this.sell_time_start);
                dest.writeString(this.supplier_code);
                dest.writeString(this.supplier_type);
            }

            public HotProductListBean() {
            }

            protected HotProductListBean(Parcel in) {
                this.brand_id = in.readString();
                this.brief = in.readString();
                this.channel_id = in.readString();
                this.cover_price = in.readString();
                this.figure = in.readString();
                this.name = in.readString();
                this.p_catalog_id = in.readString();
                this.product_id = in.readString();
                this.sell_time_end = in.readString();
                this.sell_time_start = in.readString();
                this.supplier_code = in.readString();
                this.supplier_type = in.readString();
            }

            public static final Creator<HotProductListBean> CREATOR = new Creator<HotProductListBean>() {
                @Override
                public HotProductListBean createFromParcel(Parcel source) {
                    return new HotProductListBean(source);
                }

                @Override
                public HotProductListBean[] newArray(int size) {
                    return new HotProductListBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.is_deleted);
            dest.writeString(this.name);
            dest.writeString(this.p_catalog_id);
            dest.writeString(this.parent_id);
            dest.writeString(this.pic);
            dest.writeList(this.child);
            dest.writeList(this.hot_product_list);
        }

        public ResultBean() {
        }

        protected ResultBean(Parcel in) {
            this.is_deleted = in.readString();
            this.name = in.readString();
            this.p_catalog_id = in.readString();
            this.parent_id = in.readString();
            this.pic = in.readString();
            this.child = new ArrayList<ChildBean>();
            in.readList(this.child, ChildBean.class.getClassLoader());
            this.hot_product_list = new ArrayList<HotProductListBean>();
            in.readList(this.hot_product_list, HotProductListBean.class.getClassLoader());
        }

        public static final Creator<ResultBean> CREATOR = new Creator<ResultBean>() {
            @Override
            public ResultBean createFromParcel(Parcel source) {
                return new ResultBean(source);
            }

            @Override
            public ResultBean[] newArray(int size) {
                return new ResultBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.code);
        dest.writeString(this.msg);
        dest.writeList(this.result);
    }

    public TypeBean() {
    }

    protected TypeBean(Parcel in) {
        this.code = in.readInt();
        this.msg = in.readString();
        this.result = new ArrayList<ResultBean>();
        in.readList(this.result, ResultBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<TypeBean> CREATOR = new Parcelable.Creator<TypeBean>() {
        @Override
        public TypeBean createFromParcel(Parcel source) {
            return new TypeBean(source);
        }

        @Override
        public TypeBean[] newArray(int size) {
            return new TypeBean[size];
        }
    };
}
