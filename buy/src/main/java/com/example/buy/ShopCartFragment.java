package com.example.buy;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.buy.activity.GoodsActiviy;
import com.example.buy.activity.PayActivity;
import com.example.buy.databeans.GetCartBean;
import com.example.buy.databeans.GoodsBean;
import com.example.buy.databeans.OkBean;
import com.example.buy.presenter.PostDelteGoodsPresenter;
import com.example.framework.manager.AccountManager;
import com.example.framework.manager.CartManager;
import com.example.buy.presenter.GetCartPresenter;
import com.example.buy.presenter.PostUpDateCartPresenter;
import com.example.common.IntentUtil;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.port.OnClickItemListener;
import com.example.framework.port.IPresenter;

import java.util.ArrayList;

import com.example.net.AppNetConfig;

public class ShopCartFragment extends BaseNetConnectFragment implements View.OnClickListener {
    //请求购物车  刷新单个   删除
    private final int CART_GOODS = 100;
    private final int UPDATA_GOODS = 101;
    private final int DEL_CODE = 102;

    private Button buyBut;
    private RecyclerView recyclerView;
    private CheckBox checkAll;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button editBut;
    private TextView nullGoods;
    private TextView cartMoney;
    //数据和被选择
    private ArrayList<GoodsBean> list = new ArrayList<>();
    private ArrayList<Boolean> checks = new ArrayList<>();
    private ArrayList<GoodsBean> waitDelList = new ArrayList<>();

    //购物车数据获取  购物车更新
    private IPresenter sendCartPresenter;
    private IPresenter upDatePresenter;
    private IPresenter deleteGoodsPresenter;

    //删除与支付状态   全选状态
    boolean delStatus = false;
    boolean checkStatus = false;


