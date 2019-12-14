package com.example.administrator.shaomall.message;

import android.view.View;
import android.widget.TextView;

import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.app.ShaoHuaApplication;
import com.shaomall.framework.base.BaseHolder;
import com.shaomall.framework.bean.MessageBean;

public class IsReadHolder extends BaseHolder<MessageBean> {
    private TextView mMessageItemTv;
    private TextView MMessageTitle;
    @Override
    protected View initView() {
        View rootView = View.inflate(ShaoHuaApplication.context, R.layout.item_message, null);
        initView(rootView);
        return rootView;
    }

    @Override
    protected void refreshData() {
    mMessageItemTv.setText(getDatas().getMessage());
        MMessageTitle.setText("旧消息");
    }

    private void initView(View view) {
        mMessageItemTv = view.findViewById(R.id.message_item_tv);
        MMessageTitle = view.findViewById(R.id.message_item_title);
    }
}
