package com.example.shoppingcart.Ui;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commen.LoadingPageConfig;
import com.example.commen.util.PageUtil;
import com.example.commen.util.ShopMailError;
import com.alibaba.fastjson.JSONObject;
import com.example.net.AppNetConfig;
import com.example.shoppingcart.Adapter.RvAdp;
import com.example.shoppingcart.R;
import com.example.shoppingcart.bean.ShoppingCartBean;
import com.example.shoppingcart.presenter.RemoveOneProductPresenter;
import com.example.shoppingcart.presenter.ShoppingCartPresenter;
import com.example.shoppingcart.presenter.UpDateShoppingCartPresenter;
import com.shaomall.framework.base.BaseMVPFragment;
import com.shaomall.framework.bean.LoginBean;
import com.shaomall.framework.manager.UserInfoManager;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartFragment extends BaseMVPFragment<Object> implements UserInfoManager.UserInfoStatusListener {
    private RecyclerView listview;
    private RelativeLayout shoppingrelative;
    ArrayList<ShoppingCartBean> arr = new ArrayList<>();
    private CheckBox allChekbox;
    private TextView tvTotalPrice;
    private TextView tvDelete;
    private TextView tvGoToPay;
    ShoppingCartPresenter presenter;
    RvAdp rvAdp;

    //TODO 总价格
    float sum = 0.0f;
    private UpDateShoppingCartPresenter upDateShoppingCartPresenter;
    PageUtil pageUtil;
    //TODO 选中了多少个商品
    int select=0;
    @Override
    public int setLayoutId() {
        return R.layout.activity_shoopingcart;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        LinearLayout topBar = (LinearLayout) view.findViewById(R.id.top_bar);
        TextView title = (TextView) view.findViewById(R.id.title);
        listview = (RecyclerView) view.findViewById(R.id.listview);
        shoppingrelative = (RelativeLayout) view.findViewById(R.id.shoppingrelative);
        allChekbox = (CheckBox) view.findViewById(R.id.all_chekbox);
        tvTotalPrice = (TextView) view.findViewById(R.id.tv_total_price);
        tvDelete = (TextView) view.findViewById(R.id.tv_delete);
        tvGoToPay = (TextView) view.findViewById(R.id.tv_go_to_pay);
        presenter = new ShoppingCartPresenter();
        presenter.attachView(this);
        rvAdp = new RvAdp(arr, getContext());
        pageUtil=new PageUtil(getContext());
        UserInfoManager instance = UserInfoManager.getInstance();
        instance.registerUserInfoStatusListener(this);
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void initData() {


        //TODO 加载动画
        pageUtil.setReview(shoppingrelative);


        //TODO 删除按钮
        tvDelete.setOnClickListener(new View.OnClickListener() {

            private RemoveOneProductPresenter removeOneProductPresenter;
            @Override
            public void onClick(View v) {
                //TODO 删除的判断
                boolean judgmentOfDeletion=false;

                for (int x = 0; x <= arr.size() - 1; x++) {
                    if (arr.get(x).isSelect()) {
                        judgmentOfDeletion=true;

                    }
                }

                if (judgmentOfDeletion){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("您确定要删除购物车中的商品么?");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            builder.create().cancel();
                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int i=0;i<arr.size();i++){

                                if (arr.get(i).isSelect()){
                                    select--;
                                    //TODO 如果选中了就删除掉
                                    if (removeOneProductPresenter==null){
                                        removeOneProductPresenter = new RemoveOneProductPresenter();
                                        removeOneProductPresenter.attachView(ShoppingCartFragment.this);
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
                            //TODO 设置付款选中了多少个商品
                            tvGoToPay.setText("付款("+select+")");
                        }
                    });
                    builder.show();
                }else {
                    Toast.makeText(mContext, "您还没有选择商品请先选择", Toast.LENGTH_SHORT).show();
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
                    //TODO 全选的话让删除的数量增加

                    if (allChekbox.isChecked()) {
                        if (!arr.get(a).isSelect()) {
                            arr.get(a).setSelect(true);
                            sum = sum + Float.parseFloat(arr.get(a).getProductPrice()) * Float.parseFloat(arr.get(a).getProductNum());

                            select++;
                        }

                    } else {
                        arr.get(a).setSelect(false);
                        sum = 0;
                        select--;

                    }

                }
                //TODO 设置付款选中了多少个商品
                tvGoToPay.setText("付款("+select+")");

                rvAdp.notifyDataSetChanged();

                //TODO 格式化只保留两位小数点
                DecimalFormat df = new DecimalFormat("#.00");
                sum = Float.parseFloat(df.format(sum));
                tvTotalPrice.setText(sum + "");
            }
        });

        //TODO 付款按钮
        tvGoToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 付款的判断
                boolean payOfDeletion=false;

                for (int x = 0; x <= arr.size() - 1; x++) {
                    if (arr.get(x).isSelect()) {
                        payOfDeletion=true;
                    }
                }
                if (payOfDeletion){
                    Bundle bundle = new Bundle();
                    bundle.putInt("payment",select);
                    bundle.putFloat("sum",sum);
                    toClass(AddressManagerActivity.class,bundle);

                }else {
                    Toast.makeText(mContext, "您还没有选择要付款的商品", Toast.LENGTH_SHORT).show();

                }

            }
        });

        //TODO 网络请求
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




        //TODO 单选适配器的点击事件
        rvAdp.setItemCallBack(new RvAdp.ItemCallBack() {
            @Override
            public void onItemCallBack(final int i) {

                //TODO 购物车里面的check多选
                if (arr.get(i).isSelect()) {
                    //TODO 判断购物车里的物品是否全部选中
                    int whether = 0;
                    for (int x = 0; x <= arr.size() - 1; x++) {
                        if (!arr.get(x).isSelect()) {
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
                    select++;
                } else {
                    //TODO 让全选取消一个就成为false
                    select--;
                    allChekbox.setChecked(false);
                    DecimalFormat df = new DecimalFormat("#.00");
                    sum = Float.parseFloat(df.format(sum));
                    sum = sum - Float.parseFloat(arr.get(i).getProductPrice()) * Float.parseFloat(arr.get(i).getProductNum());

                }

                //TODO 设置付款选中了多少个商品
                tvGoToPay.setText("付款("+select+")");

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
        if (upDateShoppingCartPresenter ==null){
            upDateShoppingCartPresenter = new UpDateShoppingCartPresenter();
            upDateShoppingCartPresenter.attachView(this);
        }


        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId",arr.get(i).getProductId());
        jsonObject.put("productNum",num);
        jsonObject.put("productName",arr.get(i).getProductName());
        jsonObject.put("url",arr.get(i).getUrl());
        jsonObject.put("productPrice",arr.get(i).getProductPrice());
        upDateShoppingCartPresenter.setJsonParam(jsonObject);
        upDateShoppingCartPresenter.doJsonPostHttpRequest(AppNetConfig.REQUEST_CODE_TOUPDATE_CARTQUANTITY);


    }



    @Override
    public void onHiddenChanged(boolean hidden) {
        select=0;
        //TODO 设置付款选中了多少个商品
        tvGoToPay.setText("付款("+select+")");
        if (!hidden){
            if (UserInfoManager.getInstance().isLogin()) {
                //网络请求
                presenter.doGetHttpRequest(AppNetConfig.REQUEST_CODE_GET_SHORTCART_PRODUCTS);
            }
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
                //TODO 如果购物车里面没有数据了吧全选按钮设置为false并把总价格归零
                if (arr.size()==0){
                allChekbox.setChecked(false);
                sum=0f;
                tvTotalPrice.setText(sum+"");
            }
            for (int i=0;i<=newlist.size()-1;i++){
                Log.d("CHY:选择 ",newlist.get(i).isSelect()+"");

            }
            arr.addAll(list);
            rvAdp.notifyDataSetChanged();

        }



    }

    @Override
    public void loadingPage(int requestCode, int code) {
        if (requestCode!=AppNetConfig.REQUEST_CODE_TOUPDATE_CARTQUANTITY){
            if (code==LoadingPageConfig.STATE_LOADING_CODE){
                //TODO 正在加载中
                pageUtil.showLoad();
            }else if (code==LoadingPageConfig.STATE_ERROR_CODE){
                //TODO 加载失败
                Toast.makeText(mContext, "加载失败当前网络不好", Toast.LENGTH_SHORT).show();
            }else if (code==LoadingPageConfig.STATE_EMPTY_CODE){
                //TODO 加载成功, 但数据为空
                Toast.makeText(mContext, "数据为空", Toast.LENGTH_SHORT).show();
            }else if (code==LoadingPageConfig.STATE_SUCCESS_CODE){
                //TODO 加载成功
                pageUtil.hideload();
            }

        }
    }



    //TODO 如果退出登录了让他选择的数量归零
    @Override
    public void onUserStatus(boolean isLogin, LoginBean userInfo) {
        if (!isLogin){
            select=0;
            //TODO 设置付款选中了多少个商品
            tvGoToPay.setText("付款("+select+")");
        }
    }
}
