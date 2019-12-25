package com.example.buy.fragment;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PointF;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSONObject;
import com.example.buy.BezierTypeEvaluator;
import com.example.buy.activity.OrderActivity;
import com.example.buy.adapter.MyShoppingBasketAdapter;
import com.example.buy.R;
import com.example.buy.bean.InsertBean;
import com.example.buy.bean.ShoppingCartBean;
import com.example.buy.presenter.InsertPresenter;
import com.example.buy.presenter.ShoppingCartPresenter;
import com.example.common.LoadingPage;
import com.example.common.SignUtil;
import com.example.common.view.MyOKButton;
import com.example.framework.base.IGetBaseView;
import com.example.framework.base.ILoadView;
import com.example.framework.base.IPostBaseView;
import com.example.framework.manager.ShoppingManager;
import com.example.common.NumberAddSubView;
import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车页面
 */
public class ShoppingCartFragment extends BaseFragment implements NumberAddSubView.OnNumberChangeListener, IGetBaseView<ShoppingCartBean>, IPostBaseView, ILoadView {
    TitleBar tbShoppingCart;
    ImageView ivShoppingCart;
    RecyclerView mRecyclerview;
    RelativeLayout shoppingcartlayout;
    TextView tvShopcartTotal;
    TextView tvShopcarttotaltitle;
    CheckBox checkboxAll;
    CheckBox cbAll;
    LinearLayout llDelete;
    LinearLayout llCheckAll;
    Button btnDelete;
    MyOKButton btnCheckOut;
    LoadingPage lpLoading;

    int flag = 0;

