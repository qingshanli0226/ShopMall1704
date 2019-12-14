package com.example.administrator.shaomall.message;

import com.shaomall.framework.base.BaseHolder;
import com.shaomall.framework.base.BaseListAdapter;
import com.shaomall.framework.bean.MessageBean;

import java.util.List;

public class IsReadAdapter extends BaseListAdapter<MessageBean> {
    public IsReadAdapter(List<MessageBean> datas) {
        super(datas);
    }

    @Override
    protected BaseHolder<MessageBean> geHolder() {
        return new IsReadHolder();
    }
}
