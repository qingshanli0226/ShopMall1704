package com.example.buy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.buy.databeans.GoodsBean;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;

import java.util.ArrayList;

public class PayActivity extends BaseNetConnectActivity implements View.OnClickListener {

    private Button payBut;
    private RecyclerView recyclerView;
    ArrayList<GoodsBean> list;


    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(IntentUtil.ORDERS);
        list = bundle.getParcelableArrayList(IntentUtil.GOODS);
        recyclerView.getAdapter().notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.payBut) {
            //检验,然后支付
            verifyMoney();
        }
    }

    //发起更新现金请求
    private void verifyMoney() {
        //{"code":"200","message":"请求成功","result":"6666666"}
        //如果没钱则提示用户,他是个穷人,待支付
        orderCancel();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        orderCancel();
    }

    //支付取消,更改订单为待支付订单
    private void orderCancel() {

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
        payBut = findViewById(R.id.payBut);
        recyclerView = findViewById(R.id.recyclerView);
        //http://49.233.93.155:8080  updateMoney  money=1333
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<GoodsBean>(R.layout.item_pay, list) {
            @Override
            public void onBind(BaseViewHolder holder, final int position) {
                holder.getTextView(R.id.itemTitle, list.get(position).getProductName());
                holder.getTextView(R.id.itemNum, list.get(position).getProductNum() + "");
                holder.getTextView(R.id.itemPrice, list.get(position).getProductId());
            }
        });
        payBut.setOnClickListener(this);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void onRequestDataSuccess(int requestCode, Object data) {

    }
}
