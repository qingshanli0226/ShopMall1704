package com.example.buy;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.framework.base.BaseActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;

import java.util.ArrayList;

public class ShowOrderActivity extends BaseActivity {

    private RecyclerView recyclerView;

    ArrayList<String> list = new ArrayList<>();
    private TextView showOrderType;

    @Override
    public int getLayoutId() {
        return R.layout.activity_showorder;
    }

    @Override
    public void init() {
        showOrderType.setText("展示的订单类型");
        //http://49.233.93.155:8080  updateMoney  money=1333
        //获取传递过来的数据,然后进行订单类型的显示
        /***
         * 21 “findForPay”
         * 说明：查找待支付的订单
         * GET
         * 请求参数：
         * 无
         * 请求头需要添加token
         *
         * 返回值:
         * 返回格式：application/json, text/json
         * 示例：
         * [{"subject":"buy","body":"测试数据","totalPrice":"500","time":"1558940014208","status":null,"reserveOne":null}]
         *
         * 22 “findForSend”
         * 说明：查找待发货的订单
         * GET
         * 请求参数：
         * 无
         * 请求头需要添加token
         *
         * 返回值:
         * 返回格式：application/json, text/json
         * 示例：
         * [{"subject":"buy","body":"测试数据","totalPrice":"500","time":"1558940014208","status":null,"reserveOne":null}]
         * */
    }

    @Override
    public void initDate() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<String>(R.layout.item_order, list) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {

            }
        });
    }
}
