package com.example.buy;

import com.example.framework.base.BaseActivity;

public class CollectActivity extends BaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    public void init() {

    }

    @Override
    public void initDate() {
        //sp读取收藏的物品名称,然后进行网络请求,接着展示
        /**
         * 13, "search"
         * 说明：搜索数据的接口
         * GET
         * 请求参数：
         * 参数格式：application/x-www-form-urlencoded
         * 示例：name=chenshan(使用拼音或者英文，暂不支持中文)
         *
         * 返回值:
         * 返回格式：application/json, text/json
         * 示例：
         * */

    }
}
