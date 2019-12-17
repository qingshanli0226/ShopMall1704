package com.example.shoppingcart.Ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commen.util.ShopMailError;
import com.alibaba.fastjson.JSONObject;
import com.example.net.AppNetConfig;
import com.example.shoppingcart.Adapter.RvAdp;
import com.example.shoppingcart.R;
import com.shaomall.framework.bean.ShoppingCartBean;
import com.example.shoppingcart.presenter.RemoveOneProductPresenter;
import com.example.shoppingcart.presenter.UpDateShoppingCartPresenter;
import com.shaomall.framework.base.BaseMVPFragment;
import com.shaomall.framework.manager.ShoppingManager;
import com.shaomall.framework.manager.UserInfoManager;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

public class ShoppingCartFragment extends BaseMVPFragment<Object> implements ShoppingManager.NotifyUpdatedShoppingDataListener {
    private RecyclerView recyclerView;
    private CheckBox allCheckbox;
    private TextView tvTotalPrice;
    private TextView tvDelete;
    private TextView tvGoToPay;
    private RvAdp rvAdp;
    float sum = 0.00f;
    private UpDateShoppingCartPresenter upDateShoppingcartPresenter;
    private List<ShoppingCartBean> listData = ShoppingManager.getInstance().getShoppingCartData();
    private HashMap<String, Integer> upDateGoodsNum; //更新数据


