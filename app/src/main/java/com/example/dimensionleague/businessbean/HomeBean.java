package com.example.dimensionleague.businessbean;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

public class HomeBean {

    /**
     * code : 200
     * msg : 请求成功
     * result : {"act_info":[{"icon_url":"/operation/img/1478169868/1478761370286.png","name":"尚硅谷福利专区之111.1专区","url":"/oper/1478169868app.html"},{"icon_url":"/operation/img/1478763176/1478762941492.png","name":"尚硅谷福利专区 黄金狗粮限量11.1元抢","url":"/oper/1478763176app.html"}],"banner_info":[{"image":"/1478770583834.png","option":3,"type":0,"value":{"url":"/act20161111?cyc_app=1"}},{"image":"/1478770583835.png","option":2,"type":0,"value":{"url":"/act20161111?cyc_app=1"}},{"image":"/1478770583836.png","option":1,"type":0,"value":{"url":"/act20161111?cyc_app=1"}}],"channel_info":[{"channel_name":"服饰","image":"/app/img/menu-cyc.png","option":2,"type":1,"value":{"channel_id":"8"}},{"channel_name":"游戏","image":"/app/img/menu-game.png","option":2,"type":1,"value":{"channel_id":"4"}},{"channel_name":"动漫","image":"/app/img/menu-carttoon.png","option":2,"type":1,"value":{"channel_id":"3"}},{"channel_name":"装扮","image":"/app/img/menu-cosplay.png","option":2,"type":1,"value":{"channel_id":"5"}},{"channel_name":"古风","image":"/app/img/menu-oldage.png","option":2,"type":1,"value":{"channel_id":"6"}},{"channel_name":"漫展票务","image":"/app/img/menu-collect.png","option":2,"type":1,"value":{"channel_id":"9"}},{"channel_name":"文具","image":"/app/img/menu-stationery.png","option":2,"type":1,"value":{"channel_id":"11"}},{"channel_name":"零食","image":"/app/img/menu-snack.png","option":2,"type":1,"value":{"channel_id":"10"}},{"channel_name":"首饰","image":"/app/img/menu-jewelry.png","option":2,"type":1,"value":{"channel_id":"12"}},{"channel_name":"更多","image":"/app/img/menu-more.png","option":6,"type":1,"value":{"channel_id":"13"}}],"hot_info":[{"cover_price":"159.00","figure":"/1477984921265.jpg","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","product_id":"9356"},{"cover_price":"159.00","figure":"/1477984931882.jpg","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪-特典版","product_id":"10391"},{"cover_price":"29.00","figure":"/1452161899947.jpg","name":"【喵鹿酱】超萌 假透肉 拼接 踩脚过膝打底袜 裤袜-加绒保暖","product_id":"3831"},{"cover_price":"199.00","figure":"/1447232577216.jpg","name":"【漫踪】原创 宫崎骏 龙猫 可爱雪地靴动漫保暖鞋周边冬季毛绒鞋子","product_id":"2691"},{"cover_price":"70.00","figure":"/1474370572805.jpg","name":"【现货】【GIRLISM少女主义】 第4期 2016夏秋刊 lolita","product_id":"9414"},{"cover_price":"4.80","figure":"/1465268743242.jpg","name":"【艾漫】全职高手-蜜饯系列","product_id":"6869"},{"cover_price":"143.10","figure":"/1477360350123.png","name":"【高冷猫】暗黑系软妹病娇药丸少女秋装假俩件加厚卫衣帽衫  预售","product_id":"10136"},{"cover_price":"329.00","figure":"/supplier/1467702094592.jpg","name":"【wacom】数位板画板ctl471手绘板bamboo电脑绘画电子绘图板ps","product_id":"7752"}],"recommend_info":[{"cover_price":"138.00","figure":"/supplier/1478873740576.jpg","name":"【尚硅谷】日常 萌系小天使卫衣--白色款","product_id":"10659"},{"cover_price":"138.00","figure":"/supplier/1478873369497.jpg","name":"【尚硅谷】日常 萌系小恶魔卫衣--黑色款","product_id":"10658"},{"cover_price":"32.00","figure":"/supplier/1478867468462.jpg","name":"预售【漫友文化】全职高手6 天闻角川  流地徽章 全新典藏版 蝴蝶蓝 猫树绘 赠精美大海报+首刷限定赠2017年活页台历","product_id":"10657"},{"cover_price":"18.00","figure":"/1478860081305.jpg","name":"【幸运星】烫金雪纺JSK的配件小物：手 套、项链","product_id":"10656"},{"cover_price":"178.00","figure":"/1478850234799.jpg","name":"【尚硅谷】妖狐图腾 阴阳师同人元素卫衣","product_id":"10655"},{"cover_price":"138.00","figure":"/1478849792177.jpg","name":"【尚硅谷】学院风 日常百搭 宽松长袖衬衫","product_id":"10654"}],"seckill_info":{"end_time":"1479052800","list":[{"cover_price":"20.00","figure":"/1478489000522.png","name":"尚硅谷购物节特供优惠券  满600-120优惠券","origin_price":"20.00","product_id":"7100"},{"cover_price":"10.00","figure":"/1478489035167.png","name":"尚硅谷购物节特供优惠券  满300-80优惠券","origin_price":"10.00","product_id":"7101"},{"cover_price":"5.00","figure":"/1478489878735.png","name":"尚硅谷购物节特供优惠券  满160-40优惠券","origin_price":"5.00","product_id":"7102"},{"cover_price":"49.00","figure":"/1475045805488.jpg","name":"【古风原创】 自动直柄伞 晴雨伞 【云鹤游】包邮  新增折叠伞","origin_price":"69.00","product_id":"9593"},{"cover_price":"5.00","figure":"/1478678511949.png","name":"尚硅谷购物节特供优惠券  满60-20优惠券","origin_price":"5.00","product_id":"10536"},{"cover_price":"49.00","figure":"/1438680345318.jpg","name":"【古风原创】 自动直柄伞 晴雨伞 【青竹词】包邮  新增折叠伞","origin_price":"59.00","product_id":"555"}],"start_time":"1478772000"}}
     */

