package com.example.shopmall.activity;

import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IBaseView;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.RegisterBean;
import com.example.shopmall.presenter.RegisterPresenter;

import java.util.HashMap;

public class RegisterActivity extends BaseActivity implements IBaseView<RegisterBean> {

    TitleBar tb_register;
    EditText et_name;
    EditText et_word;
    Button bt_register;

    RegisterPresenter registerPresenter;

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        tb_register = findViewById(R.id.tb_register);
        et_name = findViewById(R.id.et_name);
        et_word = findViewById(R.id.et_word);
        bt_register = findViewById(R.id.bt_register);
    }

    @Override
    public void initData() {

        tb_register.setBackgroundColor(Color.RED);
        tb_register.setLeftImg(R.drawable.left);
        tb_register.setCenterText("注册",18, Color.WHITE);

        tb_register.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = et_name.getText().toString().trim();
                String word = et_word.getText().toString().trim();
                Log.d("####", "onClick: " + name + "" + word);
                HashMap<String, String> header = new HashMap<>();

                HashMap<String, String> query = new HashMap<>();
                query.put("name",name);
                query.put("password",word);

                registerPresenter = new RegisterPresenter(Constant.REGISTER_URL, RegisterBean.class,query);
                registerPresenter.attachView(RegisterActivity.this);
                registerPresenter.register();

                et_name.setText("");
                et_word.setText("");

            }
        });

    }

    @Override
    public void onGetDataSucess(RegisterBean data) {

    }

    @Override
    public void onPostDataSucess(RegisterBean data) {
        Log.d("####", "onPostDataSucess: " + data.getMessage());
    }

    @Override
    public void onGetDataFailed(String ErrorMsg) {

    }

    @Override
    public void onLoadingPage() {

    }

    @Override
    public void onStopLoadingPage() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        registerPresenter.detachView();

    }
}
