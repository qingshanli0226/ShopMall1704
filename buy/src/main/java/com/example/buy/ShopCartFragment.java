package com.example.buy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.buy.databeans.GetCartBean;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.presenter.CartPresenter;
import com.example.buy.presenter.GoodsPresenter;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ShopCartFragment extends BaseNetConnectFragment implements View.OnClickListener {

    public final int CART_GOODS=100;
    public final int VERIFY_GOODS=101;

    private Button buyBut;
    private Button delBut;
    private RecyclerView recyclerView;
    private CheckBox checkAll;
    private SwipeRefreshLayout swipeRefreshLayout;
    //数据和被选择
    ArrayList<GoodsBean> list = new ArrayList<>();
    ArrayList<Boolean> checks = new ArrayList<>();

    //购物车控制  库存控制
    IPresenter cartPresenter;
    IPresenter goodsPresenter;

    @Override
    public void onClick(View v) {
        if (v.getId() == buyBut.getId()) {
            //支付跳转
            Intent intent = new Intent(getContext(), PayActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(IntentUtil.GOODS,list);
            intent.putExtra(IntentUtil.ORDERS,bundle);
            startActivity(intent);
        } else if (v.getId() == delBut.getId()) {
            //TODO 本来是删除商品,但是莫得接口
            //随便给个提示好了
        }
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

                CheckBox itemCheck = (CheckBox) holder.getView(R.id.itemCheck);
                for (boolean i:checks){
                    itemCheck.setChecked(i);
                }
                //被选中
                itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        checks.set(position,isChecked);
                        boolean flag=true;
                        for (boolean i:checks){
                            if (!i){
                                flag=i;
                                break;
                            }
                        }
                        if (flag){
                            checkAll.setChecked(true);
                        }
                    }
                });
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
                goodsPresenter.onHttpPostRequest(CART_GOODS);
            }
        });
        //全选监听
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                for (boolean i:checks){
                    i=isChecked;
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }
        });

        cartPresenter=new CartPresenter();
        cartPresenter.attachView(this);
    }

    @Override
    public void initDate() {
        goodsPresenter.onHttpPostRequest(CART_GOODS);
    }

    @Override
    public void onRequestDataSuccess(int requestCode, Object data) {
        switch (requestCode){
            case CART_GOODS:
                checks.clear();
                list.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                Gson gson = new Gson();
                GoodsBean[] goods = gson.fromJson(((GetCartBean) data).getResult(), GoodsBean[].class);
                list.addAll(Arrays.asList(goods));
                for (int i=0;i<list.size();i++){
                    checks.add(false);
                }
                recyclerView.getAdapter().notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
                //每一次加入购物车都重新请求库存
                verifyGoods();
                break;
            case VERIFY_GOODS:
                GoodsBean[] noGoods = new Gson().fromJson(((GetCartBean) data).getResult(), GoodsBean[].class);
                Log.e("xxxx","库存不足的商品"+noGoods);
                break;
        }
    }

    private void verifyGoods() {
        cartPresenter.onHttpPostRequest(VERIFY_GOODS);
        goodsPresenter.detachView();
        goodsPresenter=new GoodsPresenter(list);
        goodsPresenter.attachView(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cartPresenter.detachView();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_shopcart;
    }

    @Override
    public int getRelativeLayout() {
        return R.id.shopCartRel;
    }

}