    public int code;
    public String msg;
    public ResultBean result;

    public static class ResultBean {
        /**
         * act_info : [{"icon_url":"/operation/img/1478169868/1478761370286.png","name":"尚硅谷福利专区之111.1专区","url":"/oper/1478169868app.html"},{"icon_url":"/operation/img/1478763176/1478762941492.png","name":"尚硅谷福利专区 黄金狗粮限量11.1元抢","url":"/oper/1478763176app.html"}]
         * banner_info : [{"image":"/1478770583834.png","option":3,"type":0,"value":{"url":"/act20161111?cyc_app=1"}},{"image":"/1478770583835.png","option":2,"type":0,"value":{"url":"/act20161111?cyc_app=1"}},{"image":"/1478770583836.png","option":1,"type":0,"value":{"url":"/act20161111?cyc_app=1"}}]
         * channel_info : [{"channel_name":"服饰","image":"/app/img/menu-cyc.png","option":2,"type":1,"value":{"channel_id":"8"}},{"channel_name":"游戏","image":"/app/img/menu-game.png","option":2,"type":1,"value":{"channel_id":"4"}},{"channel_name":"动漫","image":"/app/img/menu-carttoon.png","option":2,"type":1,"value":{"channel_id":"3"}},{"channel_name":"装扮","image":"/app/img/menu-cosplay.png","option":2,"type":1,"value":{"channel_id":"5"}},{"channel_name":"古风","image":"/app/img/menu-oldage.png","option":2,"type":1,"value":{"channel_id":"6"}},{"channel_name":"漫展票务","image":"/app/img/menu-collect.png","option":2,"type":1,"value":{"channel_id":"9"}},{"channel_name":"文具","image":"/app/img/menu-stationery.png","option":2,"type":1,"value":{"channel_id":"11"}},{"channel_name":"零食","image":"/app/img/menu-snack.png","option":2,"type":1,"value":{"channel_id":"10"}},{"channel_name":"首饰","image":"/app/img/menu-jewelry.png","option":2,"type":1,"value":{"channel_id":"12"}},{"channel_name":"更多","image":"/app/img/menu-more.png","option":6,"type":1,"value":{"channel_id":"13"}}]
         * hot_info : [{"cover_price":"159.00","figure":"/1477984921265.jpg","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪","product_id":"9356"},{"cover_price":"159.00","figure":"/1477984931882.jpg","name":"现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪-特典版","product_id":"10391"},{"cover_price":"29.00","figure":"/1452161899947.jpg","name":"【喵鹿酱】超萌 假透肉 拼接 踩脚过膝打底袜 裤袜-加绒保暖","product_id":"3831"},{"cover_price":"199.00","figure":"/1447232577216.jpg","name":"【漫踪】原创 宫崎骏 龙猫 可爱雪地靴动漫保暖鞋周边冬季毛绒鞋子","product_id":"2691"},{"cover_price":"70.00","figure":"/1474370572805.jpg","name":"【现货】【GIRLISM少女主义】 第4期 2016夏秋刊 lolita","product_id":"9414"},{"cover_price":"4.80","figure":"/1465268743242.jpg","name":"【艾漫】全职高手-蜜饯系列","product_id":"6869"},{"cover_price":"143.10","figure":"/1477360350123.png","name":"【高冷猫】暗黑系软妹病娇药丸少女秋装假俩件加厚卫衣帽衫  预售","product_id":"10136"},{"cover_price":"329.00","figure":"/supplier/1467702094592.jpg","name":"【wacom】数位板画板ctl471手绘板bamboo电脑绘画电子绘图板ps","product_id":"7752"}]
         * recommend_info : [{"cover_price":"138.00","figure":"/supplier/1478873740576.jpg","name":"【尚硅谷】日常 萌系小天使卫衣--白色款","product_id":"10659"},{"cover_price":"138.00","figure":"/supplier/1478873369497.jpg","name":"【尚硅谷】日常 萌系小恶魔卫衣--黑色款","product_id":"10658"},{"cover_price":"32.00","figure":"/supplier/1478867468462.jpg","name":"预售【漫友文化】全职高手6 天闻角川  流地徽章 全新典藏版 蝴蝶蓝 猫树绘 赠精美大海报+首刷限定赠2017年活页台历","product_id":"10657"},{"cover_price":"18.00","figure":"/1478860081305.jpg","name":"【幸运星】烫金雪纺JSK的配件小物：手 套、项链","product_id":"10656"},{"cover_price":"178.00","figure":"/1478850234799.jpg","name":"【尚硅谷】妖狐图腾 阴阳师同人元素卫衣","product_id":"10655"},{"cover_price":"138.00","figure":"/1478849792177.jpg","name":"【尚硅谷】学院风 日常百搭 宽松长袖衬衫","product_id":"10654"}]
         * seckill_info : {"end_time":"1479052800","list":[{"cover_price":"20.00","figure":"/1478489000522.png","name":"尚硅谷购物节特供优惠券  满600-120优惠券","origin_price":"20.00","product_id":"7100"},{"cover_price":"10.00","figure":"/1478489035167.png","name":"尚硅谷购物节特供优惠券  满300-80优惠券","origin_price":"10.00","product_id":"7101"},{"cover_price":"5.00","figure":"/1478489878735.png","name":"尚硅谷购物节特供优惠券  满160-40优惠券","origin_price":"5.00","product_id":"7102"},{"cover_price":"49.00","figure":"/1475045805488.jpg","name":"【古风原创】 自动直柄伞 晴雨伞 【云鹤游】包邮  新增折叠伞","origin_price":"69.00","product_id":"9593"},{"cover_price":"5.00","figure":"/1478678511949.png","name":"尚硅谷购物节特供优惠券  满60-20优惠券","origin_price":"5.00","product_id":"10536"},{"cover_price":"49.00","figure":"/1438680345318.jpg","name":"【古风原创】 自动直柄伞 晴雨伞 【青竹词】包邮  新增折叠伞","origin_price":"59.00","product_id":"555"}],"start_time":"1478772000"}
         */

