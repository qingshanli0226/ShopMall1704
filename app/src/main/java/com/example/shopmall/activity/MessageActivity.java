package com.example.shopmall.activity;

import android.graphics.Color;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.example.common.LoadingPage;
import com.example.common.TitleBar;
import com.example.framework.base.BaseActivity;
import com.example.shopmall.R;

/**
 * 消息界面
 */
public class MessageActivity extends BaseActivity {

    private LinearLayout llMessage;
    private TitleBar tbMessage;
    private RecyclerView rvMessage;

    @Override
    protected int setLayout() {
        return R.layout.activity_message;
    }

    @Override
    public void initView() {
        llMessage = findViewById(R.id.ll_message);
        tbMessage = findViewById(R.id.tb_message);
        rvMessage = findViewById(R.id.rv_message);
    }

    @Override
    public void initData() {
        tbMessage.setBackgroundColor(Color.RED);
        tbMessage.setLeftImg(R.drawable.left);
        tbMessage.setCenterText("消息中心",18,Color.WHITE);

        tbMessage.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

    }
}
