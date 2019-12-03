package com.example.buy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class ShopCartFragment extends Fragment implements View.OnClickListener {
    Context context;
    private Button buyBut;
    private Button delBut;
    private RecyclerView recyclerView;
    private CheckBox checkAll;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ShopCartFragment(Context context) {
        this.context = context;
    }

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopcart, container, false);
        buyBut = view.findViewById(R.id.buyBut);
        delBut = view.findViewById(R.id.delBut);
        recyclerView = view.findViewById(R.id.recyclerView);
        checkAll = view.findViewById(R.id.checkAll);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        buyBut.setOnClickListener(this);
        delBut.setOnClickListener(this);

        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新购物车数据
                requestCart();
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //判断是否登录
        // 进入页面进行请求购物车数据   然后进行库存判断
        requestCart();
        verifyGoods();
        initData();
    }
    private void requestCart() {
        /**
         *
         * 16, “getShortcartProducts”
         * 说明：获取服务端购物车产品信息的接口
         * GET
         * 请求参数：
         * 无
         * 请求头需要添加token
         *
         * 返回值:
         * 返回格式：application/json, text/json
         * 示例：
         * {"code":"200","message":"请求成功","result":"[{\"productId\":\"1512\",\"productNum\":1,\"productName\":\"衬衫\",\"url\":\"http://www.baidu.com\"}]"}
         * */
    }
    private void verifyGoods() {
        /**
         * 18,”checkInventory”
         * 说明：检查服务端多个产品是否库存充足
         * POST
         * 请求参数：
         * 参数格式：application/json, text/json
         * 示例：
         * 请求参数说明：请求参数是一json对象数组，每个json 对象是一个产品，里面有产品信息，以及检查服务端是否有相应数量的产品。
         * [{"productId":"1000","productNum":"0","productName":"衬衫","url":"http:\/\/www.baidu.com"},{"productId":"1001","productNum":"1","productName":"衬衫","url":"http:\/\/www.baidu.com"},{"productId":"1002","productNum":"2","productName":"衬衫","url":"http:\/\/www.baidu.com"},{"productId":"1003","productNum":"3","productName":"衬衫","url":"http:\/\/www.baidu.com"},{"productId":"1004","productNum":"4","productName":"衬衫","url":"http:\/\/www.baidu.com"}]
         * 请求头需要添加token
         * */
    }

    private void initData() {
        //设置页面,对失效物品,进行提示

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buyBut.getId()) {
            Intent intent = new Intent(context, PayActivity.class);
            startActivity(intent);
        } else if (v.getId() == delBut.getId()) {
            //上传删除的商品  并进行回调,对用户进行反馈
            //
        }
    }
}
