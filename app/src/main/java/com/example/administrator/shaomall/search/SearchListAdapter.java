package com.example.administrator.shaomall.search;

import com.shaomall.framework.base.BaseHolder;
import com.shaomall.framework.base.BaseListAdapter;
import com.shaomall.framework.bean.SearchBean;

import java.util.List;

public class SearchListAdapter extends BaseListAdapter<SearchBean.HotProductListBean> {
    public SearchListAdapter(List<SearchBean.HotProductListBean>datas) {
        super(datas);
    }

    @Override
    protected BaseHolder<SearchBean.HotProductListBean> geHolder() {
        return new SearchHolder();
    }
}
