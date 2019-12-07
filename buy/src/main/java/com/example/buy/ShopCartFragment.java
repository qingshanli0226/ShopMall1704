package com.example.buy;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.buy.databeans.GetCartBean;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.presenter.CartPresenter;
import com.example.buy.presenter.GoodsPresenter;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.base.BasePresenter;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;
import com.example.net.RetrofitCreator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class ShopCartFragment extends BaseNetConnectFragment implements View.OnClickListener {
    private Button buyBut;
    private Button delBut;
    private RecyclerView recyclerView;
    private CheckBox checkAll;
    private SwipeRefreshLayout swipeRefreshLayout;

    ArrayList<GoodsBean> list = new ArrayList<>();

    IPresenter goodsPresenter;

    IPresenter cartPresenter;

    @Override
    public void onStart() {
        super.onStart();
        //判断是否登录
        // 进入页面进行请求购物车数据   然后进行库存判断
        verifyGoods();
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

    @Override
    public void onClick(View v) {
        if (v.getId() == buyBut.getId()) {
            Intent intent = new Intent(getContext(), PayActivity.class);
            startActivity(intent);
        } else if (v.getId() == delBut.getId()) {
            //上传删除的商品  并进行回调,对用户进行反馈
            //
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopcart;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.shopCartRel;
    }

    @Override
    public void init(View view) {
        super.init(view);
        buyBut = view.findViewById(R.id.buyBut);
        delBut = view.findViewById(R.id.delBut);
        recyclerView = view.findViewById(R.id.recyclerView);
        checkAll = view.findViewById(R.id.checkAll);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        buyBut.setOnClickListener(this);
        delBut.setOnClickListener(this);
        //设置recyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new BaseRecyclerAdapter<GoodsBean>(R.layout.item_goods, list) {
            @Override
            public void onBind(BaseViewHolder holder, final int position) {
                holder.getTextView(R.id.itemTitle, list.get(position).getProductName());
                holder.getTextView(R.id.itemNum, list.get(position).getProductNum() + "");
                holder.getTextView(R.id.itemPrice, list.get(position).getProductId());
                //减按钮
                if (list.get(position).getProductNum()==1) {
                    holder.getView(R.id.itemDelBut).setClickable(false);
                }
                holder.getView(R.id.itemDelBut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = list.get(position).getProductNum();
                        list.get(position).setProductNum(num - 1);
                        if (list.get(position).getProductNum()== 1) {
                            v.setClickable(false);
                        }
                    }
                });
                //加按钮
                holder.getView(R.id.itemAddBut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = list.get(position).getProductNum();
                        list.get(position).setProductNum(num + 1);
                    }
                });
            }
        });
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新购物车数据
              //  goodsPresenter.onHttpPostRequest();
            }
        });
        //库存检测管理者
        goodsPresenter=new GoodsPresenter();
        cartPresenter=new CartPresenter();

        goodsPresenter.attachView(this);
        cartPresenter.attachView(this);
    }

    @Override
    public void initDate() {


    }
}
