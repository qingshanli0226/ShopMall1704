package com.example.shoppingcart.Ui;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.commen.ShopMailError;
import com.example.net.AppNetConfig;
import com.example.shoppingcart.Adapter.RvAdp;
import com.example.shoppingcart.bean.ShoppingCartBean;
import com.example.shoppingcart.R;
import com.example.shoppingcart.presenter.ShoppingcartPresenter;
import com.example.shoppingcart.presenter.UpDateShoppingcartPresenter;
import com.shaomall.framework.base.BaseMVPFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShoppingcartActivity extends BaseMVPFragment<Object> {
    private LinearLayout topBar;
    private TextView title;
    private RecyclerView listview;
    ArrayList<ShoppingCartBean> arr = new ArrayList<>();

    private CheckBox allChekbox;
    private TextView tvTotalPrice;
    private TextView tvDelete;
    private TextView tvGoToPay;
    ShoppingcartPresenter presenter;
    RvAdp rvAdp;
    float sum = 0.0f;

    @Override
    public int setLayoutId() {
        return R.layout.activity_shoopingcart;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        topBar = (LinearLayout) view.findViewById(R.id.top_bar);
        title = (TextView) view.findViewById(R.id.title);
        listview = (RecyclerView) view.findViewById(R.id.listview);

        allChekbox = (CheckBox) view.findViewById(R.id.all_chekbox);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        tvDelete = (TextView) view.findViewById(R.id.tv_delete);
        tvGoToPay = (TextView) view.findViewById(R.id.tv_go_to_pay);
        presenter = new ShoppingcartPresenter();
        presenter.attachView(this);
    }

    @Override
    protected void initData() {
        //TODO 删除按钮
        tvDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        //TODO 全选按钮
        allChekbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int a = 0; a <= arr.size() - 1; a++) {
                    if (allChekbox.isChecked()) {
                        if (arr.get(a).isSelect() == false) {
                            arr.get(a).setSelect(true);
                            sum = sum + Float.parseFloat(arr.get(a).getProductPrice())* Float.parseFloat(arr.get(a).getProductNum());
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
                toClass(AddressManager.class);
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
                Toast.makeText(mContext, ""+i+""+num, Toast.LENGTH_SHORT).show();
                uploadGoodsData(i,num);
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
                    sum = sum + Float.parseFloat(arr.get(i).getProductPrice())* Float.parseFloat(arr.get(i).getProductNum());

                } else {
                    //TODO 让全选取消一个就成为false
                    allChekbox.setChecked(false);
                    DecimalFormat df = new DecimalFormat("#.00");
                    sum = Float.parseFloat(df.format(sum));
                    sum = sum - Float.parseFloat(arr.get(i).getProductPrice())* Float.parseFloat(arr.get(i).getProductNum());

                }

                tvTotalPrice.setText(sum + "");

            }
        });


    }

    private void uploadGoodsData(int i, int num) {
        UpDateShoppingcartPresenter upDateShoppingcartPresenter = new UpDateShoppingcartPresenter();
        upDateShoppingcartPresenter.attachView(this);

        JSONObject jsonObject = new JSONObject();
        //jsonObject.put("productId",arr.get());


        //upDateShoppingcartPresenter.setJsonParam(jsonObject);
    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        Toast.makeText(mContext, "" + error.getErrorMessage(), Toast.LENGTH_SHORT).show();

    }


    @Override
    public void onRequestHttpDataListSuccess(int requestCode, String message, List<Object> data) {

        if (requestCode==AppNetConfig.REQUEST_CODE_GET_SHORTCART_PRODUCTS){
            List<ShoppingCartBean> list=(List<ShoppingCartBean>)(List)data;
            arr.addAll(list);
            rvAdp.notifyDataSetChanged();
        }
    }
}