        public SeckillInfoBean seckill_info;
        public List<ActInfoBean> act_info;
        public List<BannerInfoBean> banner_info;
        public List<ChannelInfoBean> channel_info;
        public List<HotInfoBean> hot_info;
        public List<RecommendInfoBean> recommend_info;
        public static class SeckillInfoBean {
            /**
             * end_time : 1479052800
             * list : [{"cover_price":"20.00","figure":"/1478489000522.png","name":"尚硅谷购物节特供优惠券  满600-120优惠券","origin_price":"20.00","product_id":"7100"},{"cover_price":"10.00","figure":"/1478489035167.png","name":"尚硅谷购物节特供优惠券  满300-80优惠券","origin_price":"10.00","product_id":"7101"},{"cover_price":"5.00","figure":"/1478489878735.png","name":"尚硅谷购物节特供优惠券  满160-40优惠券","origin_price":"5.00","product_id":"7102"},{"cover_price":"49.00","figure":"/1475045805488.jpg","name":"【古风原创】 自动直柄伞 晴雨伞 【云鹤游】包邮  新增折叠伞","origin_price":"69.00","product_id":"9593"},{"cover_price":"5.00","figure":"/1478678511949.png","name":"尚硅谷购物节特供优惠券  满60-20优惠券","origin_price":"5.00","product_id":"10536"},{"cover_price":"49.00","figure":"/1438680345318.jpg","name":"【古风原创】 自动直柄伞 晴雨伞 【青竹词】包邮  新增折叠伞","origin_price":"59.00","product_id":"555"}]
             * start_time : 1478772000
             */

