package com.example.buy.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.example.common.code.Constant;
import com.example.common.utils.IntentUtil;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.bean.PointBean;
import com.example.framework.manager.AccountManager;
import com.example.framework.manager.DaoManager;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 跳转过来,进行库存验证,不足则提示
 * 充足则提交订单  订单完成
 * <p>
 * 支付完成,更新现金和积分  再退出
 */
public class PayActivity extends BaseNetConnectActivity{
    private Button payBut;
    private RecyclerView recyclerView;
    private TextView orderMoney;
    private TextView payMoney;
    private TextView subtractIntegra;
    private CheckBox checkInegra;
    private TextView userPoint;

    private ArrayList<GoodsBean> list = new ArrayList<>();
    //下订单 支付确认  库存  积分 现金
    public static final int CODE_ORDER = 100;
    private IPresenter sendOrderPresenter;

    public static final int CODE_PAY_SURE = 101;
    private IPresenter postOrderPresenter;

    public static final int COED_VERIFY = 300;
    private IPresenter verifyPresenter;

    public static final int COED_POINT = 500;
    private IPresenter pointPresenter;

    public static final int COED_MONEY = 400;
    private IPresenter moneyPresenter;

    private Handler handler = new MyHandler(this);
    //阿里支付的消息what
    public static final int PAY = 200;
    //订单   订单号
    private String orderInfo;
    private String outTradeNo;

