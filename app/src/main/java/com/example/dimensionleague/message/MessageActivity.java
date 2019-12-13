package com.example.dimensionleague.message;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dimensionleague.R;
import com.example.framework.base.BaseNetConnectActivity;
import com.example.point.activity.HistoryActivity;
import com.example.point.activity.StepActivity;

public class MessageActivity extends BaseNetConnectActivity {
    private ImageView iv_left;
    private TextView physical;
    private ImageView iv_right;
    private LinearLayout layout_titlebar;
    private RecyclerView message_re;

    @Override
    public void init() {
        super.init();
        iv_left = findViewById(com.example.point.R.id.iv_left);
        physical = findViewById(com.example.point.R.id.physical);
        iv_right = findViewById(com.example.point.R.id.iv_right);
        layout_titlebar = findViewById(com.example.point.R.id.layout_titlebar);
        message_re = findViewById(R.id.message_re);
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        physical.setText("消息");
        layout_titlebar.setBackgroundColor(Color.rgb(247, 195, 93));
    }

    @Override
    public void initDate() {
        super.initDate();

    }

    @Override
    public int getRelativeLayout() {
        return super.getRelativeLayout();
    }

    @Override
    public boolean isConnectStatus() {
        return super.isConnectStatus();
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);
    }

    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) and run LayoutCreator again
    }
}
