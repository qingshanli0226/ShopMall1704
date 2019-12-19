package com.example.buy.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.R;
import com.example.buy.databeans.GetOrderBean;
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

import java.util.ArrayList;

/**
 * 订单详情页
 */
public class OrderActivity extends BaseNetConnectActivity {

    //网络请求码 全部  待支付 待发货 第二次
    public final static int CODE_ALL = 200;
    public final static int CODE_PAY = 201;
    public final static int CODE_WAIT = 202;
    public final static int CODE_All_TWO = 203;

    private RecyclerView recyclerView;
    private TextView showOrderType;
    private MyToolBar myToolBar;

    ArrayList<GetOrderBean> list = new ArrayList<>();

    IPresenter payOrder;
    IPresenter watiOrder;

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
        recyclerView=findViewById(R.id.recyclerView);

        myToolBar = findViewById(R.id.myToolBar);
        myToolBar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
        myToolBar.init(Constant.OTHER_STYLE);
        myToolBar.getOther_back().setImageResource(R.drawable.back3);
        myToolBar.getOther_title().setTextColor(Color.WHITE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<GetOrderBean>(R.layout.item_order, list) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                ((TextView) holder.getView(R.id.orderTitle)).setText(list.get(position).getSubject());
                ((TextView) holder.getView(R.id.orderNum)).setText(list.get(position).getBody());
                ((TextView) holder.getView(R.id.orderMoney)).setText(list.get(position).getTotalPrice());
            }
        });


    }

    @Override
    public void initDate() {
        payOrder = new GetPayOrderPresenter();
        watiOrder = new GetWaitOrderPresenter();
        //http://49.233.93.155:8080  updateMoney  money=1333
        //获取传递过来的数据,然后进行订单类型的显示

        payOrder.attachView(this);
        watiOrder.attachView(this);
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
                payOrder.doHttpGetRequest(CODE_PAY);
                break;
            case Constant.WAIT_SEND:
                watiOrder.doHttpGetRequest(CODE_WAIT);
                break;
        }
    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        Log.e("xxx","订单数据:"+((GetOrderBean) data).toString());
        switch (requestCode) {
            case CODE_ALL:
                list.add((GetOrderBean) data);
                watiOrder.doHttpGetRequest(CODE_All_TWO);
                break;
            case CODE_PAY:
                list.clear();
                list.add((GetOrderBean) data);
                OrderManager.getInstance().updatePayOrederNum(list.size());
                break;
            case CODE_WAIT:
                list.clear();
                list.add((GetOrderBean) data);
                OrderManager.getInstance().updateWaitOrederNum(list.size());
                break;
            case CODE_All_TWO:
                list.add((GetOrderBean) data);
                OrderManager.getInstance().updateAllOrederNum(list.size());
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        disPreseter(payOrder, watiOrder);
    }

    private void disPreseter(IPresenter... iPresenter) {
        for (int i = 0; i < iPresenter.length; i++) {
            if (iPresenter[i] != null) {
                iPresenter[i].detachView();
            }
        }
    }
}