    @Override
    public void init() {
        super.init();
        payBut = findViewById(R.id.payBut);
        orderMoney = findViewById(R.id.orderMoney);
        payMoney = findViewById(R.id.payMoney);
        recyclerView = findViewById(R.id.recyclerView);
        checkInegra = findViewById(R.id.checkInegra);
        subtractIntegra = findViewById(R.id.subtractIntegra);
        userPoint = findViewById(R.id.userPoint);

        payBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //下订单
                List<SendOrdersBean.BodyBean> bodyBeans = new ArrayList<>();
                for (GoodsBean i : list) {
                    bodyBeans.add(new SendOrdersBean.BodyBean(i.getProductName(), i.getProductId()));
                }
                //直接发起订单
                SendOrdersBean sendOrdersBean = new SendOrdersBean("购买", getMoney(checkInegra.isChecked()), bodyBeans);
                sendOrderPresenter = new PostOrderPresenter(sendOrdersBean);
                sendOrderPresenter.attachView(PayActivity.this);
                sendOrderPresenter.doHttpPostJSONRequest(CODE_ORDER);
            }
        });

        //设置RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<GoodsBean>(R.layout.item_pay, list) {
            @Override
            public void onBind(BaseViewHolder holder, final int position) {
                holder.getImageView(R.id.itemPayImg, AppNetConfig.BASE_URl_IMAGE + list.get(position).getUrl());
                holder.getTextView(R.id.itemPayTitle, list.get(position).getProductName());
                holder.getTextView(R.id.itemPayNum, list.get(position).getProductNum() + "");
                holder.getTextView(R.id.itemPayPrice, list.get(position).getProductPrice());
                holder.getTextView(R.id.itemOneSum, Float.valueOf(list.get(position).getProductPrice()) * list.get(position).getProductNum() + "");
            }
        });
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

        Intent intent = getIntent();
        if (intent.getParcelableArrayListExtra(IntentUtil.ORDERS)!=null){
            list.addAll(intent.getParcelableArrayListExtra(IntentUtil.ORDERS));
            //设置数据
            recyclerView.getAdapter().notifyDataSetChanged();
            orderMoney.setText("原价: " + getMoney(false));
            payMoney.setText(getMoney(false));
            //库存检测
            verifyPresenter = new PostVerifyGoodsPresenter(list);
            verifyPresenter.attachView(this);
            verifyPresenter.doHttpPostJSONRequest(COED_VERIFY);

            setPoint();
        }else {
            orderInfo=intent.getStringExtra(IntentUtil.WAIT_PAY_INFO);
            outTradeNo= intent.getStringExtra(IntentUtil.WAIT_PAY_NO);
            gotoPay();
        }
    }


    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode) {
            case CODE_ORDER:
                GetPayOrderBean payOrderBean = (GetPayOrderBean) data;
                if (payOrderBean.getCode().equals(Constant.CODE_OK)) {
                    //下订单成功
                    orderInfo = payOrderBean.getResult().getOrderInfo();   // 订单信息
                    outTradeNo = payOrderBean.getResult().getOutTradeNo();
                    gotoPay();
                }
                break;
            case CODE_PAY_SURE:
                OkBean okBean = (OkBean) data;
                if (okBean.getCode().equals(Constant.CODE_OK)) {
                    //发送现金
                    if (AccountManager.getInstance().getUser().getMoney() == null) {
                        moneyPresenter = new PostUpDateMoneyPresenter("0");
                    } else {
                        moneyPresenter = new PostUpDateMoneyPresenter(getMoney(checkInegra.isChecked()));
                    }
                    moneyPresenter.attachView(this);
                    moneyPresenter.doHttpPostRequest(COED_MONEY);
                }
                break;
            case COED_VERIFY:
                if (((GetCheckGoodsBean) data).getCode().equals(Constant.CODE_OK)) {
                    List<GetCheckGoodsBean.ResultBean> result = ((GetCheckGoodsBean) data).getResult();
                    for (int i=0;i<result.size();i++){
                        if (result.get(i).getProductId().equals(list.get(i).getProductId())){
                            GoodsBean goodsBean = list.get(i);
                            goodsBean.setProductNum(Integer.valueOf(result.get(i).getProductNum()));
                            list.set(i,goodsBean);
                        }
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                }
                break;
            case COED_MONEY:
                if (((OkBean) data).getCode().equals(Constant.CODE_OK)) {
                    //发送积分
                    if (checkInegra.isChecked()) {
                        int sum = Integer.valueOf(userPoint.getText().toString())-Integer.valueOf(getMoney(false));
                        if (sum < 0) {
                            sum = 0;
                        }
                        pointPresenter = new PostUpDatePointPresenter(sum + "");
                        pointPresenter.attachView(this);
                        pointPresenter.doHttpPostRequest(COED_POINT);
                    }else {
                        finishActivity();
                    }
                }
                break;
            case COED_POINT:
                if (((OkBean) data).getCode().equals(Constant.CODE_OK)) {
                    Integer point = Integer.valueOf(userPoint.getText().toString());
                    String time = DateFormat.format("MM月dd日HH时mm分", System.currentTimeMillis())+"";
                    PointBean pointBean = new PointBean(null, time, "购买了" + list.size() + "个商品", point);
                    DaoManager.Companion.getInstance(this).addPointBean(pointBean);
                    finishActivity();
                }
                break;
        }
    }

    //获取总价
    private String getMoney(boolean pointStatus) {
        int sum = 0;
        for (GoodsBean i : list) {
            sum += Float.valueOf(i.getProductPrice()) * i.getProductNum();
        }
        if (pointStatus) {
            sum -= Integer.valueOf(subtractIntegra.getText().toString());
            if (sum < 0) {
                sum = 0;
            }
        }
        return sum + "";
    }

    private static class MyHandler extends Handler {
        private WeakReference<PayActivity> mWeakReference;

        public MyHandler(PayActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            PayActivity activity = mWeakReference.get();
            switch (msg.what) {
                case PAY:
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    String result = payResult.getResult();// 同步返回需要验证的信息
                    PayResultMessBean payResultMessBean = new Gson().fromJson(result, PayResultMessBean.class);
                    if (payResultMessBean==null){

                    }else {
                        String resultStatus = payResult.getResultStatus();
                        // 判断resultStatus 为9000则代表支付成功
                        if (TextUtils.equals(resultStatus, "9000")) {
                            // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                            Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                            activity.postOrderPresenter = new PostPayResultPresenter(
                                    new SendPayResultBean(activity.outTradeNo,
                                            payResultMessBean.toString(),
                                            true));
                            activity.postOrderPresenter.attachView(activity);
                            activity.postOrderPresenter.doHttpPostJSONRequest(CODE_PAY_SURE);
                        } else {
                            // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                            Toast.makeText(activity, "支付失败:" + payResult, Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
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
    protected void onDestroy() {
        super.onDestroy();
        disPreseter(pointPresenter, postOrderPresenter, moneyPresenter, verifyPresenter, sendOrderPresenter);
    }

    private void disPreseter(IPresenter... iPresenter) {
        for (int i = 0; i < iPresenter.length; i++) {
            if (iPresenter[i] != null) {
                iPresenter[i].detachView();
            }
        }
    }
    //设置积分
    private void setPoint() {
        if (AccountManager.getInstance().getUser().getPoint() == null) {
            subtractIntegra.setText("0");
            userPoint.setText("0");
        } else {
            userPoint.setText(AccountManager.getInstance().getUser().getPoint() + "");
            subtractIntegra.setText(AccountManager.getInstance().getUser().getPoint() + "");
            if (Integer.valueOf((String) AccountManager.getInstance().getUser().getPoint()) > Integer.valueOf(getMoney(false))) {
                subtractIntegra.setText(getMoney(false));
            }
        }
    }
    //发起支付宝支付
    private void gotoPay(){
        if (orderInfo!=null&&outTradeNo!=null&&!orderInfo.isEmpty()&&!outTradeNo.isEmpty()){
            // 必须异步调用
            new Thread(new Runnable() {

                @Override
                public void run() {
                    PayTask payTask = new PayTask(PayActivity.this);
                    Map<String, String> result = payTask.payV2(orderInfo, true);

                    Message msg = new Message();
                    msg.what = PAY;
                    msg.obj = result;
                    handler.sendMessage(msg);
                }
            }).start();
        }
    }
}
