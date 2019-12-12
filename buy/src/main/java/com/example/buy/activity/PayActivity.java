package com.example.buy.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.buy.R;
import com.example.buy.databeans.GetPayOrderBean;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.buy.databeans.PayResult;
import com.example.buy.databeans.PayResultMessBean;
import com.example.buy.databeans.SendOrdersBean;
import com.example.buy.databeans.SendPayResultBean;
import com.example.buy.presenter.PostOrderPresenter;
import com.example.buy.presenter.PostPayResultPresenter;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PayActivity extends BaseNetConnectActivity implements View.OnClickListener {

    public static final int PAY=200;

    public static final int CODE_ORDER=100;
    public static final int CODE_PAY_SURE=101;

    private Button payBut;
    private RecyclerView recyclerView;
    private TextView orderMoney;
    private TextView payMoney;

    ArrayList<GoodsBean> list=new ArrayList<>();

    IPresenter sendOrederPresenter;
    IPresenter postOrderPresenter;

    GetPayOrderBean getPayOrderBean;

    @SuppressLint("HandlerLeak")
    Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PAY:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String result = payResult.getResult();// 同步返回需要验证的信息
                    PayResultMessBean payResultMessBean = new Gson().fromJson(result, PayResultMessBean.class);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Log.e("xxx","支付结果"+payResult.toString());
                        Toast.makeText(PayActivity.this,"支付成功",Toast.LENGTH_SHORT).show();
                        postOrderPresenter=new PostPayResultPresenter(
                                new SendPayResultBean(getPayOrderBean.getResult().getOutTradeNo(),
                                        payResultMessBean.toString(),
                                        true)
                        );
                        postOrderPresenter.attachView(PayActivity.this);
                        postOrderPresenter.doHttpPostJSONRequest(CODE_PAY_SURE);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this,"支付失败:"+payResult,Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        list = intent.getParcelableArrayListExtra(IntentUtil.ORDERS);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<GoodsBean>(R.layout.item_pay, list) {
            @Override
            public void onBind(BaseViewHolder holder, final int position) {
                holder.getImageView(R.id.itemPayImg,AppNetConfig.BASE_URl_IMAGE+list.get(position).getUrl());
                holder.getTextView(R.id.itemPayTitle, list.get(position).getProductName());
                holder.getTextView(R.id.itemPayNum, list.get(position).getProductNum() + "");
                holder.getTextView(R.id.itemPayPrice, list.get(position).getProductId());
            }
        });
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
        sendOrederPresenter.doHttpPostJSONRequest(CODE_ORDER);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.payBut) {
            final String orderInfo = (getPayOrderBean.getResult().getOrderInfo());   // 订单信息

            // 必须异步调用
            new Thread(new Runnable() {

                @Override
                public void run() {
                    PayTask alipay = new PayTask(PayActivity.this);
                    Map<String,String> result = alipay.payV2(orderInfo,true);

                    Message msg = new Message();
                    msg.what = PAY;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            }).start();
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
        //沙箱接入
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
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
                getPayOrderBean= (GetPayOrderBean) data;
                Log.e("xxx","返回订单的东西"+data.toString());
                if (getPayOrderBean.getCode().equals(AppNetConfig.CODE_OK)){
                    //下订单成功

                }
                break;
            case CODE_PAY_SURE:
                OkBean okBean= (OkBean) data;
                if (okBean.getCode().equals(AppNetConfig.CODE_OK)) {
                    finishActivity();
                }
                break;
        }
    }
    private String getMoney(){
        int sum=0;
        for (GoodsBean i:list){
            sum+=Float.valueOf(i.getProductPrice());
        }
        return sum+"";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sendOrederPresenter!=null){
            sendOrederPresenter.detachView();
        }
    }
}
