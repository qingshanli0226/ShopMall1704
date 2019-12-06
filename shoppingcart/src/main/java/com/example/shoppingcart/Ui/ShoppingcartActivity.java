package com.example.shoppingcart.Ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.shoppingcart.R;
import com.shaomall.framework.base.BaseActivity;

public class ShoppingcartActivity extends BaseActivity {
    private LinearLayout topBar;
    private TextView title;
    private ListView listview;
    private CheckBox allChekbox;
    private TextView tvTotalPrice;
    private TextView tvDelete;
    private TextView tvGoToPay;

    @Override
    protected void initView() {

        topBar = (LinearLayout) findViewById(R.id.top_bar);
        title = (TextView) findViewById(R.id.title);
        listview = (ListView) findViewById(R.id.listview);
        allChekbox = (CheckBox) findViewById(R.id.all_chekbox);
        tvTotalPrice = (TextView) findViewById(R.id.tv_total_price);
        tvDelete = (TextView) findViewById(R.id.tv_delete);
        tvGoToPay = (TextView) findViewById(R.id.tv_go_to_pay);

    }

    @Override
    protected int setLayoutId() {
        return R.layout.activity_shoopingcart;
    }

    @Override
    protected void initData() {


    }
}