            public String end_time;
            public String start_time;
            public List<ListBean> list;



            public static class ListBean {
                /**
                 * cover_price : 20.00
                 * figure : /1478489000522.png
                 * name : 尚硅谷购物节特供优惠券  满600-120优惠券
                 * origin_price : 20.00
                 * product_id : 7100
                 */

                public String cover_price;
                public String figure;
                public String name;
                public String origin_price;
                public String product_id;

            }
        }

        public static class ActInfoBean{
            /**
             * icon_url : /operation/img/1478169868/1478761370286.png
             * name : 尚硅谷福利专区之111.1专区
             * url : /oper/1478169868app.html
             */

            public String icon_url;
            public String name;
            public String url;

        }

        public static class BannerInfoBean{
            /**
             * image : /1478770583834.png
             * option : 3
             * type : 0
             * value : {"url":"/act20161111?cyc_app=1"}
             */

            public String image;
            public int option;
            public int type;
            public ValueBean value;

            public static class ValueBean {
                /**
                 * url : /act20161111?cyc_app=1
                 */

                public String url;
            }
        }

        public static class ChannelInfoBean {
            /**
             * channel_name : 服饰
             * image : /app/img/menu-cyc.png
             * option : 2
             * type : 1
             * value : {"channel_id":"8"}
             */

            public String channel_name;
            public String image;
            public int option;
            public int type;
            public ValueBeanX value;

            public static class ValueBeanX {
                /**
                 * channel_id : 8
                 */

                public String channel_id;
            }
        }

        public static class HotInfoBean {
            /**
             * cover_price : 159.00
             * figure : /1477984921265.jpg
             * name : 现货【一方尘寰】剑侠情缘三剑三七秀 干将莫邪 90橙武仿烧蓝复古对簪
             * product_id : 9356
             */

            public String cover_price;
            public String figure;
            public String name;
            public String product_id;
        }

        public static class RecommendInfoBean {
            /**
             * cover_price : 138.00
             * figure : /supplier/1478873740576.jpg
             * name : 【尚硅谷】日常 萌系小天使卫衣--白色款
             * product_id : 10659
             */

            public String cover_price;
            public String figure;
            public String name;
            public String product_id;
        }
    }
}
