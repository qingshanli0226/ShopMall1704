package com.example.shoppingcart.Ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.commen.util.ShopMailError;
import com.example.net.AppNetConfig;
import com.example.shoppingcart.OutsideClass.GetJsonDataUtil;
import com.example.shoppingcart.R;
import com.example.shoppingcart.bean.JsonBean;
import com.example.shoppingcart.bean.OrderInfoBean;
import com.example.shoppingcart.pay.PayMessage;
import com.example.shoppingcart.presenter.ConfirmServerPayResultPresenter;
import com.example.shoppingcart.presenter.SendOrederPresenter;
import com.google.gson.Gson;
import com.shaomall.framework.base.BaseMVPActivity;
import com.shaomall.framework.base.presenter.IBasePresenter;
import com.shaomall.framework.bean.ShoppingCartBean;
import com.shaomall.framework.manager.ActivityInstanceManager;
import com.shaomall.framework.manager.ShoppingManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Map;


public class AddressManagerActivity extends BaseMVPActivity<Object> {
    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private TextView subtotalprice;
    private TextView todalprice;
    private TextView howmanyindex;
    private TextView tvAddress;
    private IBasePresenter sendOrderPresenter;
    private Button commitorder; //提交订单
    private OrderInfoBean infoBean = null;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                //todo 验证支付结果信息.
                case 1:
                    PayMessage payMessage = (PayMessage) (msg.obj);
                    String outTradeNo = payMessage.getOutTradeNo();//支付信息的订单号.
                    Map<String, String> result = payMessage.getResult();

                    //JSONObject object = JSONObject.parseObject(result.toString().replace("=", ":"));
                    String resultStatus = result.get("resultStatus");
                    String resultContent = result.get("result");
                    boolean payResultIsOk = false;
                    if (resultStatus.equals("9000")) {//如果支付成功, 在这里可以在UI上提示用户支付成功.
                        ActivityInstanceManager.removeActivity(AddressManagerActivity.this);
                        payResultIsOk = true;
                    }
                    //到服务端去拿到支付结果.
                    confirmServerPayResult(outTradeNo, resultContent, payResultIsOk);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected int setLayoutId() {
        return R.layout.activity_payment;
    }

    @Override
    protected void initView() {
        tvAddress = (TextView) findViewById(R.id.tv_address);
        subtotalprice = (TextView) findViewById(R.id.subtotalprice);
        todalprice = (TextView) findViewById(R.id.todalprice);
        commitorder = (Button) findViewById(R.id.commitorder);
        howmanyindex = (TextView) findViewById(R.id.howmanyindex);
        initJsonData();
        tvAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPickerView();
            }
        });

        //点击去支付功能
        commitorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //参数不为空, 进行支付
                if (infoBean != null) {
                    EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
                    payByZhiFuBao(infoBean);
                }
            }
        });
    }


    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        ArrayList<ShoppingCartBean> data = extras.getParcelableArrayList("data");
        int payment = extras.getInt("payment");
        float sum = extras.getFloat("sum");
        sendOrderPresenter = new SendOrederPresenter(data, sum);
        sendOrderPresenter.attachView(this);
        sendOrderPresenter.doJsonPostHttpRequest(AppNetConfig.REQUEST_CODE_GET_ORDER_INFO);
        subtotalprice.setText("$" + sum);
        todalprice.setText("$" + sum);
        howmanyindex.setText("共" + payment + "件商品");
    }

    /**
     * //到服务端去拿到支付结果.
     *
     * @param outTradeNo
     * @param resultContent
     * @param payResultIsOk
     */
    private void confirmServerPayResult(String outTradeNo, String resultContent, boolean payResultIsOk) {
        if (payResultIsOk) { //支付成功
            //向服务器拿结果
            IBasePresenter confirmServerPayResultPresenter = new ConfirmServerPayResultPresenter(outTradeNo, resultContent);
            confirmServerPayResultPresenter.attachView(this);
            confirmServerPayResultPresenter.doJsonPostHttpRequest(AppNetConfig.REQUEST_CODE_CONFIRM_SERVER_PAY_RESULT);
        }
    }


    /**
     * 支付宝支付
     *
     * @param orderInfoBean
     */
    private void payByZhiFuBao(final OrderInfoBean orderInfoBean) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                PayTask payTask = new PayTask(AddressManagerActivity.this);
                //调用支付宝API，发起支付。第一个参数是服务端生成的订单信息。第二个参数是显示加载过程.
                Map<String, String> result = payTask.payV2(orderInfoBean.getOrderInfo(), true);
                PayMessage payMessage = new PayMessage();
                payMessage.setOutTradeNo(orderInfoBean.getOutTradeNo()); //将订单号存在paymessage里.
                payMessage.setResult(result);//将支付宝的支付结果存在paymessage里.
                Log.d("111111", "支付结果：" + payMessage.getOutTradeNo());
                Message message = new Message();
                message.what = 1;
                message.obj = payMessage;
                handler.sendMessage(message);
            }
        };

        Thread thread = new Thread(runnable);
        thread.start();
    }

    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, Object data) {
        if (requestCode == AppNetConfig.REQUEST_CODE_GET_ORDER_INFO) {
            infoBean = (OrderInfoBean) data;
        } else if (requestCode == AppNetConfig.REQUEST_CODE_CONFIRM_SERVER_PAY_RESULT) {
            boolean isPayResult = ((String) data).equals("true");//支付成功
            if (isPayResult) {
                ShoppingManager.getInstance().removeShoppingCartData();
            }
        }
    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        if (requestCode == AppNetConfig.REQUEST_CODE_GET_ORDER_INFO) { //订单信息获取失败
            toast(error.getErrorMessage(), false);
        } else if (requestCode == AppNetConfig.REQUEST_CODE_CONFIRM_SERVER_PAY_RESULT) { //服务器后台失败
            toast(error.getErrorMessage(), false);
        }
    }

    /**
     * 获取本地的省份JSON串
     */
    private void initJsonData() {
        /*
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据
        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体
        /*
         * 添加省份数据
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;
        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三级）

            for (int c = 0; c < jsonBean.get(i).getCity().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCity().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCity().get(c).getArea() == null
                        || jsonBean.get(i).getCity().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCity().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }
            //添加城市数据
            options2Items.add(CityList);
            //添加地区数据
            options3Items.add(Province_AreaList);
        }
    }

    /**
     * 解析省份地址
     *
     * @param result
     * @return
     */
    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    /**
     * 省份三级联动
     */
    private void showPickerView() {
        // 弹出选择器（省市区三级联动）
        OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                tvAddress.setText(options1Items.get(options1).getPickerViewText() + "  "
                        + options2Items.get(options1).get(options2) + "  "
                        + options3Items.get(options1).get(options2).get(options3));
            }
        })
                .setTitleText("城市选择")
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();
        //pvOptions.setPicker(options1Items);//一级选择器
        //pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
        Log.d("CHY", tvAddress.getText() + "");
    }

}
