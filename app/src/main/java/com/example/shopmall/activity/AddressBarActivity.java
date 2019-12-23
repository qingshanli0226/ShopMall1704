package com.example.shopmall.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.manager.ShoppingManager;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.shopmall.adapter.AddressBarAdapter;
import com.example.shopmall.bean.AutoLoginBean;
import com.example.shopmall.presenter.AutoLoginPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 收货地址
 */
public class AddressBarActivity extends BaseActivity implements IPostBaseView<AutoLoginBean> {

    //头部导航栏
    private TitleBar tbAddressBar;
    //RecyclerView显示地址信息
    private RecyclerView rvAddressBar;
    //点击新建收货地址
    private Button btNewReceivingAddress;

    //用户token
    private String token;
    //自动登录
    private AutoLoginPresenter autoLoginPresenter;

    @Override
    protected void onStart() {
        super.onStart();

        handler.sendEmptyMessage(100);

    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100){
                String getToken = UserManager.getInstance().getToken();

                autoLoginPresenter = new AutoLoginPresenter("autoLogin",getToken);
                autoLoginPresenter.attachPostView(AddressBarActivity.this);
                autoLoginPresenter.getCipherTextData();
            }
        }
    };

    @Override
    protected int setLayout() {
        return R.layout.activity_address_bar;
    }

    @Override
    public void initView() {
        tbAddressBar = findViewById(R.id.tb_address_bar);
        rvAddressBar = findViewById(R.id.rv_address_bar);
        btNewReceivingAddress = findViewById(R.id.bt_new_receiving_address);

        rvAddressBar.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void initData() {
        tbAddressBar.setTitleBacKGround(Color.WHITE);
        tbAddressBar.setCenterText("收货地址",18,Color.BLACK);
        tbAddressBar.setLeftImg(R.drawable.left);

        tbAddressBar.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                finish();
            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });

        //新建地址
        btNewReceivingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressBarActivity.this, LocationActivity.class);
                intent.putExtra("token",token);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onPostDataSucess(AutoLoginBean data) {
        if (data.getCode().equals("200")){
            token = data.getResult().getToken();
            UserManager.getInstance().savaToken(token);
            AddressBarAdapter addressBarAdapter = new AddressBarAdapter();
            List<AutoLoginBean.ResultBean> resultBeans = new ArrayList<>();
            resultBeans.add(data.getResult());
            addressBarAdapter.reFresh(resultBeans);
            rvAddressBar.setAdapter(addressBarAdapter);
        }else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(AddressBarActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (autoLoginPresenter != null){
            autoLoginPresenter.detachView();
        }

        handler.removeCallbacksAndMessages(this);

    }
}