    @Override
    public void onClick(View v) {
        if (v.getId() == buyBut.getId()) {
            if (delStatus) {
                //删除
                for (int i = 0; i < checks.size(); i++) {
                    if (checks.get(i)) {
                        waitDelList.add(list.get(i));
                    }
                }
                deleteGoods();
            } else {
                //支付跳转
                ArrayList<GoodsBean> goods = new ArrayList<>();
                for (int i = 0; i < checks.size(); i++) {
                    if (checks.get(i)) {
                        goods.add(list.get(i));
                    }
                }
                Intent intent = new Intent(getContext(), PayActivity.class);
                intent.putExtra(IntentUtil.ORDERS, goods);
                startActivity(intent);
            }
        } else if (v.getId() == editBut.getId()) {
            //编辑
            if (delStatus) {
                buyBut.setText(getResources().getString(R.string.buyNow));
                delStatus = false;
            } else {
                buyBut.setText(getResources().getString(R.string.buyDelete));
                delStatus = true;
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
        nullGoods = view.findViewById(R.id.nullGoods);
        cartMoney = view.findViewById(R.id.cartMoney);

        buyBut.setOnClickListener(this);
        editBut.setOnClickListener(this);

        //设置recyclerView
        BaseRecyclerAdapter<GoodsBean> adapter = new BaseRecyclerAdapter<GoodsBean>(R.layout.item_goods, list) {
            @Override
            public void onBind(BaseViewHolder holder, final int position) {
                holder.getTextView(R.id.itemTitle, list.get(position).getProductName());
                holder.getTextView(R.id.itemNum, list.get(position).getProductNum() + "");
                holder.getTextView(R.id.itemPrice, list.get(position).getProductPrice());
                CheckBox itemCheck = (CheckBox) holder.getView(R.id.itemCheck);
                //点击图片跳转
                holder.getImageView(R.id.itemImg,
                        AppNetConfig.BASE_URl_IMAGE + list.get(position).getUrl())
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getContext(), GoodsActiviy.class);
                                intent.putExtra(IntentUtil.SHOW_GOOD, list.get(position));
                                getContext().startActivity(intent);
                            }
                        });
                for (boolean i : checks) {
                    itemCheck.setChecked(i);
                }
                //被选中
                itemCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (checks.size() > position) {
                            checks.set(position, isChecked);
                            judgeCheckAll();
                        }
                        //更新价格
                        cartMoney.setText(getAllMoney());
                    }
                });
                //减按钮
                holder.getView(R.id.itemDelBut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (list.get(position).getProductNum() > 1) {
                            upDateNum(position, list.get(position).getProductNum() - 1);
                        }
                    }
                });
                //加按钮
                holder.getView(R.id.itemAddBut).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        upDateNum(position, list.get(position).getProductNum() + 1);
                    }
                });
            }
        };
        adapter.setClickListener(new OnClickItemListener() {
            @Override
            public void onClickListener(int position) {

            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        //下拉刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //下拉刷新购物车数据
                sendCartPresenter.doHttpGetRequest(CART_GOODS);
            }
        });
        //全选监听
        checkAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                /**
                 * 状态true 选true   不动作
                 * 状态false 选true   全部选中
                 * 状态true 选false    全部取消
                 * 状态false 选false   没有被全选那不可能取消全选  不存在
                 * * */

                if (isChecked) {
                    if (!checkStatus) {
                        for (int i = 0; i < checks.size(); i++) {
                            checks.set(i, true);
                        }
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                } else {
                    if (checkStatus) {
                        for (int i = 0; i < checks.size(); i++) {
                            checks.set(i, false);
                        }
                        recyclerView.getAdapter().notifyDataSetChanged();
                    }
                }

                //更新价格
                cartMoney.setText(getAllMoney());

            }
        });

        sendCartPresenter = new GetCartPresenter();
        sendCartPresenter.attachView(this);
    }

    @Override
    public void initDate() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (AccountManager.getInstance().isLogin()){
            sendCartPresenter.doHttpGetRequest(CART_GOODS);
        }
        checkAll.setChecked(false);
    }

    @Override
    public void onRequestSuccess(int requestCode, Object data) {
        super.onRequestSuccess(requestCode, data);
        switch (requestCode) {
            case CART_GOODS:
                checks.clear();
                list.clear();
                recyclerView.getAdapter().notifyDataSetChanged();
                if (((GetCartBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
                    list.addAll(((GetCartBean) data).getResult());
                    for (int i = 0; i < list.size(); i++) {
                        checks.add(false);
                    }
                    recyclerView.getAdapter().notifyDataSetChanged();
                    //更新商品数量
                    CartManager.getInstance().updataCartNum(list.size());

                    if (list.size() == 0) {
                        recyclerView.setVisibility(View.GONE);
                        nullGoods.setVisibility(View.VISIBLE);
                    } else {
                        recyclerView.setVisibility(View.VISIBLE);
                        nullGoods.setVisibility(View.GONE);
                    }
                }
                break;
            case UPDATA_GOODS:
                if (((OkBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
                    //更新成功
//                    sendCartPresenter.doHttpGetRequest(CART_GOODS);
                }
                break;
            case DEL_CODE:
                if (((OkBean) data).getCode().equals(AppNetConfig.CODE_OK)) {
                    waitDelList.remove(0);
                    deleteGoods();
                } else {
                    Toast.makeText(getContext(), waitDelList.get(0).getProductName() + "删除失败", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    //判断全选
    private synchronized void judgeCheckAll() {
        /**
         *  默认状态为全选
         *  每选择一个判断是否全部选中
         * 有一个为false  全选状态为false  取消全选
         * */
        checkStatus = true;
        for (boolean i : checks) {
            if (!i) {
                checkStatus = false;
                break;
            }
        }
        checkAll.setChecked(checkStatus);
    }

    //更新单个数量
    private void upDateNum(int goodsPosition, int goodsNum) {
        list.get(goodsPosition).setProductNum(goodsNum);
        upDatePresenter = new PostUpDateCartPresenter(list.get(goodsPosition));
        upDatePresenter.attachView(ShopCartFragment.this);
        upDatePresenter.doHttpPostJSONRequest(UPDATA_GOODS);
    }

    //获取总价
    private String getAllMoney() {
        float sum = 0;
        for (int i = 0; i < list.size(); i++) {
            if (checks.get(i)) {
                sum += (Float.valueOf(list.get(i).getProductPrice()) * list.get(i).getProductNum());
            }
        }
        return "总计:" + sum + "元";
    }

    //删除单个
    private void deleteGoods() {
        if (waitDelList.size() > 0) {
            deleteGoodsPresenter = new PostDelteGoodsPresenter(waitDelList.get(0));
            deleteGoodsPresenter.attachView(this);
            deleteGoodsPresenter.doHttpPostJSONRequest(DEL_CODE);
        } else {
            buyBut.setText(getResources().getString(R.string.buyNow));
            delStatus = false;
            checkAll.setChecked(false);
            sendCartPresenter.doHttpGetRequest(CART_GOODS);
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
    public void onDestroy() {
        super.onDestroy();
        disPreseter(sendCartPresenter, upDatePresenter);
    }

    private void disPreseter(IPresenter... iPresenter) {
        for (int i = 0; i < iPresenter.length; i++) {
            if (iPresenter[i] != null) {
                iPresenter[i].detachView();
            }
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showLoading() {

    }

}
