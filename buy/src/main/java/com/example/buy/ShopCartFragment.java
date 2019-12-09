package com.example.buy;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.buy.activity.PayActivity;
import com.example.buy.databeans.GetCartBean;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.buy.presenter.GetCartPresenter;
import com.example.buy.presenter.PostUpDatePresenter;
import com.example.buy.presenter.PostVerifyGoodsPresenter;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.IPresenter;
import com.example.net.AppNetConfig;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;

public class ShopCartFragment extends BaseNetConnectFragment implements View.OnClickListener {

    public final int CART_GOODS=100;
    public final int VERIFY_GOODS=101;
    public final int UPDATA_GOODS=102;

    private Button buyBut;
    private RecyclerView recyclerView;
    private CheckBox checkAll;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button editBut;
    //数据和被选择
    ArrayList<GoodsBean> list = new ArrayList<>();
    ArrayList<Boolean> checks = new ArrayList<>();

    //购物车数据获取 库存  购物车更新
    IPresenter sendCartPresenter;
    IPresenter goodsPresenter;
    IPresenter upDatePresenter;

    //删除与支付状态
    boolean delStatus=false;

    OnCartListener onCartListener;

    public void setOnCartListener(OnCartListener onCartListener) {
        this.onCartListener = onCartListener;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == buyBut.getId()) {
            if (delStatus){
                for (int i=0;i<checks.size();i++){
                    if (checks.get(i)){
                        list.remove(i);
                    }
                }
                recyclerView.getAdapter().notifyDataSetChanged();
            }else {
                //支付跳转
                ArrayList<GoodsBean> goods=new ArrayList<>();
                for (int i=0;i<checks.size();i++){
                    if (checks.get(i)){
                        goods.add(list.get(i));
                    }
                }
                Intent intent = new Intent(getContext(), PayActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(IntentUtil.GOODS,goods);
                intent.putExtra(IntentUtil.ORDERS,bundle);
                startActivity(intent);
            }
        } else if(v.getId()==editBut.getId()){
            if (delStatus){
                buyBut.setText( getResources().getString(R.string.buyNow));
                delStatus=false;
            }else {
                buyBut.setText( getResources().getString(R.string.buyDelete));
                delStatus=true;
            }
        }
    }

    @Override
    public void init(View view) {
        super.init(view);
        buyBut = view.findViewById(R.id.buyBut);
        editBut = view.findViewById(R.id.editBut);
        recyclerView = view.findViewById(R.id.recyclerView);
        checkAll = view.findViewById(R.id.checkAll);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        buyBut.setOnClickListener(this);
        editBut.setOnClickListener(this);
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
                        judgeCheckAll();
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
                        upDatePresenter=new PostUpDatePresenter(list.get(position));
                        upDatePresenter.attachView(ShopCartFragment.this);
                        upDatePresenter.onHttpPostRequest(UPDATA_GOODS);
                    }
                });
                //加按钮
                holder.getView(R.id.itemAddBut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int num = list.get(position).getProductNum();
                        list.get(position).setProductNum(num + 1);
                        if (list.get(position).getProductNum()>1){
                            holder.getView(R.id.itemAddBut).setClickable(true);
                        }
                        upDatePresenter=new PostUpDatePresenter(list.get(position));
                        upDatePresenter.attachView(ShopCartFragment.this);
                        upDatePresenter.onHttpPostRequest(UPDATA_GOODS);
                    }
                });
            }
        });
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新购物车数据
                sendCartPresenter.onHttpGetRequest(CART_GOODS);
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

              //  goodsPresenter.onHttpPostRequest();
            }
        });

        sendCartPresenter=new GetCartPresenter();
        sendCartPresenter.attachView(this);
    }

    @Override
    public void initDate() {
        sendCartPresenter.onHttpGetRequest(CART_GOODS);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        disPreseter(sendCartPresenter,goodsPresenter,upDatePresenter);
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
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        swipeRefreshLayout.setRefreshing(false);
        switch (requestCode){
            case CART_GOODS:
                Log.e("xxx","购物车获取到的数据"+data.toString());
                checks.clear();
                list.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                if (((GetCartBean) data).getCode().equals(AppNetConfig.CODE_OK)){
                    Gson gson = new Gson();
                    GoodsBean[] goods = gson.fromJson(((GetCartBean) data).getResult(), GoodsBean[].class);
                    list.addAll(Arrays.asList(goods));
                    for (int i=0;i<list.size();i++){
                        checks.add(false);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                    //每一次刷新购物车都重新检查库存
                    goodsPresenter=new PostVerifyGoodsPresenter(list);
                    goodsPresenter.attachView(this);
                    //监听全局的购物车商品数量
                    onCartListener.OnCartChangeListener(list.size());
                }else {
                    Toast.makeText(getContext(),"获取数据失败",Toast.LENGTH_SHORT).show();
                }
                break;
            case VERIFY_GOODS:
                Log.e("xxx","购物车的库存数据"+data.toString());
                GoodsBean[] noGoods = new Gson().fromJson(((GetCartBean) data).getResult(), GoodsBean[].class);
                Log.e("xxxx","库存刷新后的商品"+noGoods);
                break;
            case UPDATA_GOODS:
                if (Integer.valueOf(((OkBean)data).getCode())!=200){
                    //更新失败
                }
                break;
        }
    }
    private void judgeCheckAll(){
        //判断全选
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

    private void disPreseter(IPresenter... iPresenter){
        for (int i=0;i<iPresenter.length;i++){
            if (iPresenter[i]!=null){
                iPresenter[i].detachView();
            }
        }
    }
}
