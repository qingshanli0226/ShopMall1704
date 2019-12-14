package com.shaomall.framework.base;


import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import java.util.List;

public abstract class IBaseRecyclerAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    public IBaseRecyclerAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }
}
