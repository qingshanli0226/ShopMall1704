package com.example.buy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.R;
import com.example.buy.databeans.GetOrderBean;
import com.example.buy.databeans.GetPayBean;
import com.example.buy.presenter.GetPayOrderPresenter;
import com.example.buy.presenter.GetWaitOrderPresenter;
import com.example.common.utils.IntentUtil;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.manager.OrderManager;
import com.example.framework.port.IPresenter;
import com.example.framework.port.OnClickItemListener;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 订单详情页
 */
public class OrderActivity extends BaseNetConnectActivity {

    //网络请求码 全部 全部的第二次 待支付 待发货
    public final static int CODE_ALL = 200;
    public final static int CODE_All_TWO = 203;
    public final static int CODE_PAY = 201;
    public final static int CODE_WAIT = 202;

    private RecyclerView recyclerView;
    private RecyclerView recyclerViewTwo;
    private MyToolBar myToolBar;
    private LinearLayout body;

    private ArrayList<GetPayBean.ResultBean> list = new ArrayList<>();
    private ArrayList<GetOrderBean.ResultBean> listTwo = new ArrayList<>();

    private IPresenter payOrder;
    private IPresenter waitOrder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_showorder;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.orderRel;
    }

    @Override
    public void init() {
        super.init();
        recyclerView = findViewById(R.id.recyclerView);
        recyclerViewTwo = findViewById(R.id.recyclerViewTwo);
        myToolBar = findViewById(R.id.myToolBar);
        body = findViewById(R.id.body);

        myToolBar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        myToolBar.init(Constant.OTHER_STYLE);
        myToolBar.getOther_back().setImageResource(R.drawable.back3);
        myToolBar.getOther_title().setTextColor(Color.WHITE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        BaseRecyclerAdapter<GetPayBean.ResultBean> recyclerAdapter = new BaseRecyclerAdapter<GetPayBean.ResultBean>(R.layout.item_order_pay, list) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                String time = DateFormat.format("MM月dd日    HH时mm分", Long.valueOf(list.get(position).getTime())) + "";
                ((TextView) holder.getView(R.id.orderNum)).setText("时间: " + time);
                ((TextView) holder.getView(R.id.orderMoney)).setText("价格(¥): " + list.get(position).getTotalPrice());
                ((TextView) holder.getView(R.id.orderTrade)).setText("订单编号: " + list.get(position).getTradeNo());
            }
        };
        recyclerAdapter.setClickListener(new OnClickItemListener() {
            @Override
            public void onClickListener(int position) {
                if (list.get(position).getOrderInfo()!=null){
                    Intent intent=new Intent(OrderActivity.this,PayActivity.class);
                    intent.putExtra(IntentUtil.WAIT_PAY_NO,list.get(position).getTradeNo());
                    intent.putExtra(IntentUtil.WAIT_PAY_INFO,(String)list.get(position).getOrderInfo());
                    boundActivity(intent);
                }else {
                    toast(OrderActivity.this,"该订单已经失效");
                }
            }
        });
        recyclerView.setAdapter(recyclerAdapter);

        recyclerViewTwo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerViewTwo.setAdapter(new BaseRecyclerAdapter<GetOrderBean.ResultBean>(R.layout.item_order_wait, listTwo) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                String time = DateFormat.format("MM月dd日    HH时mm分",Long.valueOf(listTwo.get(position).getTime()))+"";
                ((TextView) holder.getView(R.id.orderNum)).setText("时间: "+time);
                ((TextView) holder.getView(R.id.orderMoney)).setText("价格(¥): "+listTwo.get(position).getTotalPrice());
                ((TextView) holder.getView(R.id.orderTrade)).setText("订单编号: "+listTwo.get(position).getTradeNo());
            }
        });

    }

    @Override
    public void initDate() {
        payOrder = new GetPayOrderPresenter();
        waitOrder = new GetWaitOrderPresenter();

        payOrder.attachView(this);
        waitOrder.attachView(this);
        myToolBar.getOther_back().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishActivity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取到要展示的订单的类型
        Intent intent = getIntent();
        Bundle data = intent.getBundleExtra("data");
        String type = data.getString(IntentUtil.ORDER_SHOW);
        myToolBar.getOther_title().setText(type);
        switch (type) {
            case Constant.ALL_ORDER:
                //先请求待支付,然后请求待发货
                payOrder.doHttpGetRequest(CODE_ALL);
                break;
            case Constant.WAIT_PAY:
                recyclerViewTwo.setVisibility(View.GONE);
                body.setVisibility(View.GONE);
                payOrder.doHttpGetRequest(CODE_PAY);
                break;
            case Constant.WAIT_SEND:
                recyclerView.setVisibility(View.GONE);
                body.setVisibility(View.GONE);
                waitOrder.doHttpGetRequest(CODE_WAIT);
                break;
        }
    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode) {
            case CODE_ALL:
                list.addAll(((GetPayBean) data).getResult());
                Collections.reverse(list);
                waitOrder.doHttpGetRequest(CODE_All_TWO);
                break;
            case CODE_All_TWO:
                listTwo.addAll(((GetOrderBean) data).getResult());
                Collections.reverse(listTwo);
                OrderManager.getInstance().updateAllOrederNum(listTwo.size());
                break;
            case CODE_PAY:
                if (((GetPayBean) data).getCode().equals(Constant.CODE_OK)){
                    list.clear();
                    list.addAll(((GetPayBean) data).getResult());
                    Collections.reverse(list);
                    OrderManager.getInstance().updatePayOrederNum(list.size());
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                break;
            case CODE_WAIT:
                listTwo.clear();
                listTwo.addAll(((GetOrderBean) data).getResult());
                Collections.reverse(listTwo);
                OrderManager.getInstance().updateWaitOrederNum(listTwo.size());
                recyclerViewTwo.getAdapter().notifyDataSetChanged();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disPreseter(payOrder, waitOrder);
    }

    private void disPreseter(IPresenter... iPresenter) {
        for (int i = 0; i < iPresenter.length; i++) {
            if (iPresenter[i] != null) {
                iPresenter[i].detachView();
            }
        }
    }

}
