package com.example.shopmall.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.bean.ResultBean;
import com.example.framework.manager.ShoppingManager;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.shopmall.bean.AddressBean;
import com.example.shopmall.presenter.LogOutPresenter;

/**
 * 设置
 */
public class SetActivity extends BaseActivity implements IPostBaseView<AddressBean> {

    private TitleBar tbSet;
    private Button btLogOut;
    private LogOutPresenter logOutPresenter;
    private SharedPreferences sharedPreferences;

    @Override
    protected int setLayout() {
        return R.layout.activity_set;
    }

    @Override
    public void initView() {
        tbSet = findViewById(R.id.tb_set);
        btLogOut = findViewById(R.id.bt_log_out);
    }

    @Override
    public void initData() {
        tbSet.setTitleBacKGround(Color.WHITE);
        tbSet.setCenterText("设置",18,Color.BLACK);
        tbSet.setLeftImg(R.drawable.left);
        tbSet.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

        if (!UserManager.getInstance().getLoginStatus()){
            initLogin();
        }

        //退出登录
        btLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String getToken = UserManager.getInstance().getToken();
                Log.d("####", "handleMessage: " + getToken);
                if (UserManager.getInstance().getLoginStatus()){
                    logOutPresenter = new LogOutPresenter("logout",getToken);
                    logOutPresenter.attachPostView(SetActivity.this);
                    logOutPresenter.getCipherTextData();
                }else {
                    initLogin();
                }
            }
        });
    }

    private void initLogin() {
        Toast.makeText(SetActivity.this, "请先登录", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(SetActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onPostDataSucess(AddressBean data) {
        if (data.getCode().equals("200")){
            ResultBean resultBean = new ResultBean();
            UserManager.getInstance().setActiveUser(SetActivity.this,resultBean);
            sharedPreferences = getSharedPreferences("login", Context.MODE_PRIVATE);
            sharedPreferences.edit().putBoolean("isLogin",false).apply();
            ShoppingManager.getInstance().setMainitem(0);
            finish();
        }else {
            initLogin();
        }
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(logOutPresenter!=null){
            logOutPresenter.detachView();
        }
    }
}
