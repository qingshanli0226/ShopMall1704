package com.example.dimensionleague.businessbean;

import java.util.List;

public class GetRecommendBean {
    /**
     * code : 200
     * message : 请求成功
     * result : [{"name":"小裙子","url":"SKIRT_URL.json"},{"name":"夹克衫","url":"JACKET_URL.json"},{"name":"包包","url":"BAG_URL.json"}]
     */

    public String code;
    public String message;
    public List<ResultBean> result;

    public static class ResultBean {
        /**
         * name : 小裙子
         * url : SKIRT_URL.json
         */

        public String name;
        public String url;
    }
}
