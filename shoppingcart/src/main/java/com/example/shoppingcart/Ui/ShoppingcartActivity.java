package com.example.shoppingcart.Ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.example.shoppingcart.bean.ShoppingCartBean;
import com.example.shoppingcart.presenter.RemoveOneProductPresenter;
import com.example.shoppingcart.presenter.ShoppingcartPresenter;
import com.example.shoppingcart.presenter.UpDateShoppingcartPresenter;
import com.shaomall.framework.base.BaseMVPFragment;
import com.shaomall.framework.manager.UserInfoManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShoppingcartActivity extends BaseMVPFragment<Object> {
    private RecyclerView listview;
    ArrayList<ShoppingCartBean> arr = new ArrayList<>();
    //TreeSet<ShoppingCartBean> arr= new TreeSet<ShoppingCartBean>();
    private CheckBox allChekbox;
    private TextView tvTotalPrice;
    private TextView tvDelete;
    private TextView tvGoToPay;
    ShoppingcartPresenter presenter;
    RvAdp rvAdp;
    float sum = 0.0f;
    private UpDateShoppingcartPresenter upDateShoppingcartPresenter;

    @Override
    public int setLayoutId() {
        return R.layout.activity_shoopingcart;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        LinearLayout topBar = (LinearLayout) view.findViewById(R.id.top_bar);
        TextView title = (TextView) view.findViewById(R.id.title);
        listview = (RecyclerView) view.findViewById(R.id.listview);

        allChekbox = (CheckBox) view.findViewById(R.id.all_chekbox);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        tvDelete = (TextView) view.findViewById(R.id.tv_delete);
        tvGoToPay = (TextView) view.findViewById(R.id.tv_go_to_pay);
        presenter = new ShoppingcartPresenter();
        presenter.attachView(this);
        rvAdp = new RvAdp(arr, getContext());
    }

    @Override
    protected void initData() {


        //TODO 删除按钮
        tvDelete.setOnClickListener(new View.OnClickListener() {

            private RemoveOneProductPresenter removeOneProductPresenter;

            @Override
            public void onClick(View v) {
                for (int i=0;i<arr.size();i++){
                    if (arr.get(i).isSelect()){
                        //TODO 如果选中了就删除掉
                        if (removeOneProductPresenter==null){
                            removeOneProductPresenter = new RemoveOneProductPresenter();
                            removeOneProductPresenter.attachView(ShoppingcartActivity.this);
                        }
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("productId",arr.get(i).getProductId());
                        jsonObject.put("productNum",arr.get(i).getProductNum());
                        jsonObject.put("productName",arr.get(i).getProductName());
                        jsonObject.put("url",arr.get(i).getUrl());
                        jsonObject.put("productPrice",arr.get(i).getProductPrice());
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
        allChekbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int a = 0; a <= arr.size() - 1; a++) {
                    if (allChekbox.isChecked()) {
                        if (!arr.get(a).isSelect()) {
                            arr.get(a).setSelect(true);
                            sum = sum + Float.parseFloat(arr.get(a).getProductPrice()) * Float.parseFloat(arr.get(a).getProductNum());
                        }
                    } else {
                        arr.get(a).setSelect(false);
                        sum = 0;
                    }
                }

                rvAdp.notifyDataSetChanged();

                //TODO 格式化只保留两位小数点
                DecimalFormat df = new DecimalFormat("#.00");
                sum = Float.parseFloat(df.format(sum));
                tvTotalPrice.setText(sum + "");
            }
        });

        tvGoToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toClass(AddressManagerActivity.class);
            }
        });

        //网络请求
        presenter.doGetHttpRequest(AppNetConfig.REQUEST_CODE_GET_SHORTCART_PRODUCTS);
        rvAdp = new RvAdp(arr, getContext());
        listview.setLayoutManager(new LinearLayoutManager(getContext()));
        listview.setAdapter(rvAdp);

        //TODO 上传
        rvAdp.setItemNumCallBack(new RvAdp.ItemNumCallBack() {
            @Override
            public void onItemNumCallBack(int i, int num) {
                uploadGoodsData(i, num);
            }
        });


        //TODO 适配器的点击事件
        rvAdp.setItemCallBack(new RvAdp.ItemCallBack() {
            @Override
            public void onItemCallBack(final int i) {

                //TODO 购物车里面的check多选
                if (arr.get(i).isSelect()) {
                    //TODO 判断购物车里的物品是否全部选中
                    int whether = 0;
                    for (int x = 0; x <= arr.size() - 1; x++) {
                        if (arr.get(x).isSelect() == false) {
                            whether++;
                        }
                    }

                    //TODO 如果等于0代表全都选中了给他赋值一个true
                    if (whether == 0) {
                        allChekbox.setChecked(true);
                    }

                    //TODO 格式化保留两位小数
                    DecimalFormat df = new DecimalFormat("#.00");
                    sum = Float.parseFloat(df.format(sum));
                    sum = sum + Float.parseFloat(arr.get(i).getProductPrice()) * Float.parseFloat(arr.get(i).getProductNum());

                } else {
                    //TODO 让全选取消一个就成为false
                    allChekbox.setChecked(false);
                    DecimalFormat df = new DecimalFormat("#.00");
                    sum = Float.parseFloat(df.format(sum));
                    sum = sum - Float.parseFloat(arr.get(i).getProductPrice()) * Float.parseFloat(arr.get(i).getProductNum());

                }

                //TODO 格式化只保留两位小数点
                DecimalFormat df = new DecimalFormat("#.00");
                sum = Float.parseFloat(df.format(sum));
                tvTotalPrice.setText(sum + "");

            }
        });
    }

    //TODO  展示购物和内商品名称展示详情页面
    private void DisplayGoods() {

    }

    //TODO 更新商品数量接口
    private void uploadGoodsData(int i, int num) {
        if (upDateShoppingcartPresenter==null){
            upDateShoppingcartPresenter = new UpDateShoppingcartPresenter();
            upDateShoppingcartPresenter.attachView(this);
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId",arr.get(i).getProductId());
        jsonObject.put("productNum",num);
        jsonObject.put("productName",arr.get(i).getProductName());
        jsonObject.put("url",arr.get(i).getUrl());
        jsonObject.put("productPrice",arr.get(i).getProductPrice());
        upDateShoppingcartPresenter.setJsonParam(jsonObject);
        upDateShoppingcartPresenter.doJsonPostHttpRequest(AppNetConfig.REQUEST_CODE_TOUPDATE_CARTQUANTITY);


    }

    @Override
    public void onStart() {
        super.onStart();
        if (UserInfoManager.getInstance().isLogin()) {
            //网络请求
            presenter.doGetHttpRequest(AppNetConfig.REQUEST_CODE_GET_SHORTCART_PRODUCTS);
        }
    }

    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        Toast.makeText(mContext, "" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestHttpDataFailed(ShopMailError error) {
        Toast.makeText(mContext, "" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, Object data) {
        if (requestCode==AppNetConfig.REQUEST_CODE_TOUPDATE_CARTQUANTITY){
            // TODO　修改购物车数量
            presenter.doGetHttpRequest(AppNetConfig.REQUEST_CODE_GET_SHORTCART_PRODUCTS);
        }else if (requestCode==AppNetConfig.COURT_SHIP_CODE_DELETE_SHOPPINGCART_QUANTITY){
            //TODO 删除购物车
            presenter.doGetHttpRequest(AppNetConfig.REQUEST_CODE_GET_SHORTCART_PRODUCTS);
        }
    }

    @Override
    public void onRequestHttpDataListSuccess(int requestCode, String
            message, List<Object> data) {

        if (requestCode == AppNetConfig.REQUEST_CODE_GET_SHORTCART_PRODUCTS) {
            List<ShoppingCartBean> list = (List<ShoppingCartBean>) (List) data;
            ArrayList<ShoppingCartBean> newlist = new ArrayList<>();
            arr.clear();

            for (ShoppingCartBean cd: arr){
                if (!newlist.contains(cd)){
                    newlist.add(cd);

                }
            }
            if (arr.size()==0){
                //TODO 如果购物车里面没有数据了吧全选按钮设置为false并把总价格归零
                if (arr.size()==0){
                    allChekbox.setChecked(false);
                    sum=0f;
                    tvTotalPrice.setText(sum+"");
                }
            }
            for (int i=0;i<=newlist.size()-1;i++){
                Log.d("CHY:选择 ",newlist.get(i).isSelect()+"");

            }
            arr.addAll(list);
            rvAdp.notifyDataSetChanged();
        }

    }
}