    private MyShoppingBasketAdapter myShoppingBasketAdapter;
    //删除数据暂存
    private List<Map<String, String>> data3 = new ArrayList<>();
    private ShoppingManager myShoppingManager;
    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    setCheck(msg);
                    break;
                case 200:
                    int arg1 = msg.arg1;
                    if (myShoppingManager.getisSetting()) {
                        Log.e("####log", arg1 + "");
                        if (arg1 == 0) {
                            cbAll.setChecked(true);
                        } else if (arg1 == 1) {
                            cbAll.setChecked(false);
                        }
                    } else {
                        if (arg1 == 0) {
                            checkboxAll.setChecked(true);
                        } else if (arg1 == 1) {
                            checkboxAll.setChecked(false);
                        }
                    }
                    break;
                case 300:
                    lpLoading.isSucceed();
                    lpLoading.setVisibility(View.GONE);
                    break;
            }
        }
    };
    private InsertPresenter addOneProduct;
    private ShoppingCartPresenter presenter;

    @Override
    public void onResume() {
        super.onResume();
        if (flag == 0) {
            flag = 1;
        } else {
            initData2();
        }
    }

    //判断购物车内是否有物品
    protected void judgeNumberisZero() {
        if (myShoppingManager.getAllNumber() == 0) {
            mRecyclerview.setVisibility(View.INVISIBLE);
            myShoppingManager.setisSetting(true);
            settingChanged();
            btnCheckOut.setBackgroundColor(Color.GRAY);
            btnCheckOut.setButtonEnabled(false);
            tbShoppingCart.setRightText("编辑", 14, Color.GRAY);
            checkboxAll.setVisibility(View.INVISIBLE);
            ivShoppingCart.setVisibility(View.INVISIBLE);
            tvShopcartTotal.setVisibility(View.INVISIBLE);
            tvShopcarttotaltitle.setVisibility(View.INVISIBLE);
        } else {
            mRecyclerview.setVisibility(View.VISIBLE);
            btnCheckOut.setBackgroundColor(getResources().getColor(R.color.color_lightred));
            btnCheckOut.setButtonEnabled(true);
            tbShoppingCart.setRightText("编辑", 14, Color.BLACK);
            checkboxAll.setVisibility(View.VISIBLE);
            ivShoppingCart.setVisibility(View.VISIBLE);
            tvShopcartTotal.setVisibility(View.VISIBLE);
            tvShopcarttotaltitle.setVisibility(View.VISIBLE);
        }
    }

    //初始化数据
    @Override
    protected void initData() {
        ShoppingManager.getInstance().setMainitem(3);
        initData2();
        setCheckAll();
        setSetting();
        setDelete();
    }

    //设置物品是否被选择
    protected void setCheck(Message msg) {
        boolean isSetting = myShoppingManager.getisSetting();
        String[] s = msg.obj.toString().split(" ");
        if (!isSetting) {
            BigDecimal bigDecimal = new BigDecimal(s[2]);
            double allCount = bigDecimal.doubleValue();
            myShoppingManager.setAllCount(allCount);

            tvShopcartTotal.setText("￥" + allCount + "0");

            List<Map<String, String>> data = myShoppingManager.getData();
            Map<String, String> map = data.get(msg.arg2);
            map.put("ischecked", s[0]);
            map.put("num", s[1]);
            data.set(msg.arg2, map);
            myShoppingBasketAdapter.refresh2(data, msg.arg2, allCount);
            myShoppingManager.setData(data);
        } else {
            Map<String, String> map = data3.get(msg.arg2);
            map.put("ischecked", s[0]);
            map.put("num", s[1]);
            data3.set(msg.arg2, map);
            myShoppingBasketAdapter.reFresh(data3);
        }
    }

    //删除点击事件
    private void setDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int allChecked = 0;
                for (int i = 0; i < data3.size(); i++) {
                    Map<String, String> map = data3.get(i);
                    if (map.get("ischecked").equals("true")) {
                        allChecked++;
                    }
                }

                if (allChecked != 0) {
                    setAlertDialog(allChecked);
                }
            }
        });
    }

    //TitleBar点击事件
    private void setSetting() {
        tbShoppingCart.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {
                int allNumber = myShoppingManager.getAllNumber();
                if (allNumber == 0) {
//                    Toast.makeText(getContext(), "购物车内空空的,快去加点什么吧~~", Toast.LENGTH_SHORT).show();
                } else {
                    settingChanged();
                }
            }

            @Override
            public void CenterClick() {

            }
        });
    }

    //设置弹出框
    protected void setAlertDialog(int allchecked) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提醒：");
        builder.setMessage("是否要删除这" + allchecked + "项物品?");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                for (int j = 0; j < data3.size(); j++) {
                    final Map<String, String> map = data3.get(j);
                    if (map.get("ischecked").equals("true")) {
                        String token = ShoppingManager.getInstance().getToken(getContext());
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.put("token", token);
                        JSONObject jsonObject = new JSONObject();
                        jsonObject.put("productId", map.get("id"));
                        jsonObject.put("productNum", map.get("num"));
                        jsonObject.put("productName", map.get("title"));
                        jsonObject.put("url", map.get("img"));
                        jsonObject.put("productPrice", map.get("price"));
                        jsonObject.put("sign", SignUtil.generateJsonSign(jsonObject));
                        SignUtil.encryptJsonParamsByBase64(jsonObject);
                        addOneProduct = new InsertPresenter("removeOneProduct", InsertBean.class, hashMap, jsonObject);
                        addOneProduct.getPostJsonData();
                        addOneProduct.attachPostView(new IPostBaseView() {
                            @Override
                            public void onPostDataSucess(Object data) {
                                Log.e("####", "删除" + map.get("title") + "成功");
                                initData2();
                            }

                            @Override
                            public void onPostDataFailed(String ErrorMsg) {

                            }
                        });
                    }
                }
                settingChanged();
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //编辑事件
    private void settingChanged() {

        boolean isSetting = myShoppingManager.getisSetting();

        if (isSetting) {
            myShoppingManager.setisSetting(false);
            llCheckAll.setVisibility(View.VISIBLE);
            llDelete.setVisibility(View.GONE);
            tbShoppingCart.setRightText("编辑", 14, Color.BLACK);
            initData2();
        } else {
            data3.clear();
            List<Map<String, String>> data4 = myShoppingManager.getData();
            for (int i = 0; i < data4.size(); i++) {
                Map<String, String> map = data4.get(i);
                Map<String, String> map1 = new HashMap();
                map1.putAll(map);
                map1.put("ischecked", "false");
                data3.add(map1);
            }
            myShoppingManager.setisSetting(true);
            llCheckAll.setVisibility(View.GONE);
            llDelete.setVisibility(View.VISIBLE);
            tbShoppingCart.setRightText("取消", 14, Color.RED);
            cbAll.setChecked(false);
            myShoppingBasketAdapter.setCheckedcount2(0);
            myShoppingBasketAdapter.reFresh(data3);
        }
    }

    //设置全选点击事件
    private void setCheckAll() {
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cbAll.isChecked()) {
                    for (int i = 0; i < data3.size(); i++) {
                        Map<String, String> map = data3.get(i);
                        map.put("ischecked", "true");
                        data3.set(i, map);
                    }
                } else {
                    for (int i = 0; i < data3.size(); i++) {
                        Map<String, String> map = data3.get(i);
                        map.put("ischecked", "false");
                        data3.set(i, map);
                    }
                }
                myShoppingBasketAdapter.reFresh(data3);
            }
        });

        checkboxAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkboxAll.isChecked()) {
                    setAllChecked();
                } else {
                    setAllUnChecked();
                }
                double allCount = myShoppingManager.getAllCount();

                tvShopcartTotal.setText("￥" + allCount + "0");

                myShoppingBasketAdapter.reFresh(myShoppingManager.getData());
                myShoppingBasketAdapter.setAllcount(allCount);
            }
        });
    }

    //取消全选（全不选）
    private void setAllUnChecked() {
        List<Map<String, String>> data = myShoppingManager.getData();
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> map = data.get(i);
            map.put("ischecked", "false");
            data.set(i, map);

            myShoppingManager.setAllCount(0);
        }

        myShoppingBasketAdapter.setCheckedcount(0);
        myShoppingManager.setData(data);
    }

    //全选
    private void setAllChecked() {
        List<Map<String, String>> data = myShoppingManager.getData();
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> map = data.get(i);
            String ischecked = map.get("ischecked");
            map.put("ischecked", "true");
            data.set(i, map);

            if (ischecked.equals("false")) {
                double allCount = myShoppingManager.getAllCount();
                BigDecimal bigDecimal = new BigDecimal(map.get("price"));
                double price = bigDecimal.doubleValue();
                allCount += price * Integer.parseInt(map.get("num"));
                myShoppingManager.setAllCount(allCount);
            }
            myShoppingBasketAdapter.setCheckedcount(data.size());
        }
        myShoppingManager.setData(data);
    }

    //刷新购物车数据
    private void initData2() {
        String token = ShoppingManager.getInstance().getToken(getContext());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);

        presenter = new ShoppingCartPresenter("getShortcartProducts", ShoppingCartBean.class, hashMap);
        presenter.attachGetView(this);
        presenter.attachLoadView(this);
        presenter.getGetData();
    }

    //设置TitleBar
    protected void setTitleBar() {
        tbShoppingCart.setCenterText("购物车", 18, Color.BLACK);
        tbShoppingCart.setRightText("编辑", 14, Color.BLUE);
    }

    //初始化控件
    @Override
    protected void initView(View view) {
        tbShoppingCart = view.findViewById(R.id.tb_buy_shoppingcart);
        ivShoppingCart = view.findViewById(R.id.iv_buy_shoppingcart);
        mRecyclerview = view.findViewById(R.id.rv_buy_shoppingcart);
        shoppingcartlayout = view.findViewById(R.id.rl_buy_shoppingcartlayout);
        tvShopcartTotal = view.findViewById(R.id.tv_buy_shopcartTotal);
        tvShopcarttotaltitle = view.findViewById(R.id.tv_buy_shopcarttotaltitle);
        checkboxAll = view.findViewById(R.id.cb_buy_checkboxAll);
        llDelete = view.findViewById(R.id.ll_buy_delete);
        llCheckAll = view.findViewById(R.id.ll_buy_checkall);
        cbAll = view.findViewById(R.id.cb_buy_all);
        btnDelete = view.findViewById(R.id.btn_buy_delete);
        btnCheckOut = view.findViewById(R.id.btn_buy_checkout);
        lpLoading = view.findViewById(R.id.lp_buy_loading);

        myShoppingManager = ShoppingManager.getInstance();

        setTitleBar();
        setRecycler();
        setCheckOut();
    }

    //点击去结算
    private void setCheckOut() {
        btnCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Map<String, String>> data = myShoppingManager.getData();
                List<Map<String, String>> data2 = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    Map<String, String> map = data.get(i);
                    String ischecked = map.get("ischecked");
                    if (ischecked.equals("true")) {
                        data2.add(map);
                    }
                }
                if(data2.size()!=0){
                    myShoppingManager.setBuyThings(data2);
                    startActivity(new Intent(getContext(), OrderActivity.class));
                }
            }
        });
    }

    //初始化RecyclerView
    private void setRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(manager);
        myShoppingBasketAdapter = new MyShoppingBasketAdapter(getContext(), this, handler);
        mRecyclerview.setAdapter(myShoppingBasketAdapter);
        ((SimpleItemAnimator) mRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    //设置布局
    @Override
    protected int setLayout() {
        return R.layout.fragment_shopping_cart;
    }

    //物品添加方法
    @Override
    public void addNumber(View view, int value, String price, boolean ischecked, int postion) {
        add(view);

        List<Map<String, String>> data = myShoppingManager.getData();
        Map<String, String> map = data.get(postion);
        map.put("num", value + "");
        data.set(postion, map);

        myShoppingManager.setData(data);
        double allCount = myShoppingManager.getAllCount();
        myShoppingBasketAdapter.refresh2(data, postion, allCount);

        refreshNumber(postion);

        if (ischecked) {
            BigDecimal bigDecimal = new BigDecimal(price);
            allCount += bigDecimal.doubleValue();
            tvShopcartTotal.setText("￥" + allCount + "0");
            myShoppingBasketAdapter.setAllcount(allCount);
            myShoppingManager.setAllCount(allCount);
        }
    }

    //物品减少方法
    @Override
    public void subNumner(View view, int value, String price, boolean ischecked, int postion) {
        List<Map<String, String>> data = myShoppingManager.getData();

        Map<String, String> map = data.get(postion);
        map.put("num", value + "");
        data.set(postion, map);

        myShoppingManager.setData(data);

        double allCount = myShoppingManager.getAllCount();
        myShoppingBasketAdapter.refresh2(data, postion, allCount);

        refreshNumber(postion);
        if (ischecked) {
            BigDecimal bigDecimal = new BigDecimal(price);
            allCount -= bigDecimal.doubleValue();

            tvShopcartTotal.setText("￥" + allCount + "0");

            myShoppingBasketAdapter.setAllcount(allCount);
            myShoppingManager.setAllCount(allCount);
        }
    }

    //更新购物车物品数量
    public void refreshNumber(int position) {
        String token = ShoppingManager.getInstance().getToken(getContext());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);

        List<Map<String, String>> data = myShoppingManager.getData();

        Map<String, String> map = data.get(position);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("productId", map.get("id"));
        jsonObject.put("productNum", map.get("num"));
        jsonObject.put("productName", map.get("title"));
        jsonObject.put("url", map.get("img"));
        jsonObject.put("productPrice", map.get("price"));
        jsonObject.put("sign", SignUtil.generateJsonSign(jsonObject));

        SignUtil.encryptJsonParamsByBase64(jsonObject);

        addOneProduct = new InsertPresenter("updateProductNum", InsertBean.class, hashMap, jsonObject);
        addOneProduct.attachPostView(this);
        addOneProduct.getPostJsonData();

    }

    //贝斯尔曲线方法
    public void add(final View view) {

        //贝塞尔起始数据点
        int[] startPosition = new int[2];
        //贝塞尔结束数据点
        int[] endPosition = new int[2];
        //控制点
        int[] recyclerPosition = new int[2];

        view.getLocationInWindow(startPosition);
        ivShoppingCart.getLocationInWindow(endPosition);
        mRecyclerview.getLocationInWindow(recyclerPosition);

        PointF startF = new PointF();
        PointF endF = new PointF();
        PointF controllF = new PointF();

        startF.x = startPosition[0];
        startF.y = startPosition[1] - recyclerPosition[1] + tbShoppingCart.getHeight();
        endF.x = endPosition[0];
        endF.y = endPosition[1] - recyclerPosition[1] + tbShoppingCart.getHeight();
        controllF.x = endF.x;
        controllF.y = startF.y;

        final ImageView imageView = new ImageView(getContext());
        shoppingcartlayout.addView(imageView);

        imageView.setImageResource(R.mipmap.goods_add_btn);

        imageView.getLayoutParams().width = getResources().getDimensionPixelSize(R.dimen.dpsize_30);
        imageView.getLayoutParams().height = getResources().getDimensionPixelSize(R.dimen.dpsize_30);
        imageView.setVisibility(View.VISIBLE);
        imageView.setX(startF.x);
        imageView.setY(startF.y);

        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                imageView.setX(pointF.x);
                imageView.setY(pointF.y);
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }
            @Override
            public void onAnimationEnd(Animator animator) {
                shoppingcartlayout.removeView(imageView);
            }
            @Override
            public void onAnimationCancel(Animator animator) {

            }
            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

        ObjectAnimator objectAnimatorX = new ObjectAnimator().ofFloat(ivShoppingCart, "scaleX", 0.6f, 1.0f);
        ObjectAnimator objectAnimatorY = new ObjectAnimator().ofFloat(ivShoppingCart, "scaleY", 0.6f, 1.0f);
        objectAnimatorX.setInterpolator(new AccelerateInterpolator());
        objectAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimatorX).with(objectAnimatorY).after(valueAnimator);
        set.setDuration(800);
        set.start();
    }

    //购物车数据网址连接成功
    @Override
    public void onGetDataSucess(ShoppingCartBean data) {
        myShoppingManager = ShoppingManager.getInstance();
        List<Map<String, String>> data2 = new ArrayList<>();
        List<ShoppingCartBean.ResultBean> result = data.getResult();
        double allcount1 = 0;
        int checkedcount1 = 0;
        int allNumber1 = 0;
        for (int i = 0; i < result.size(); i++) {
            ShoppingCartBean.ResultBean resultBean = result.get(i);

            Map<String, String> map = new HashMap<>();
            map.put("id", resultBean.getProductId());
            map.put("img", resultBean.getUrl());
            map.put("title", resultBean.getProductName());
            map.put("price", resultBean.getProductPrice());
            List<Map<String, String>> data1 = myShoppingManager.getData();
            int isclicked = 0;
            for (int j = 0; j < data1.size(); j++) {
                Map<String, String> map1 = data1.get(j);
                if (map1.get("id").equals(resultBean.getProductId())) {
                    map.put("ischecked", map1.get("ischecked"));
                    if (map1.get("ischecked").equals("true")) {
                        checkedcount1++;
                        BigDecimal bigDecimal = new BigDecimal(resultBean.getProductNum());
                        double num = bigDecimal.doubleValue();
                        BigDecimal bigDecimal2 = new BigDecimal(resultBean.getProductPrice());
                        double price = bigDecimal2.doubleValue();
                        allcount1 += num * price;
                    }
                    isclicked = 1;
                    break;
                }
            }
            if (isclicked == 0) {
                map.put("ischecked", "true");
                checkedcount1++;
                BigDecimal bigDecimal = new BigDecimal(resultBean.getProductNum());
                double num = bigDecimal.doubleValue();
                BigDecimal bigDecimal2 = new BigDecimal(resultBean.getProductPrice());
                double price = bigDecimal2.doubleValue();
                allcount1 += num * price;
            }
            map.put("num", resultBean.getProductNum());
            allNumber1 += Integer.parseInt(resultBean.getProductNum());
            data2.add(map);
        }

        myShoppingManager.setData(data2);

        myShoppingManager.setAllCount(allcount1);

        int allNumber = myShoppingManager.getAllNumber();
        int myallNumber = allNumber1 - allNumber;

        myShoppingManager.setOnNumberChanged(myallNumber);

        if(myShoppingBasketAdapter!=null){
            myShoppingBasketAdapter.reFresh(data2);
            judgeNumberisZero();
            myShoppingBasketAdapter.setCheckedcount(checkedcount1);
            myShoppingBasketAdapter.setAllcount(allcount1);
            if (checkedcount1 == data2.size()) {
                checkboxAll.setChecked(true);
            } else {
                checkboxAll.setChecked(false);
            }
            tvShopcartTotal.setText("￥" + allcount1 + "0");
        }
        ShoppingManager.getInstance().setOnNumberChanged(0);
    }

    //购物车数据网址连接失败
    @Override
    public void onGetDataFailed(String ErrorMsg) {
        if(lpLoading!=null){
            lpLoading.start(LoadingPage.LOADING_FAILURE);
        }
    }

    //更新购物车物品数量网址连接成功
    @Override
    public void onPostDataSucess(Object data) {
        initData2();
    }

    //更新购物车物品数量网址连接失败
    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }

    @Override
    public void onLoadingPage() {
        if (flag == 0) {
            lpLoading.setVisibility(View.VISIBLE);

            lpLoading.start(LoadingPage.LOADING_SUCCEED);
        }
    }

    @Override
    public void onStopLoadingPage() {
        handler.sendEmptyMessageDelayed(300, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(addOneProduct!=null){
            addOneProduct.detachView();
        }
        if(presenter!=null){
            presenter.detachView();
        }
        handler.removeCallbacksAndMessages(null);
    }
}
