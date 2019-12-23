package com.example.buy.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.util.Log;

import com.example.buy.R;
import com.example.buy.adapter.MyShoppingSendAdapter;
import com.example.buy.bean.SendBean;
import com.example.buy.presenter.SendPresenter;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IGetBaseView;
import com.example.framework.manager.ShoppingManager;

import java.util.HashMap;

public class SendGoodsActivity extends BaseActivity implements IGetBaseView<SendBean> {
    TitleBar tbSendgoods;
    RecyclerView rvSendgoods;
    private MyShoppingSendAdapter myShoppingSendAdapter;
    private SendPresenter presenter;

    @Override
    protected int setLayout() {
        return R.layout.activity_send_goods;
    }

    @Override
    public void initView() {
        tbSendgoods = findViewById(R.id.tb_buy_sendgoods);
        rvSendgoods = findViewById(R.id.rv_buy_sendgoods);
    }

    @Override
    public void initData() {
        initTitle();
        initRecycler();
        initData2();
    }

    private void initData2() {
        String token = ShoppingManager.getInstance().getToken(this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);

        presenter = new SendPresenter("findForSend", SendBean.class, hashMap);
        presenter.attachGetView(this);
        presenter.getGetData();
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvSendgoods.setLayoutManager(manager);
        myShoppingSendAdapter = new MyShoppingSendAdapter(this);
        rvSendgoods.setAdapter(myShoppingSendAdapter);
    }

    private void initTitle() {
        tbSendgoods.setCenterText("待发货", 20, Color.WHITE);
        tbSendgoods.setRightText("返回", 13, Color.WHITE);
        tbSendgoods.setBackgroundColor(Color.RED);

        tbSendgoods.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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
    public void onGetDataSucess(SendBean data) {
        Log.e("####get", data.toString());

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
}
