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
import com.example.common.SignUtil;
import com.example.framework.base.IGetBaseView;
import com.example.framework.base.IPostBaseView;
import com.example.framework.manager.ShoppingManager;
import com.example.common.NumberAddSubView;
import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.net.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车页面
 */
public class ShoppingCartFragment extends BaseFragment implements NumberAddSubView.OnNumberChangeListener, IGetBaseView<ShoppingCartBean>, IPostBaseView {
    TitleBar tbShoppingCart;
    ImageView ivShoppingCart;
    RecyclerView mRecyclerview;
    RelativeLayout shoppingcartlayout;
    TextView tvShopcartTotal;
    CheckBox checkboxAll;
    CheckBox cbAll;
    LinearLayout llDelete;
    LinearLayout llCheckAll;
    Button btnDelete;
    Button btnCheckOut;

    int flag = 0;

    int flag2 = 0;

    private MyShoppingBasketAdapter myShoppingBasketAdapter;
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
                    if (arg1 == 0) {
                        checkboxAll.setChecked(true);
                    } else if (arg1 == 1) {
                        checkboxAll.setChecked(false);
                    }
                    break;
            }
        }
    };
    private ShoppingManager myShoppingManager;
    private InsertPresenter addOneProduct;

    //设置物品是否被选择
    protected void setCheck(Message msg) {
        boolean isSetting = myShoppingManager.getisSetting();
        double allCount = myShoppingManager.getAllCount();
        String[] s = msg.obj.toString().split(" ");
        if (!isSetting) {
            allCount = Double.parseDouble(s[2]);
            myShoppingManager.setAllCount(allCount);

            tvShopcartTotal.setText("￥" + allCount + "0");

        }

        List<Map<String, String>> data = myShoppingManager.getData();
        Map<String, String> map = data.get(msg.arg2);
        map.put("id", map.get("id"));
        map.put("img", map.get("img"));
        map.put("title", map.get("title"));
        map.put("price", map.get("price"));

        map.put("ischecked", s[0]);
        map.put("num", s[1]);
        data.set(msg.arg2, map);
        myShoppingBasketAdapter.refresh2(data, msg.arg2, allCount);
        myShoppingManager.setData(data);
    }

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
        } else {
            mRecyclerview.setVisibility(View.VISIBLE);
        }
    }

    //初始化数据
    @Override
    protected void initData() {
        initData2();
        setCheckAll();
        setSetting();
        setDelete();
    }

    //删除点击事件
    private void setDelete() {
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int allChecked = myShoppingManager.getAllChecked();
                if (allChecked != 0) {
                    setAlertDialog(allChecked);
                }
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
                List<Map<String, String>> data = myShoppingManager.getData();

                for (int j = 0; j < data.size(); j++) {
                    final Map<String, String> map = data.get(j);
                    if (map.get("ischecked").equals("true")) {
                        flag2 = 0;

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
                    Toast.makeText(getContext(), "购物车内空空的,快去加点什么吧~~", Toast.LENGTH_SHORT).show();
                } else {
                    settingChanged();
                }
            }
            @Override
            public void CenterClick() {

            }
        });
    }

    //编辑事件
    private void settingChanged() {
        boolean isSetting = myShoppingManager.getisSetting();
        if (isSetting) {
            myShoppingManager.setisSetting(false);
            llCheckAll.setVisibility(View.VISIBLE);
            llDelete.setVisibility(View.GONE);
        } else {
            myShoppingManager.setisSetting(true);
            llCheckAll.setVisibility(View.GONE);
            llDelete.setVisibility(View.VISIBLE);
        }
        setAllUnChecked();
        cbAll.setChecked(false);
        checkboxAll.setChecked(false);
        myShoppingManager.setAllCount(0);
        myShoppingBasketAdapter.setAllcount(0);
        myShoppingBasketAdapter.reFresh(myShoppingManager.getData());
    }

    //设置全选点击事件
    private void setCheckAll() {
        cbAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Map<String, String>> data = myShoppingManager.getData();
                if (cbAll.isChecked()) {
                    for (int i = 0; i < data.size(); i++) {
                        Map<String, String> map = data.get(i);
                        map.put("id", map.get("id"));
                        map.put("img", map.get("img"));
                        map.put("title", map.get("title"));
                        map.put("price", map.get("price"));
                        map.put("ischecked", "true");
                        map.put("num", map.get("num"));
                        data.set(i, map);
                    }
                } else {
                    for (int i = 0; i < data.size(); i++) {
                        Map<String, String> map = data.get(i);
                        map.put("id", map.get("id"));
                        map.put("img", map.get("img"));
                        map.put("title", map.get("title"));
                        map.put("price", map.get("price"));
                        map.put("ischecked", "false");
                        map.put("num", map.get("num"));
                        data.set(i, map);
                    }
                }
                myShoppingManager.setData(data);
                myShoppingBasketAdapter.reFresh(data);
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
            map.put("id", map.get("id"));
            map.put("img", map.get("img"));
            map.put("title", map.get("title"));
            map.put("price", map.get("price"));
            map.put("ischecked", "false");
            map.put("num", map.get("num"));
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
            map.put("id", map.get("id"));
            map.put("img", map.get("img"));
            map.put("title", map.get("title"));
            map.put("price", map.get("price"));
            String ischecked = map.get("ischecked");
            map.put("ischecked", "true");
            map.put("num", map.get("num"));
            data.set(i, map);

            if (ischecked.equals("false")) {
                double allCount = myShoppingManager.getAllCount();
                allCount += Double.parseDouble(map.get("price")) * Integer.parseInt(map.get("num"));
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

        ShoppingCartPresenter presenter = new ShoppingCartPresenter("getShortcartProducts", ShoppingCartBean.class, hashMap);
        presenter.attachGetView(this);
        presenter.getGetData();

    }

    //设置TitleBar
    protected void setTitleBar() {
        tbShoppingCart.setCenterText("购物车", 18, Color.RED);
        tbShoppingCart.setRightText("编辑", 14, Color.BLACK);
    }

    //初始化控件
    @Override
    protected void initView(View view) {
        tbShoppingCart = view.findViewById(R.id.tb_buy_shoppingcart);
        ivShoppingCart = view.findViewById(R.id.iv_buy_shoppingcart);
        mRecyclerview = view.findViewById(R.id.rv_buy_shoppingcart);
        shoppingcartlayout = view.findViewById(R.id.rl_buy_shoppingcartlayout);
        tvShopcartTotal = view.findViewById(R.id.tv_buy_shopcartTotal);
        checkboxAll = view.findViewById(R.id.cb_buy_checkboxAll);
        llDelete = view.findViewById(R.id.ll_buy_delete);
        llCheckAll = view.findViewById(R.id.ll_buy_checkall);
        cbAll = view.findViewById(R.id.cb_buy_all);
        btnDelete = view.findViewById(R.id.btn_buy_delete);
        btnCheckOut = view.findViewById(R.id.btn_buy_checkout);

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
                myShoppingManager.setBuyThings(data2);
                startActivity(new Intent(getContext(), OrderActivity.class));
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
        map.put("id", map.get("id"));
        map.put("img", map.get("img"));
        map.put("title", map.get("title"));
        map.put("price", map.get("price"));
        map.put("ischecked", map.get("ischecked"));
        map.put("num", value + "");
        data.set(postion, map);

        myShoppingManager.setData(data);
        double allCount = myShoppingManager.getAllCount();
        myShoppingBasketAdapter.refresh2(data, postion, allCount);

        refreshNumber(postion);

        if (ischecked) {
            allCount += Double.parseDouble(price);
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
        map.put("id", map.get("id"));
        map.put("img", map.get("img"));
        map.put("title", map.get("title"));
        map.put("price", map.get("price"));
        map.put("ischecked", map.get("ischecked"));
        map.put("num", value + "");
        data.set(postion, map);

        myShoppingManager.setData(data);

        double allCount = myShoppingManager.getAllCount();
        myShoppingBasketAdapter.refresh2(data, postion, allCount);

        refreshNumber(postion);
        if (ischecked) {
            allCount -= Double.parseDouble(price);

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
        List<Map<String, String>> data2 = new ArrayList<>();
        List<ShoppingCartBean.ResultBean> result = data.getResult();
        for (int i = 0; i < result.size(); i++) {
            ShoppingCartBean.ResultBean resultBean = result.get(i);

            Map<String, String> map = new HashMap<>();
            map.put("id", resultBean.getProductId());
            map.put("img", resultBean.getUrl());
            map.put("title", resultBean.getProductName());
            map.put("price", resultBean.getProductPrice());
            map.put("ischecked", "false");
            map.put("num", resultBean.getProductNum());
            data2.add(map);
        }
        myShoppingBasketAdapter.reFresh(data2);
        myShoppingManager.setData(data2);
        judgeNumberisZero();
        myShoppingManager.setAllCount(0);
        myShoppingBasketAdapter.setCheckedcount(0);
        myShoppingBasketAdapter.setAllcount(0);

        checkboxAll.setChecked(false);
        tvShopcartTotal.setText("￥0.00");

        myShoppingManager.setOnNumberChanged(0);
    }

    //购物车数据网址连接失败
    @Override
    public void onGetDataFailed(String ErrorMsg) {

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
}
