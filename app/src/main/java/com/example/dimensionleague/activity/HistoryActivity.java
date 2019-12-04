package com.example.dimensionleague.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dimensionleague.R;
import com.example.framework.base.BaseActivity;

import java.util.List;

public class HistoryActivity extends BaseActivity {

    private ImageView iv_left;
    private TextView physical;
    private ImageView iv_right;
    private RecyclerView history_re;

    @Override
    public void init() {
        iv_left=findViewById(R.id.iv_left);
        physical=findViewById(R.id.physical);
        iv_right=findViewById(R.id.iv_right);
        history_re=findViewById(R.id.history_re);
    }

    @Override
    public void initDate() {
        iv_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HistoryActivity.this,StepActivity.class);
                startActivity(intent);
                finish();
            }
        });
        physical.setText("历史步数");


    }

    @Override
    public int getLayoutId() {
        return R.layout.history_activity;
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onHttpRequestDataSuccess(int requestCode, Object data) {

    }

    @Override
    public void onHttpRequestDataListSuccess(int requestCode, List data) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