    @Override
    public int setLayoutId() {
        return R.layout.activity_shoopingcart;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        LinearLayout topBar = (LinearLayout) view.findViewById(R.id.top_bar);
        TextView title = (TextView) view.findViewById(R.id.title);
        recyclerView = (RecyclerView) view.findViewById(R.id.rv_choppingCart);
        allCheckbox = (CheckBox) view.findViewById(R.id.all_chekbox);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        tvDelete = (TextView) view.findViewById(R.id.tv_delete);
        tvGoToPay = (TextView) view.findViewById(R.id.tv_go_to_pay);

        rvAdp = new RvAdp(listData, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(rvAdp);
    }

    @Override
    protected void initData() {
        //数据更新监听
        ShoppingManager.getInstance().registerNotifyUpdatedShoppingDataListener(this);

        //TODO 删除按钮
        tvDelete.setOnClickListener(new View.OnClickListener() {
            private RemoveOneProductPresenter removeOneProductPresenter;

            @Override
            public void onClick(View v) {
                for (int i = 0; i < listData.size(); i++) {
                    if (listData.get(i).isSelect()) {
                        //TODO 如果选中了就删除掉
                        if (removeOneProductPresenter == null) {
                            removeOneProductPresenter = new RemoveOneProductPresenter();
                            removeOneProductPresenter.attachView(ShoppingCartFragment.this);
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("productId", listData.get(i).getProductId());
                        jsonObject.put("productNum", listData.get(i).getProductNum());
                        jsonObject.put("productName", listData.get(i).getProductName());
                        jsonObject.put("url", listData.get(i).getUrl());
                        jsonObject.put("productPrice", listData.get(i).getProductPrice());
                        removeOneProductPresenter.setJson(jsonObject);
                        removeOneProductPresenter.doJsonPostHttpRequest(AppNetConfig.COURT_SHIP_CODE_DELETE_SHOPPINGCART_QUANTITY);
                    }
                }
            }
        });


        //TODO 点击商品名称展示商品的详情页面
        rvAdp.setItemDetailsCallBack(new RvAdp.ItemDetailsCallBack() {
            @Override
            public void onItemDetailsCallBack(int i) {
                DisplayGoods();
            }
        });

        //TODO 全选按钮
        allCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < listData.size(); i++) {
                    if (allCheckbox.isChecked()) {
                        if (!listData.get(i).isSelect()) {
                            listData.get(i).setSelect(true);
                            sum += getShoppingPrice2Num(listData.get(i)); //计算总价
                        }
                    } else {
                        listData.get(i).setSelect(false);
                        sum = 0.00f;
                    }
                }

                rvAdp.notifyDataSetChanged(); //刷新适配器
                //TODO 更新合计值
                setTvTotalPriceValue(sum);
            }
        });


        tvGoToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClass(AddressManagerActivity.class);
            }
        });


        //TODO 上传
        rvAdp.setItemNumCallBack(new RvAdp.ItemNumCallBack() {
            @Override
            public void onItemNumCallBack(int i, int num) {
                uploadGoodsData(i, num);
            }
        });


        //TODO 适配器的点击事件
        rvAdp.setItemCheckBoxOnClickListener(new RvAdp.ItemOnCheckBoxClickListener() {
            @Override
            public void onClick(final int i) {
                calculateTheTotalPrice(i);
            }
        });
    }

    /**
     * 计算总价
     *
     * @param index
     */
    private void calculateTheTotalPrice(int index) {
        ShoppingCartBean data = listData.get(index);
        //TODO 购物车里面的check多选是否被选中
        if (data.isSelect()) { //判断当前被点中的商品是否选中
            //TODO 判断购物车里的物品是否全部选中
            int whether = 0;
            for (int i = 0; i < listData.size(); i++) {
                if (listData.get(i).isSelect() == false) {
                    whether++;
                    break;
                }
            }

            //TODO 如果等于0代表全都选中了给他赋值一个true
            if (whether == 0) {
                allCheckbox.setChecked(true);
            }

            sum += getShoppingPrice2Num(listData.get(index));

        } else {
            //TODO 让全选取消一个就成为false
            allCheckbox.setChecked(false);
            sum -= getShoppingPrice2Num(listData.get(index));
        }

        setTvTotalPriceValue(sum); //更新合计值
    }


    /**
     * 设置合计的值
     */
    @SuppressLint("SetTextI18n")
    private void setTvTotalPriceValue(float totalPrice) {
        //四舍五入保留两位小数
        BigDecimal bigDecimal = new BigDecimal(totalPrice);
        totalPrice = bigDecimal.setScale(2, RoundingMode.HALF_UP).floatValue();//四舍五入

        //        //TODO 格式化只保留两位小数点
        //        DecimalFormat df = new DecimalFormat("0.00");
        //        totalPrice = Float.parseFloat(df.format(totalPrice));
        tvTotalPrice.setText("￥" + totalPrice);
    }


    /**
     * 计算单个商品的总价格
     *
     * @param data
     * @return
     */
    private float getShoppingPrice2Num(ShoppingCartBean data) {
        float price = Float.parseFloat(data.getProductPrice());
        float num = Float.parseFloat(data.getProductNum());
        return price * num;
    }

    //TODO  展示购物和内商品名称展示详情页面
    private void DisplayGoods() {

    }

    //TODO 更新商品数量接口
    private void uploadGoodsData(int i, int num) {
        if (upDateShoppingcartPresenter == null) {
            upDateShoppingcartPresenter = new UpDateShoppingCartPresenter();
            upDateShoppingcartPresenter.attachView(this);
        }
        upDateGoodsNum = new HashMap<>();
        upDateGoodsNum.put("index", i);
        upDateGoodsNum.put("num", num);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", listData.get(i).getProductId());
        jsonObject.put("productNum", num);
        jsonObject.put("productName", listData.get(i).getProductName());
        jsonObject.put("url", listData.get(i).getUrl());
        jsonObject.put("productPrice", listData.get(i).getProductPrice());
        upDateShoppingcartPresenter.setJsonParam(jsonObject);
        upDateShoppingcartPresenter.doJsonPostHttpRequest(AppNetConfig.REQUEST_CODE_TOUPDATE_CARTQUANTITY);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (UserInfoManager.getInstance().isLogin() && !hidden) {
            ShoppingManager.getInstance().notifyUpdatedShoppingData();
        }
    }


    /**
     * 数据请求失败
     *
     * @param requestCode
     * @param error
     */
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        Toast.makeText(mContext, "" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, Object data) {
        if (requestCode == AppNetConfig.REQUEST_CODE_TOUPDATE_CARTQUANTITY) {
            //更新总价金额
            totalPriceSingleData(upDateGoodsNum.get("index"), upDateGoodsNum.get("num"));
        } else if (requestCode == AppNetConfig.COURT_SHIP_CODE_DELETE_SHOPPINGCART_QUANTITY) {
            //TODO 删除购物车
            ShoppingManager.getInstance().removeShoppingCartData();
        }
    }

    /**
     * 更新总价金额
     *
     * @param i
     * @param newNum
     */
    private void totalPriceSingleData(int i, int newNum) {
        ShoppingCartBean data = listData.get(i);
        if (data.isSelect()) {
            float price = Float.parseFloat(data.getProductPrice());
            int oldNum = Integer.parseInt(data.getProductNum());
            //根据newNum是否大于oldNum判断是点击了加还是减
            if (newNum > oldNum) { //加
                sum += price;
            } else if (newNum < oldNum) { //减
                sum -= price;
            }

            setTvTotalPriceValue(sum); //更新合计值
        }

        // TODO　修改购物车数量
        ShoppingManager.getInstance().upDataGoodsNum(upDateGoodsNum);
    }


    /**
     * 商品改变监听
     */
    @Override
    public void onNotifyUpdateShoppingData(List<ShoppingCartBean> data) {
        //TODO 如果购物车里面没有数据了吧全选按钮设置为false并把总价格归零
        if (data.size() == 0) {
            allCheckbox.setChecked(false);
            sum = 0.00f;
            setTvTotalPriceValue(sum);
        }
        rvAdp.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShoppingManager.getInstance().unRegisterNotifyUpdatedShoppingDataListener(this);
    }
}
