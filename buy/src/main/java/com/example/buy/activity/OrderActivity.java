package com.example.buy.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.example.buy.R;
import com.example.buy.ShoppingUtils;
import com.example.buy.adapter.MyShoppingOrderAdapter;
import com.example.buy.pay.AuthResult;
import com.example.buy.pay.PayResult;
import com.example.buy.util.OrderInfoUtil2_0;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;

import java.util.List;
import java.util.Map;

public class OrderActivity extends BaseActivity {

    TitleBar tb_order;
    /**
     * 用于支付宝支付业务的入参 app_id。
     */
    public static final String APPID = "2016101100657052";
    /**
     * 用于支付宝账户登录授权业务的入参 pid。
     */
    public static final String PID = "";
    /**
     * 用于支付宝账户登录授权业务的入参 target_id。
     */
    public static final String TARGET_ID = "";
    /**
     * pkcs8 格式的商户私钥。
     */
    public static final String RSA2_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCbjcvSQFSkUt8OdvjnF7SYJOVuVwSSxIDmbsPiWOH5cHoVx9pmsnivBnMmeGrrzklbRGMuAPu51H/nVTFw8wUsshBbWTgGlGQL4jLZIuSyXVXmQT8+3OjOLckgpBuzjjeaFwaJXBE8ujgMqhrPFlsSrnJ3Ouv1GM+7aG5eNexTQ04XDb87VBamuiMj1vAOFP+bMyFaxZDjfeEsfpzqcLN0bCJrfuyu2VzE0GGeC1u1EGZuF+EgHMrwoBofRoUUTCj+YuWUYGAnObU13W+VpDBS6BQFYBiOSqeehNjOvHa9kkYqq3Uw+gGA0nyGEkHyskK+cB0RtHUVT1pfxNk2bKJNAgMBAAECggEBAIKA8RpBHIIMgNT63ZHL4piug8oRuWWE9Pveu/q/RT7nrZVuaX41h2iYCa2h41pYZFTsyCTFcGuUq4YihGtVh+basTxiWs2yW0lZCf3/90zpX3LhFLWh0rN/2DjJVfBLhbyCStqc4l1HglSltf49rES9FI2RYnrryQRa9hWWOeITxK3TWCf6sUu2OV01ROM0qel7u+1VYfVTVgFKuIDP2Vyebd7M+o1XZr2lQ3sO8bADHtEkQcz2W8curBka6XfkVzS1v+KnHGk023zWy9rvFntJq4BStoCzwq41urEbCX3sFM2+3C/9uJ1vBGf4NR1Tk817yNsZjEgfnZxqmQQ2JeUCgYEA9L44XxHe6Fs7jpiY9c74oNlU1MOejyGIf2r+KtE60maxkbtCiGLuy8jidc7QBwUaWPC5KxXOJe/yqzCosRTp4MuWPkX0y5LJWIMzIhsC6oAA1voegvSDg7S1CslQ4zyXkvjt945APokW1pZXF9JkFDbTbWV0P5Hzt9MjLFpNsBcCgYEAorVmJh5ycRxt6VYxintobNO2NF/VSwCW5q04t3AFCEmVGdHvxqJU4XZrQ6lp885KhuqlRKW7h9XFPiNvF+Re64HBfYkeRaoBEk+HWNFOWQTKnBrw8ryxIACfyI0YzJ+mdxYalTR2iSFeV88ghmL+l31Dg2K7Ht9iS1QhVLymezsCgYEAzoKPSFDo+MC6kUjbkFXy3q5jcSAa84UFCxa5nmFrs8tpO6F5s2FfQ16fD27Rg4rfv3pnlB5TExnTdnWZccpGyiKfBK68ruWNtX0HMVj/beVSUBM+mZs+OWzKcm6KLSOcdixLh15lgR7lwW2b0YL6ZUAZyK4Zl+ZMcqjaffs4oEMCgYEAhGoqOoV2z1S6ojWyVaf/IOnfE5qWQ9az+AEq8iZpAgiYHg47G6VKfL5ZpxP9lRvijQ/y9f4GsxIm1l4R8Bi7ot/Tbu2jRECJZLRySrb0EwASP56xTmp/n7LCVn11nDmlSK8lbR3oBcuxMrbvGk8AMJsR0aAM9qAHJ5TNF1/8ALcCgYB9AW93Xe1WPQhO0UdAtyiY6aIeuzQhGxPtXuw1uqQK/JwpozCm3isTMjTKxMXoHunenkTvfFUIbwO8EFnmweA9Kbtnp0CnH8NSyt+S2PvEyUIqhTHhScOUerAfCAgCPX+182Cm8ieSFZfKC1bRqFmyqvT2DPeQQcKxY54V9CXPdA==";
    public static final String RSA_PRIVATE = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBALoiwYt114PXbh9tC1ymUVOrT4LLGZRG3sZx4cOE8buAn1mdp85RBwztZbX2sKT2nR8eg8MGeGNWjSFgsBCqXXDtN3JLZzDKZJfR58+bDMMkHZ7qKZCbeeilscw7KsrrsEHRduJFqb9ql8OMPfamCHHmwAB17bIMJoPvZ5kYkAjjAgMBAAECgYA1BG9SdNdVE0inpNymoCzsAxCbtZPsmx8bMRbYJQbhnGpHEVPZvVSfxwLAiKqZ4jCwy3VGWJJz4RRD3JFpvcztQBV7LFhs3cQieTBehG49xxXUIkgdGCpOg1FLfM/lDz/302R/WgFAZ30wnq/VcQZcublGeYx9KfTOT/iS9lFoQQJBAN3JF6guqAXyNm1GERO8AdQh+dLok3KopCp/qvuai7Bop0xoWtzQ+BhtXEIGb8ET5Tr4gY/XFaklCo7biTOQP8MCQQDW2cJG50aRK4RG9PAJ/HO6Ra8ZvxWYEu9LdbqjbIKNQwko4Vg9dgSnWV9g689Y+o9Gh0wsRvv3rhpQgNTJ2qBhAkEAl5XPFoM1EkNXUd6W428XrN6/+pg91xtOgUGbIrahjSQqXH3ixJQLTd7JNNNjZIrtnR2mqCJwypfnnHaOUvDfswJBAItBjWjDkruZRx1uuP7gAGqlq/62Gdr9uthgMF6ogF3rK06x8K21hwKpLOb3MOftT1xZaYfxYnOBB1FSj5ld+UECQQCe9jiqqg6qFXoDAaNrW4sSgUfo69w5CBXeVpAOvMcyfsg7vMD5kxn/yNtkposTMvEAxoSIx+i1ibY7k/DHqGl0";
    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;
    TextView tv_shopcart_total;
    RecyclerView rv_order;
    Button btn_pay;
    ShoppingUtils shoppingUtils;
    private List<Map<String, String>> data;
    private MyShoppingOrderAdapter myShoppingOrderAdapter;
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        showAlert(OrderActivity.this, getString(R.string.pay_success) + payResult);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        showAlert(OrderActivity.this, getString(R.string.pay_failed) + payResult);
                    }
                    shoppingUtils.initializeDatas();
                    finish();
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        showAlert(OrderActivity.this, getString(R.string.auth_success) + authResult);
                    } else {
                        // 其他状态值则为授权失败
                        showAlert(OrderActivity.this, getString(R.string.auth_failed) + authResult);
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private double allMoney;

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
        tb_order = findViewById(R.id.tb_order);
        rv_order = findViewById(R.id.rv_order);
        tv_shopcart_total = findViewById(R.id.tv_shopcart_total);
        btn_pay = findViewById(R.id.btn_pay);

        initRecycler();
        initPayButton();
    }

    private void initPayButton() {
        btn_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                payV2();
            }
        });
    }

    /**
     * 支付宝支付
     */
    public void payV2() {
        if (TextUtils.isEmpty(APPID) || (TextUtils.isEmpty(RSA2_PRIVATE) && TextUtils.isEmpty(RSA_PRIVATE))) {
            showAlert(this, getString(R.string.error_missing_appid_rsa_private));
            return;
        }

        boolean rsa2 = (RSA2_PRIVATE.length() > 0);
        OrderInfoUtil2_0.setTotalAmount(allMoney);
        Map<String, String> params = OrderInfoUtil2_0.buildOrderParamMap(APPID, rsa2);
        String orderParam = OrderInfoUtil2_0.buildOrderParam(params);

        String privateKey = rsa2 ? RSA2_PRIVATE : RSA_PRIVATE;
        String sign = OrderInfoUtil2_0.getSign(params, privateKey, rsa2);
        final String orderInfo = orderParam + "&" + sign;

        final Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(OrderActivity.this);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(RecyclerView.VERTICAL);
        rv_order.setLayoutManager(manager);
        myShoppingOrderAdapter = new MyShoppingOrderAdapter(this);
        rv_order.setAdapter(myShoppingOrderAdapter);
    }

    @Override
    public void initData() {
        initTitlebar();
        initShoppingData();
        setAllMoney();
    }

    private void initTitlebar() {
        tb_order.setCenterText("支付订单", 20, Color.RED);
        tb_order.setRightText("取消", 13, Color.BLACK);
        tb_order.setBackgroundColor(Color.WHITE);

        tb_order.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

    private void setAllMoney() {
        allMoney = shoppingUtils.getAllMoney();
        String s = "" + allMoney;
        if (s.contains(".")) {
            tv_shopcart_total.setText("￥" + allMoney);
        } else {
            tv_shopcart_total.setText("￥" + allMoney + ".00");
        }
    }

    private void initShoppingData() {
        shoppingUtils = ShoppingUtils.getInstance();
        data = shoppingUtils.getBuyThings();
        myShoppingOrderAdapter.refresh(data);
    }
}
