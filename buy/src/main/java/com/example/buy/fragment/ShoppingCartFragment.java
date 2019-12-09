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
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buy.BezierTypeEvaluator;
import com.example.buy.activity.OrderActivity;
import com.example.buy.adapter.MyShoppingBasketAdapter;
import com.example.buy.R;
import com.example.buy.ShoppingUtils;
import com.example.common.NumberAddSubView;
import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 购物车页面
 */
public class ShoppingCartFragment extends BaseFragment implements NumberAddSubView.OnNumberChangeListener {

    TitleBar tb_shopping_cart;
    ImageView iv_shopping_cart;
    RecyclerView mRecyclerview;
    RelativeLayout shoppingcartlayout;
    TextView tv_shopcart_total;
    CheckBox checkbox_all;
    CheckBox cb_all;
    LinearLayout ll_delete;
    LinearLayout ll_check_all;
    Button btn_delete;
    Button btn_check_out;


    int flag = 0;

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
                        checkbox_all.setChecked(true);
                    } else if (arg1 == 1) {
                        checkbox_all.setChecked(false);
                    }
                    break;
            }
        }
    };
    private ShoppingUtils myShoppingUtils;

    protected void setCheck(Message msg) {
        boolean isSetting = myShoppingUtils.getisSetting();
        double allCount = myShoppingUtils.getAllCount();
        String[] s = msg.obj.toString().split(" ");
        if (!isSetting) {
            allCount = Double.parseDouble(s[2]);
            myShoppingUtils.setAllCount(allCount);
            if (s[2].contains(".")) {
                tv_shopcart_total.setText("￥" + allCount + "0");
            } else {
                tv_shopcart_total.setText("￥" + allCount + ".00");
            }
        }


        List<Map<String, String>> data = myShoppingUtils.getData();
        Map<String, String> map = data.get(msg.arg2);
        map.put("img", map.get("img"));
        map.put("title", map.get("title"));
        map.put("price", map.get("price"));

        map.put("ischecked", s[0]);
        map.put("num", s[1]);
        data.set(msg.arg2, map);
        myShoppingBasketAdapter.refresh2(data, msg.arg2, allCount);
        myShoppingUtils.setData(data);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (flag == 0) {
            flag = 1;
        } else {
            setAllUnChecked();

            myShoppingBasketAdapter.refresh(myShoppingUtils.getData());
            checkbox_all.setChecked(false);
            tv_shopcart_total.setText("￥0.00");

            judgeNumberisZero();
        }
    }

    protected void judgeNumberisZero() {
        if (myShoppingUtils.getAllNumber() == 0) {
            mRecyclerview.setVisibility(View.INVISIBLE);
            myShoppingUtils.setisSetting(true);
            settingChanged();
        } else {
            mRecyclerview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void initData() {
        initData2();
        judgeNumberisZero();
        setCheckAll();
        setSetting();
        setDelete();
    }

    private void setDelete() {
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int allChecked = myShoppingUtils.getAllChecked();
                if (allChecked != 0) {
                    setAlertDialog(allChecked);
                }
            }
        });
    }

    protected void setAlertDialog(int allchecked) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("提醒：");
        builder.setMessage("是否要删除这" + allchecked + "项物品?");

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                List<Map<String, String>> data = myShoppingUtils.getData();

                for (int j = 0; j < data.size(); j++) {
                    Map<String, String> map = data.get(j);
                    if (map.get("ischecked").equals("true")) {
                        data.remove(j);
                        j--;
                    }
                }
                myShoppingBasketAdapter.refresh(data);
                myShoppingUtils.setData(data);

                judgeNumberisZero();

                cb_all.setChecked(false);

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

    private void setSetting() {
        tb_shopping_cart.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {
                int allNumber = myShoppingUtils.getAllNumber();
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

    private void settingChanged() {
        boolean isSetting = myShoppingUtils.getisSetting();
        if (isSetting) {
            myShoppingUtils.setisSetting(false);
            ll_check_all.setVisibility(View.VISIBLE);
            ll_delete.setVisibility(View.GONE);
        } else {
            myShoppingUtils.setisSetting(true);
            ll_check_all.setVisibility(View.GONE);
            ll_delete.setVisibility(View.VISIBLE);
        }
        setAllUnChecked();
        cb_all.setChecked(false);
        checkbox_all.setChecked(false);
        myShoppingUtils.setAllCount(0);
        myShoppingBasketAdapter.setAllcount(0);
        myShoppingBasketAdapter.refresh(myShoppingUtils.getData());
    }

    private void setCheckAll() {
        cb_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Map<String, String>> data = myShoppingUtils.getData();
                if (cb_all.isChecked()) {
                    for (int i = 0; i < data.size(); i++) {
                        Map<String, String> map = data.get(i);
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
                        map.put("img", map.get("img"));
                        map.put("title", map.get("title"));
                        map.put("price", map.get("price"));
                        map.put("ischecked", "false");
                        map.put("num", map.get("num"));
                        data.set(i, map);
                    }
                }
                myShoppingUtils.setData(data);
                myShoppingBasketAdapter.refresh(data);
            }
        });

        checkbox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkbox_all.isChecked()) {
                    setAllChecked();

                } else {
                    setAllUnChecked();
                }
                double allCount = myShoppingUtils.getAllCount();
                String s = "" + allCount;
                if (s.contains(".")) {
                    tv_shopcart_total.setText("￥" + allCount + "0");
                } else {
                    tv_shopcart_total.setText("￥" + allCount + ".00");
                }


                myShoppingBasketAdapter.refresh(myShoppingUtils.getData());
                myShoppingBasketAdapter.setAllcount(allCount);
            }
        });
    }

    private void setAllUnChecked() {
        List<Map<String, String>> data = myShoppingUtils.getData();
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> map = data.get(i);
            map.put("img", map.get("img"));
            map.put("title", map.get("title"));
            map.put("price", map.get("price"));
            map.put("ischecked", "false");
            map.put("num", map.get("num"));
            data.set(i, map);

            double allCount = myShoppingUtils.getAllCount();
            allCount -= Integer.parseInt(map.get("price")) * Integer.parseInt(map.get("num"));
            myShoppingUtils.setAllCount(allCount);
        }

        myShoppingBasketAdapter.setCheckedcount(0);
        myShoppingUtils.setData(data);
    }

    private void setAllChecked() {
        List<Map<String, String>> data = myShoppingUtils.getData();
        for (int i = 0; i < data.size(); i++) {
            Map<String, String> map = data.get(i);
            map.put("img", map.get("img"));
            map.put("title", map.get("title"));
            map.put("price", map.get("price"));
            String ischecked = map.get("ischecked");
            map.put("ischecked", "true");
            map.put("num", map.get("num"));
            data.set(i, map);

            if (ischecked.equals("false")) {
                double allCount = myShoppingUtils.getAllCount();
                allCount += Integer.parseInt(map.get("price")) * Integer.parseInt(map.get("num"));
                myShoppingUtils.setAllCount(allCount);
            }

            myShoppingBasketAdapter.setCheckedcount(data.size());
        }
        myShoppingUtils.setData(data);
    }

    private void initData2() {
        List<Map<String, String>> data = myShoppingUtils.getData();
        for (int i = 0; i < 10; i++) {

            Map<String, String> map = new HashMap<>();
            map.put("img", "http://www.qubaobei.com//ios//cf//uploadfile//132//9//8289.jpg");
            map.put("title", "大虾" + i);
            map.put("price", 10 + i + "");
            map.put("ischecked", "false");
            map.put("num", "1");
            data.add(map);
        }
        myShoppingBasketAdapter.refresh(data);
        myShoppingUtils.setData(data);
    }

    protected void setTitleBar() {
        tb_shopping_cart.setCenterText("购物车", 18, Color.RED);
        tb_shopping_cart.setRightText("编辑", 14, Color.BLACK);
    }

    @Override
    protected void initView(View view) {
        tb_shopping_cart = view.findViewById(R.id.tb_shopping_cart);
        iv_shopping_cart = view.findViewById(R.id.iv_shopping_cart);
        mRecyclerview = view.findViewById(R.id.mRecyclerview);
        shoppingcartlayout = view.findViewById(R.id.shoppingcartlayout);
        tv_shopcart_total = view.findViewById(R.id.tv_shopcart_total);
        checkbox_all = view.findViewById(R.id.checkbox_all);
        ll_delete = view.findViewById(R.id.ll_delete);
        ll_check_all = view.findViewById(R.id.ll_check_all);
        cb_all = view.findViewById(R.id.cb_all);
        btn_delete = view.findViewById(R.id.btn_delete);
        btn_check_out = view.findViewById(R.id.btn_check_out);


        myShoppingUtils = ShoppingUtils.getInstance();

        setTitleBar();
        setRecycler();
        setCheckOut();
    }

    private void setCheckOut() {
        btn_check_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Map<String, String>> data = myShoppingUtils.getData();
                List<Map<String, String>> data2 = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    Map<String, String> map = data.get(i);
                    String ischecked = map.get("ischecked");
                    if (ischecked.equals("true")) {
                        data2.add(map);
                    }
                }
                myShoppingUtils.setBuyThings(data2);

                startActivity(new Intent(getContext(), OrderActivity.class));
            }
        });
    }

    private void setRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerview.setLayoutManager(manager);
        myShoppingBasketAdapter = new MyShoppingBasketAdapter(getContext(), this, handler);
        mRecyclerview.setAdapter(myShoppingBasketAdapter);
        ((SimpleItemAnimator) mRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_shopping_cart;
    }

    @Override
    public void addNumber(View view, int value, String price, boolean ischecked, int postion) {
        add(view);

        List<Map<String, String>> data = myShoppingUtils.getData();
        Map<String, String> map = data.get(postion);
        map.put("img", map.get("img"));
        map.put("title", map.get("title"));
        map.put("price", map.get("price"));
        map.put("ischecked", map.get("ischecked"));
        map.put("num", value + "");
        data.set(postion, map);

        myShoppingUtils.setData(data);
        double allCount = myShoppingUtils.getAllCount();
        myShoppingBasketAdapter.refresh2(data, postion, allCount);

        if (ischecked) {
            allCount += Integer.parseInt(price);
            String s = "" + allCount;
            if (s.contains(".")) {
                tv_shopcart_total.setText("￥" + allCount + "0");
            } else {
                tv_shopcart_total.setText("￥" + allCount + ".00");
            }
            myShoppingBasketAdapter.setAllcount(allCount);
            myShoppingUtils.setAllCount(allCount);
        }
    }

    @Override
    public void subNumner(View view, int value, String price, boolean ischecked, int postion) {

        List<Map<String, String>> data = myShoppingUtils.getData();

        Map<String, String> map = data.get(postion);
        map.put("img", map.get("img"));
        map.put("title", map.get("title"));
        map.put("price", map.get("price"));
        map.put("ischecked", map.get("ischecked"));
        map.put("num", value + "");
        data.set(postion, map);

        myShoppingUtils.setData(data);

        double allCount = myShoppingUtils.getAllCount();
        myShoppingBasketAdapter.refresh2(data, postion, allCount);

        if (ischecked) {
            allCount -= Integer.parseInt(price);
            String s = "" + allCount;
            if (s.contains(".")) {
                tv_shopcart_total.setText("￥" + allCount + "0");
            } else {
                tv_shopcart_total.setText("￥" + allCount + ".00");
            }
            myShoppingBasketAdapter.setAllcount(allCount);
            myShoppingUtils.setAllCount(allCount);
        }
    }

    public void add(final View view) {

        //贝塞尔起始数据点
        int[] startPosition = new int[2];
        //贝塞尔结束数据点
        int[] endPosition = new int[2];
        //控制点
        int[] recyclerPosition = new int[2];

        view.getLocationInWindow(startPosition);
        iv_shopping_cart.getLocationInWindow(endPosition);
        mRecyclerview.getLocationInWindow(recyclerPosition);

        PointF startF = new PointF();
        PointF endF = new PointF();
        PointF controllF = new PointF();

        startF.x = startPosition[0];
        startF.y = startPosition[1] - recyclerPosition[1] + tb_shopping_cart.getHeight() + 60;
        endF.x = endPosition[0];
        endF.y = endPosition[1] - recyclerPosition[1] + tb_shopping_cart.getHeight() + 60;
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


        ObjectAnimator objectAnimatorX = new ObjectAnimator().ofFloat(iv_shopping_cart, "scaleX", 0.6f, 1.0f);
        ObjectAnimator objectAnimatorY = new ObjectAnimator().ofFloat(iv_shopping_cart, "scaleY", 0.6f, 1.0f);
        objectAnimatorX.setInterpolator(new AccelerateInterpolator());
        objectAnimatorY.setInterpolator(new AccelerateInterpolator());
        AnimatorSet set = new AnimatorSet();
        set.play(objectAnimatorX).with(objectAnimatorY).after(valueAnimator);
        set.setDuration(800);
        set.start();
    }
}
