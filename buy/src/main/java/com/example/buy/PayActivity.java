package com.example.buy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.databeans.GetPayOrderBean;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.SendOrdersBean;
import com.example.buy.presenter.PostOrderPresenter;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;

import java.util.ArrayList;
import java.util.List;

public class PayActivity extends BaseNetConnectActivity implements View.OnClickListener {

    public static final int CODE_ORDER=100;
    private Button payBut;
    private RecyclerView recyclerView;
    private TextView orderMoney;
    private TextView payMoney;

    ArrayList<GoodsBean> list=new ArrayList<>();

    IPresenter sendOrederPresenter;

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(IntentUtil.ORDERS);
        list = bundle.getParcelableArrayList(IntentUtil.GOODS);
        recyclerView.getAdapter().notifyDataSetChanged();
        orderMoney.setText(getMoney());

        List<SendOrdersBean.BodyBean> bodyBeans=new ArrayList<>();
        for (GoodsBean i:list){
            bodyBeans.add(new SendOrdersBean.BodyBean(i.getProductName(),i.getProductId()));
        }
        //直接发起订单
        SendOrdersBean sendOrdersBean = new SendOrdersBean(
                "购买",
                getMoney(),
                bodyBeans
        );
        Log.e("xxx","发起的订单请求"+sendOrdersBean.toString());
        sendOrederPresenter=new PostOrderPresenter(sendOrdersBean);
        sendOrederPresenter.attachView(this);
        sendOrederPresenter.onHttpPostRequest(CODE_ORDER);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.payBut) {

        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_pay;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.payRel;
    }

    @Override
    public void init() {
        super.init();
        payBut = findViewById(R.id.payBut);
        orderMoney = findViewById(R.id.orderMoney);
        payMoney = findViewById(R.id.payMoney);
        recyclerView = findViewById(R.id.recyclerView);
        //http://49.233.93.155:8080  updateMoney  money=1333
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<GoodsBean>(R.layout.item_pay, list) {
            @Override
            public void onBind(BaseViewHolder holder, final int position) {
                holder.getTextView(R.id.itemTitle, list.get(position).getProductName());
                holder.getTextView(R.id.itemNum, list.get(position).getProductNum() + "");
                holder.getTextView(R.id.itemPrice, list.get(position).getProductId());
            }
        });
        payBut.setOnClickListener(this);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode){
            case CODE_ORDER:
                Log.e("xxx","返回订单的东西"+data.toString());
                if (((GetPayOrderBean)data).getCode().equals(AppNetConfig.CODE_OK)){
                    //下订单成功
                    //订单号
                    ((GetPayOrderBean)data).getResult().getOutTradeNo();
                    //拼接的数据 appid 格式之类
                    ((GetPayOrderBean)data).getResult().getOrderInfo();
                }
                break;
        }
    }
    private String getMoney(){
        int sum=0;
        for (GoodsBean i:list){
            sum+=Integer.valueOf(i.getProductId())*i.getProductNum();
        }
        return sum+"";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sendOrederPresenter.detachView();
    }
}
