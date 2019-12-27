package com.example.buy.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.graphics.Color;

import com.example.buy.R;
import com.example.buy.adapter.MyShoppingSendAdapter;
import com.example.buy.bean.PayGoodsBean;
import com.example.buy.presenter.SendPresenter;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IGetBaseView;
import com.example.framework.manager.ShoppingManager;
import com.example.framework.manager.UserManager;

import java.util.HashMap;
import java.util.List;

public class SendGoodsActivity extends BaseActivity implements IGetBaseView<PayGoodsBean> {
    private TitleBar tbSendgoods;
    private RecyclerView rvSendgoods;
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

        presenter = new SendPresenter("findForSend", PayGoodsBean.class, hashMap);
        presenter.attachGetView(this);
        presenter.getGetData();
    }

    private void initRecycler() {
        LinearLayoutManager manager = new LinearLayoutManager(this);
        rvSendgoods.setLayoutManager(manager);
        myShoppingSendAdapter = new MyShoppingSendAdapter();
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
    public void onGetDataSucess(PayGoodsBean data) {
        List<PayGoodsBean.ResultBean> result = data.getResult();
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
}
