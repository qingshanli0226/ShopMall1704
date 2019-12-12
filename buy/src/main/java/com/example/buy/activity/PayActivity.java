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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.buy.R;
import com.example.buy.databeans.GetCheckGoodsBean;
import com.example.buy.databeans.GetPayOrderBean;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.buy.databeans.PayResult;
import com.example.buy.databeans.PayResultMessBean;
import com.example.buy.databeans.SendOrdersBean;
import com.example.buy.databeans.SendPayResultBean;
import com.example.buy.presenter.PostOrderPresenter;
import com.example.buy.presenter.PostPayResultPresenter;
import com.example.buy.presenter.PostUpDateMoneyPresenter;
import com.example.buy.presenter.PostUpDatePointPresenter;
import com.example.buy.presenter.PostVerifyGoodsPresenter;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.manager.AccountManager;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * 跳转过来,进行库存验证,不足则提示
 * 充足则提交订单  订单完成
 *
 * 支付完成,更新现金和积分  再退出
 * */
public class PayActivity extends BaseNetConnectActivity implements View.OnClickListener {

    public static final int PAY = 200;
    public static final int CODE_ORDER = 100;
    public static final int CODE_PAY_SURE = 101;
    public static final int COED_VERIFY = 300;
    public static final int COED_MONEY = 400;
    public static final int COED_POINT = 500;

    private Button payBut;
    private RecyclerView recyclerView;
    private TextView orderMoney;
    private TextView payMoney;
    private TextView subtractIntegra;
    private CheckBox checkInegra;

    private ArrayList<GoodsBean> list = new ArrayList<>();
    //订单 支付确认  库存  积分 现金
    private IPresenter sendOrederPresenter;
    private IPresenter postOrderPresenter;
    private IPresenter verifyPresenter;
    private IPresenter pointPresenter;
    private IPresenter moneyPresenter;

    private GetPayOrderBean getPayOrderBean;


    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PAY:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String result = payResult.getResult();// 同步返回需要验证的信息
                    PayResultMessBean payResultMessBean = new Gson().fromJson(result, PayResultMessBean.class);
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                        postOrderPresenter = new PostPayResultPresenter(
                                new SendPayResultBean(getPayOrderBean.getResult().getOutTradeNo(),
                                        payResultMessBean.toString(),
                                        true));
                        postOrderPresenter.attachView(PayActivity.this);
                        postOrderPresenter.doHttpPostJSONRequest(CODE_PAY_SURE);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(PayActivity.this, "支付失败:" + payResult, Toast.LENGTH_SHORT).show();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        list.addAll(intent.getParcelableArrayListExtra(IntentUtil.ORDERS));
        //设置数据
        recyclerView.getAdapter().notifyDataSetChanged();

        orderMoney.setText("原价: "+getMoney(false));
        payMoney.setText(getMoney(false));
        //库存检测
        verifyPresenter = new PostVerifyGoodsPresenter(list);
        verifyPresenter.attachView(this);
        verifyPresenter.doHttpPostJSONRequest(COED_VERIFY);
        //下订单
        List<SendOrdersBean.BodyBean> bodyBeans = new ArrayList<>();
        for (GoodsBean i : list) {
            bodyBeans.add(new SendOrdersBean.BodyBean(i.getProductName(), i.getProductId()));
        }
        //直接发起订单
        SendOrdersBean sendOrdersBean = new SendOrdersBean(
                "购买",
                getMoney(checkInegra.isChecked()),
                bodyBeans
        );
        sendOrederPresenter = new PostOrderPresenter(sendOrdersBean);
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
                    Map<String, String> result = alipay.payV2(orderInfo, true);

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
        checkInegra = findViewById(R.id.checkInegra);
        subtractIntegra = findViewById(R.id.subtractIntegra);
        //http://49.233.93.155:8080  updateMoney  money=1333
        payBut.setOnClickListener(this);

        //设置RecyclerView
        BaseRecyclerAdapter<GoodsBean> adapter = new BaseRecyclerAdapter<GoodsBean>(R.layout.item_pay, list) {
            @Override
            public void onBind(BaseViewHolder holder, final int position) {
                holder.getImageView(R.id.itemPayImg, AppNetConfig.BASE_URl_IMAGE + list.get(position).getUrl());
                holder.getTextView(R.id.itemPayTitle, list.get(position).getProductName());
                holder.getTextView(R.id.itemPayNum, list.get(position).getProductNum() + "");
                holder.getTextView(R.id.itemPayPrice, list.get(position).getProductPrice());
                holder.getTextView(R.id.itemOneSum, Float.valueOf(list.get(position).getProductPrice()) * list.get(position).getProductNum() + "");
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        //减免状态
        checkInegra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                payMoney.setText(getMoney(isChecked));
            }
        });
    }

    @Override
    public void initDate() {
        //沙箱接入
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        if (AccountManager.getInstance().user.getPoint()==null){
            subtractIntegra.setText("0");
        }else {
            subtractIntegra.setText(AccountManager.getInstance().user.getPoint()+"");
        }
    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode) {
            case CODE_ORDER:
                getPayOrderBean = (GetPayOrderBean) data;
                if (getPayOrderBean.getCode().equals(AppNetConfig.CODE_OK)) {
                    //下订单成功

                }
                break;
            case CODE_PAY_SURE:
                OkBean okBean = (OkBean) data;
                if (okBean.getCode().equals(AppNetConfig.CODE_OK)) {
                    //更新现金
                    moneyPresenter=new PostUpDateMoneyPresenter(getMoney(checkInegra.isChecked()));
                    moneyPresenter.attachView(this);
                    moneyPresenter.doHttpPostRequest(COED_MONEY);
                }
                break;
            case COED_VERIFY:
                if (((GetCheckGoodsBean)data).equals(AppNetConfig.CODE_OK)){

                }
                break;
            case COED_MONEY:
                if (((OkBean)data).getCode().equals(AppNetConfig.CODE_OK)){
                    pointPresenter=new PostUpDatePointPresenter("123");
                    pointPresenter.attachView(this);
                    pointPresenter.doHttpPostRequest(COED_POINT);
                }
                break;
            case COED_POINT:
                if (((OkBean)data).getCode().equals(AppNetConfig.CODE_OK)){
                    Log.e("xxxx","用户信息:"+AccountManager.getInstance().user.toString());
                   finishActivity();
                }
                break;
        }
    }

    private String getMoney(boolean pointStatus) {
        int sum = 0;
        for (GoodsBean i : list) {
            sum += Float.valueOf(i.getProductPrice())*i.getProductNum();
        }
        if (pointStatus){
            sum-=Integer.valueOf(subtractIntegra.getText().toString());
        }
        return sum + "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       disPreseter(pointPresenter,postOrderPresenter,moneyPresenter,verifyPresenter,sendOrederPresenter);
    }
    private void disPreseter(IPresenter... iPresenter) {
        for (int i = 0; i < iPresenter.length; i++) {
            if (iPresenter[i] != null) {
                iPresenter[i].detachView();
            }
        }
    }
}
