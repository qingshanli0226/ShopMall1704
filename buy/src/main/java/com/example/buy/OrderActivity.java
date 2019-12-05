package com.example.buy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.presenter.PayOrderPresenter;
import com.example.buy.presenter.SendOrderPresenter;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;

import java.util.ArrayList;

public class OrderActivity extends BaseNetConnectActivity {

    public final static String ALL="全部订单";
    public final static String WAIT_PAY="待支付订单";
    public final static String WAIT_SEND="待发货订单";

    private RecyclerView recyclerView;
    private TextView showOrderType;

    ArrayList<String> list = new ArrayList<>();

    IPresenter payOrder;
    IPresenter sendOrder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_showorder;
    }

    @Override
    public int getRelativeLayout() {
        return 0;
    }

    @Override
    public void init() {
        payOrder=new PayOrderPresenter();
        sendOrder=new SendOrderPresenter();
        //http://49.233.93.155:8080  updateMoney  money=1333
        //获取传递过来的数据,然后进行订单类型的显示

        payOrder.attachView(this);
        sendOrder.attachView(this);
    }

    @Override
    public void initDate() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<String>(R.layout.item_order, list) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {

            }
        });
        payOrder.onHttpPostRequest(100);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent=getIntent();
        String type = intent.getStringExtra(IntentUtil.ORDER_SHOW);
        showOrderType.setText(type);
        switch (type){
            case ALL:

                break;
            case WAIT_PAY:break;
            case WAIT_SEND:break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        payOrder.detachView();
        sendOrder.detachView();
    }
}
