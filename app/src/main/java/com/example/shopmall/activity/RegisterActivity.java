package com.example.shopmall.activity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.bean.UserBean;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.shopmall.bean.RegisterBean;
import com.example.shopmall.presenter.RegisterPresenter;

/**
 * 注册
 */
public class RegisterActivity extends BaseActivity implements IPostBaseView<RegisterBean> {

    private TitleBar tbRegister;
    private EditText etName;
    private EditText etWord;
    private Button btRegister;
    private ImageView ivWord;
    private RegisterPresenter integerPresenter;

    private boolean isView = false;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

            if (msg.what == 100){
                new CountDownTimer(1000*3,1000){

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onTick(long l) {
                        //设置注册按钮为灰色
                        btRegister.setBackgroundTintList(ColorStateList.valueOf(Color.GRAY));
                    }

                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onFinish() {
                        //解除注册按钮2秒锁定
                        btRegister.setEnabled(true);
                        //恢复注册按钮为绿色
                        btRegister.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4CAF50")));
                    }
                }.start();
            }

        }
    };

    @Override
    protected int setLayout() {
        return R.layout.activity_register;
    }

    @Override
    public void initView() {
        tbRegister = findViewById(R.id.tb_register);
        etName = findViewById(R.id.et_name);
        etWord = findViewById(R.id.et_word);
        btRegister = findViewById(R.id.bt_register);
        ivWord = findViewById(R.id.iv_word);
    }

    @Override
    public void initData() {

        tbRegister.setBackgroundColor(Color.RED);
        tbRegister.setLeftImg(R.drawable.left);
        tbRegister.setCenterText("注册", 18, Color.WHITE);

        tbRegister.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initButton();
                String name = etName.getText().toString();
                String pwd = etWord.getText().toString();
                UserBean userBean = new UserBean();
                userBean.setName(name);
                userBean.setPassword(pwd);
                UserManager.getInstance().addUser(userBean);
                integerPresenter = new RegisterPresenter(name, pwd);
                integerPresenter.attachPostView(RegisterActivity.this);
                integerPresenter.getCipherTextData();
                etName.setText("");
                etWord.setText("");
            }
        });

        ivWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isView){
                    isView = true;
                    ivWord.setBackground(getResources().getDrawable(R.drawable.view));
                    etWord.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    initSelection();
                }else {
                    isView = false;
                    ivWord.setBackground(getResources().getDrawable(R.drawable.view_off));
                    etWord.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    initSelection();
                }
            }
        });
    }

    private void initSelection() {
        if (etWord.getText().length() == 0){
            etWord.setSelection(0);
        }else {
            etWord.setSelection(etWord.getText().length());
        }
    }

    //button被点击后
    private void initButton() {
        //设置注册按钮2秒锁定
        btRegister.setEnabled(false);
        handler.sendEmptyMessage(100);
    }

    @Override
    public void onPostDataSucess(RegisterBean data) {
        Toast.makeText(this, data.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }
}