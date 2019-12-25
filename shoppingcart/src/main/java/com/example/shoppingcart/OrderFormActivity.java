package com.example.shoppingcart;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.example.commen.ToolbarCustom;
import com.example.commen.util.ShopMailError;
import com.example.net.AppNetConfig;
import com.example.shoppingcart.adapter.RvShoppingPayAdapter;
import com.example.shoppingcart.util.GetJsonDataUtil;
import com.example.shoppingcart.bean.CheckInventoryBean;
import com.example.shoppingcart.bean.JsonBean;
import com.example.shoppingcart.bean.OrderInfoBean;
import com.example.shoppingcart.pay.PayMessage;
import com.example.shoppingcart.presenter.CheckInventoryPresenter;
import com.example.shoppingcart.presenter.ConfirmServerPayResultPresenter;
import com.example.shoppingcart.presenter.SendOrderPresenter;
import com.google.gson.Gson;
import com.shaomall.framework.base.BaseMVPActivity;
import com.shaomall.framework.base.presenter.IBasePresenter;
import com.shaomall.framework.bean.ShoppingCartBean;
import com.shaomall.framework.manager.ActivityInstanceManager;
import com.shaomall.framework.manager.ShoppingManager;
import com.shaomall.framework.manager.UserInfoManager;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class OrderFormActivity extends BaseMVPActivity<Object> implements View.OnClickListener {
    private ArrayList<JsonBean> options1Items = new ArrayList<>(); //省
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();//市
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();//区
    private TextView mTvOrderTotalPrice;
    private TextView mTvAddress;
    private IBasePresenter iBasePresenter;
    private RecyclerView mRvOrderGoods;
    private TextView mTvPoint;
    private CheckBox mCbOrderPointStatus;
    private Button mBtOrderCommit;
    private EditText mEtInputAddress;
    private ArrayList<ShoppingCartBean> data;
    private float totalPrice;
    private int mPoint; //积分
    private float sum = 0.0f; //减去积分后的总价
    private RvShoppingPayAdapter payAdapter; //展示数据
    private android.widget.LinearLayout mLlOrderInformation;
    private TextView mTvOrderInfo;

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
    private ToolbarCustom mTcCartTop;


    @Override
    protected int setLayoutId() {
        return R.layout.activity_orderform;
    }

    @Override
    protected void initView() {

        mTcCartTop = findViewById(R.id.tc_cart_top);
        mTvAddress = findViewById(R.id.tv_order_address);  //地址信息
        mEtInputAddress = findViewById(R.id.et_input_address); //编辑的地址信息
        mTvOrderTotalPrice = findViewById(R.id.tv_order_total_price); //总价
        mRvOrderGoods = findViewById(R.id.rv_order_goods); //确定购买的商品展示
        mTvPoint = findViewById(R.id.tv_order_point); //积分
        mCbOrderPointStatus = findViewById(R.id.cb_order_point_status); //是否使用积分
        mBtOrderCommit = findViewById(R.id.bt_order_commit); //提交订单

        mLlOrderInformation = findViewById(R.id.ll_order_information); //展示商品错误信息
        mTvOrderInfo = findViewById(R.id.tv_order_info);

        //点击事件监听
        mTcCartTop.setLeftBackImageViewOnClickListener(this);
        mTvAddress.setOnClickListener(this);
        mBtOrderCommit.setOnClickListener(this);
        mCbOrderPointStatus.setOnClickListener(this);

    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {
        //初始化JSON省份数据
        initJsonData();

        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX); //使用支付宝沙箱

        //获取数据
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String tradeNo = bundle.getString("tradeNo");
        if (tradeNo != null) { //直接调起支付功能,否则生成订单
            String orderInfo = bundle.getString("orderInfo");
            OrderInfoBean orderInfoBean = new OrderInfoBean();
            orderInfoBean.setOutTradeNo(tradeNo);
            orderInfoBean.setOrderInfo(orderInfo);
            payByZhiFuBao(orderInfoBean); //跳转支付界面
            return;
        }


        //总价
        totalPrice = bundle.getFloat("sum");
        data = bundle.getParcelableArrayList("data");
        String point = (String) UserInfoManager.getInstance().readUserInfo().getPoint();
        if (point != null) {
            //积分
            mPoint = Integer.parseInt(point);
        }

        //展示要购买的数据
        payAdapter = new RvShoppingPayAdapter(data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvOrderGoods.setLayoutManager(linearLayoutManager);
        mRvOrderGoods.setAdapter(payAdapter);


        //设置积分 和总价
        setTvPoint2TotalPrice(mPoint, totalPrice);
    }


    /**
     * 点击事件处理
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.iv_toolbar_back_left) { //返回按钮
            ActivityInstanceManager.removeActivity(OrderFormActivity.this);

        } else if (id == R.id.tv_order_address) { //地址信息
            showPickerView();

        } else if (id == R.id.cb_order_point_status) { //选中积分按钮监听
            setPointStatus();

        } else if (id == R.id.bt_order_commit) { //提交订单
            if (data != null) {
                //检查服务端多个产品是否库存充足
                iBasePresenter = new CheckInventoryPresenter(data);
                iBasePresenter.attachView(this);
                iBasePresenter.doJsonArrayPostHttpRequest(AppNetConfig.REQUEST_CODE_CHECK_INVENTORY);
            }

        } else {
            toast("此功能正在研发中...", false);
        }
    }

    /**
     * 设置积分的值
     */
    private void setPointStatus() {
        if (mCbOrderPointStatus.isChecked()) {
            float price = this.totalPrice;
            int point = this.mPoint;
            //选中积分, 总价减去积分
            sum = price - point;
            point = 0;
            if (sum <= 0) {
                sum = 0.01f;
            }
            //设置积分 和总价
            setTvPoint2TotalPrice(point, sum);
        } else {
            setTvPoint2TotalPrice(mPoint, totalPrice);
        }
    }

    /**
     * 设置积分 和总价
     *
     * @param point
     * @param price
     */
    private void setTvPoint2TotalPrice(int point, float price) {
        //设置积分
        setTvPoint(point);
        //设置总价
        setTvTotalPrice(price);
    }

    /**
     * 设置积分
     *
     * @param point
     */
    private void setTvPoint(int point) {
        //设置积分
        String str = point + "";
        mTvPoint.setText(str);
    }

    /**
     * 设置总价
     *
     * @param price
     */
    private void setTvTotalPrice(float price) {
        //设置总价
        String str = "$" + price + "";
        mTvOrderTotalPrice.setText(str);
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
            iBasePresenter = new ConfirmServerPayResultPresenter(outTradeNo, resultContent, payResultIsOk);
            iBasePresenter.attachView(this);
            iBasePresenter.doJsonPostHttpRequest(AppNetConfig.REQUEST_CODE_CONFIRM_SERVER_PAY_RESULT);
        }else {
//            ShoppingManager.getInstance().removeShoppingCartData();
            ActivityInstanceManager.removeActivity(this); //支付失败也是销毁界面并清空购物车
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
                PayTask payTask = new PayTask(OrderFormActivity.this);
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
        if (requestCode == AppNetConfig.REQUEST_CODE_GET_ORDER_INFO) { //请求订单号
            payByZhiFuBao((OrderInfoBean) data); //跳转支付界面

        } else if (requestCode == AppNetConfig.REQUEST_CODE_CONFIRM_SERVER_PAY_RESULT) {//支付结果
            boolean isPayResult = data.equals("true");//支付成功

            if (isPayResult) {
                ShoppingManager.getInstance().removeShoppingCartData();
                ActivityInstanceManager.removeActivity(this); //关闭本页面
            } else {
                toast("支付失败", false);
            }
        }
    }


    @Override
    public void onRequestHttpDataListSuccess(int requestCode, String message, List<Object> data) {
        //检查服务端多个产品是否库存充足
        if (requestCode == AppNetConfig.REQUEST_CODE_CHECK_INVENTORY) {
            if (message.equals("请求成功")) {
                mLlOrderInformation.setVisibility(View.GONE);

                //请求成功, 进行订单功能
                if (mCbOrderPointStatus.isChecked()) {
                    iBasePresenter = new SendOrderPresenter(this.data, sum);
                } else {
                    iBasePresenter = new SendOrderPresenter(this.data, totalPrice);
                }
                iBasePresenter.attachView(this);
                iBasePresenter.doJsonPostHttpRequest(AppNetConfig.REQUEST_CODE_GET_ORDER_INFO);

            } else {
                mLlOrderInformation.setVisibility(View.VISIBLE);
                //                mTvOrderInfo.setText((String) data); //展示错误详情
                List<CheckInventoryBean> datas = (List) data;


                toast(message, false);
            }
        }

    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        if (requestCode == AppNetConfig.REQUEST_CODE_CHECK_INVENTORY) {//检查服务端多个产品是否库存充足
            toast(error.getErrorMessage(), false);

        } else if (requestCode == AppNetConfig.REQUEST_CODE_GET_ORDER_INFO) { //订单信息获取失败
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
                mTvAddress.setText(options1Items.get(options1).getPickerViewText() + "  "
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
        Log.d("CHY", mTvAddress.getText() + "");
    }


    @Override
    protected void onDestroy() {
        if (iBasePresenter != null) {
            iBasePresenter.detachView();
            iBasePresenter = null;
        }
        super.onDestroy();
    }

}
