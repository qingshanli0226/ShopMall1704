package com.example.buy.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.buy.R;
import com.example.buy.bean.PayBean;
import com.example.buy.bean.PayResultBean;
import com.example.buy.presenter.PayPresenter;
import com.example.common.SignUtil;
import com.example.common.view.MyOKButton;
import com.example.framework.base.IPostBaseView;
import com.example.framework.manager.MessageManager;
import com.example.framework.manager.ShoppingManager;
import com.example.buy.adapter.MyShoppingOrderAdapter;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.google.gson.GsonBuilder;

import org.json.JSONException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends BaseActivity implements IPostBaseView {

    TitleBar tbOrder;
    TextView tvShopcartTotal;
    RecyclerView rvOrder;
    MyOKButton btnPay;
    ShoppingManager shoppingManager;
    private List<Map<String, String>> data;
    private MyShoppingOrderAdapter myShoppingOrderAdapter;
    private PayPresenter payPresenter;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //todo 验证支付结果信息.
                case 1:
                    PayMessage payMessage = (PayMessage) (msg.obj);
                    String outTradeNo = payMessage.getOutTradeNo();
                    Map<String, String> result = payMessage.getResult();

                    String resultStatus = result.get("resultStatus");
                    String resultContent = result.get("result");
                    boolean payResultIsOk = false;
                    if (resultStatus.equals("9000")) {
                        payResultIsOk = true;
                    }
                    confirmServerPayResult(outTradeNo, resultContent, payResultIsOk);

                    break;
                default:
                    break;
            }
        }
    };

    private double allMoney;
    private PayPresenter payPresenter2;

    @Override
    protected int setLayout() {
        return R.layout.activity_order;
    }

    private static void showAlert(Context ctx, String info) {
        showAlert(ctx, info, null);
    }

    private static void showAlert(Context ctx, String info, DialogInterface.OnDismissListener onDismiss) {
        new AlertDialog.Builder(ctx)
                .setMessage(info)
                .setPositiveButton(R.string.confirm, null)
                .setOnDismissListener(onDismiss)
                .show();
    }

    @Override
    public void initView() {
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        tbOrder = findViewById(R.id.tb_buy_order);
        rvOrder = findViewById(R.id.rv_buy_order);
        tvShopcartTotal = findViewById(R.id.tv_buy_shopcartTotal);
        btnPay = findViewById(R.id.btn_buy_pay);

        initRecycler();
        initPayButton();
    }

    private void initPayButton() {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String token = ShoppingManager.getInstance().getToken(OrderActivity.this);
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("token", token);

                JSONObject jsonObject1 = new JSONObject();

                JSONArray array = new JSONArray();
                for (int i = 0; i < data.size(); i++) {
                    Map<String, String> map = data.get(i);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("productId", map.get("id"));
                    jsonObject.put("productName", map.get("title"));
                    array.add(jsonObject);
                }

                jsonObject1.put("subject", "buy");
                jsonObject1.put("totalPrice", shoppingManager.getAllMoney() + "");
                jsonObject1.put("body", array);
                jsonObject1.put("sign", SignUtil.generateJsonSign(jsonObject1));

                SignUtil.encryptJsonParamsByBase64(jsonObject1);

                payPresenter = new PayPresenter("getOrderInfo", PayBean.class, hashMap, jsonObject1);
                payPresenter.attachPostView(OrderActivity.this);
                payPresenter.getPostJsonData();

            }
        });
    }

    /**
     * 支付宝支付
     */
    public void payV2(final PayBean.ResultBean resultBean) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(OrderActivity.this);
                //调用支付宝API，发起支付。第一个参数是服务端生成的订单信息。第二个参数是显示加载过程.
                Map<String, String> result = payTask.payV2(resultBean.getOrderInfo(), true);
                PayMessage payMessage = new PayMessage();
                payMessage.setOutTradeNo(resultBean.getOutTradeNo()); //将订单号存在paymessage里.
                payMessage.setResult(result);//将支付宝的支付结果存在paymessage里.
                Log.d("LQS", "支付结果：" + payMessage.getOutTradeNo());
                Message message = new Message();
                message.what = 1;
                message.obj = payMessage;
                handler.sendMessage(message);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    private void confirmServerPayResult(String outTradeNo, String resultContent, final boolean payResultIsOk) {
        String token = ShoppingManager.getInstance().getToken(OrderActivity.this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("outTradeNo", outTradeNo);
        jsonObject.put("result", resultContent);
        jsonObject.put("clientPayResult", payResultIsOk);
        jsonObject.put("sign", SignUtil.generateJsonSign(jsonObject));

        SignUtil.encryptJsonParamsByBase64(jsonObject);

        payPresenter2 = new PayPresenter("confirmServerPayResult", PayResultBean.class, hashMap, jsonObject);
        payPresenter2.attachPostView(new IPostBaseView() {
            @Override
            public void onPostDataSucess(Object data) {
                if (payResultIsOk) {
                    Log.e("####", "付款成功");
                    startActivity(new Intent(OrderActivity.this, ShoppingResultActivity.class));
                    List<Map<String, String>> buyThings = ShoppingManager.getInstance().getBuyThings();
                    for (int i = 0; i < buyThings.size(); i++) {
                        Map<String, String> map = buyThings.get(i);
                        MessageManager.getInstance().setMessageManager(map.get("title"),"购买成功");
                    }
                    finish();
                }
            }

            @Override
            public void onPostDataFailed(String ErrorMsg) {

            }
        });
        payPresenter2.getPostJsonData();
    }

    private void initTitlebar() {
        tbOrder.setCenterText("支付订单", 20, Color.RED);
        tbOrder.setRightText("取消", 13, Color.BLACK);
        tbOrder.setBackgroundColor(Color.WHITE);

        tbOrder.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {
                finish();
            }

            @Override
            public void CenterClick() {

            }
        });
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rvOrder.setLayoutManager(manager);
        myShoppingOrderAdapter = new MyShoppingOrderAdapter(this);
        rvOrder.setAdapter(myShoppingOrderAdapter);
    }

    @Override
    public void initData() {
        initTitlebar();
        initShoppingData();
        setAllMoney();
    }

    private void setAllMoney() {
        allMoney = shoppingManager.getAllMoney();
        String text = "￥" + allMoney + "0";
        tvShopcartTotal.setText(text);
    }

    @Override
    public void onPostDataSucess(Object data) {
        Log.e("####", data.toString());
        PayBean payBean = (PayBean) data;
        PayBean.ResultBean result = payBean.getResult();
        if (result != null) {
            payV2(result);
        }
    }

    private void initShoppingData() {
        shoppingManager = ShoppingManager.getInstance();
        data = shoppingManager.getBuyThings();
        myShoppingOrderAdapter.reFresh(data);
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }

    private class PayMessage {
        private Map<String, String> result;//第一个参数，存放支付宝返回的结果信息
        private String outTradeNo;//第二个参数是服务端生成订单号

        public Map<String, String> getResult() {
            return result;
        }

        public void setResult(Map<String, String> result) {
            this.result = result;
        }

        public String getOutTradeNo() {
            return outTradeNo;
        }

        public void setOutTradeNo(String outTradeNo) {
            this.outTradeNo = outTradeNo;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(payPresenter!=null){
            payPresenter.detachView();
        }
        if(payPresenter2!=null){
            payPresenter2.detachView();
        }
    }
}
