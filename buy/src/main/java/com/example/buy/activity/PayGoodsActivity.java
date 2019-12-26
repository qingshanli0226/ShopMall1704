package com.example.buy.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

import com.example.buy.R;
import com.example.buy.adapter.MyShoppingSendAdapter;
import com.example.buy.bean.GoodsBean;
import com.example.buy.bean.PayGoodsBean;
import com.example.buy.presenter.SendPresenter;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IGetBaseView;
import com.example.framework.manager.ShoppingManager;
import com.example.framework.manager.UserManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PayGoodsActivity extends BaseActivity implements IGetBaseView<PayGoodsBean>, MyShoppingSendAdapter.OnItemClickListener {


    private TitleBar tbPaygoods;
    private RecyclerView rvPaygoods;
    private SendPresenter presenter;
    private MyShoppingSendAdapter myShoppingSendAdapter;
    private List<PayGoodsBean.ResultBean> result = new ArrayList<>();

    @Override
    protected int setLayout() {
        return R.layout.activity_pay_goods;
    }

    @Override
    public void initView() {
        tbPaygoods = findViewById(R.id.tb_buy_paygoods);
        rvPaygoods = findViewById(R.id.rv_buy_paygoods);
    }

    @Override
    public void initData() {
        boolean loginStatus = UserManager.getInstance().getLoginStatus();
        if(!loginStatus){
            setAlertDialog();
        }
        initTitle();
        initRecycler();
        initData2();
    }

    private void setAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage("您还没有登录");
        builder.setPositiveButton("前往登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShoppingManager.getInstance().setMainitem(5);
                dialogInterface.dismiss();
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initData2() {
        String token = ShoppingManager.getInstance().getToken(this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);

        presenter = new SendPresenter("findForPay", PayGoodsBean.class, hashMap);
        presenter.attachGetView(this);
        presenter.getGetData();
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvPaygoods.setLayoutManager(manager);
        myShoppingSendAdapter = new MyShoppingSendAdapter(this);
        rvPaygoods.setAdapter(myShoppingSendAdapter);
        myShoppingSendAdapter.setOnItemClickListener(this);
    }

    private void initTitle() {
        tbPaygoods.setCenterText("待支付", 20, Color.WHITE);
        tbPaygoods.setRightText("返回", 13, Color.WHITE);
        tbPaygoods.setBackgroundColor(Color.RED);

        tbPaygoods.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {
                ShoppingManager.getInstance().setMainitem(4);
                finish();
            }

            @Override
            public void CenterClick() {

            }
        });
    }

    @Override
    public void onGetDataSucess(PayGoodsBean data) {
        result.clear();
        result.addAll(data.getResult());
        myShoppingSendAdapter.reFresh(result);

    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presenter!=null){
            presenter.detachView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData2();
    }

    @Override
    public void OnItemClick(int position) {
        PayGoodsBean.ResultBean resultBean = result.get(position);
        Intent intent = new Intent(PayGoodsActivity.this, OrderActivity.class);
        intent.putExtra("isPaying",1);
        intent.putExtra("pays",resultBean);
        startActivity(intent);
    }
}
