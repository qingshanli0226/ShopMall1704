package com.example.administrator.shaomall.search;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.example.administrator.shaomall.R;
import com.example.commen.util.PinYinUtil;
import com.shaomall.framework.base.BaseMVPActivity;
import com.shaomall.framework.bean.SearchBean;

import java.util.List;

public class SearchInfoActivity extends BaseMVPActivity<SearchBean> {
    private android.widget.ImageView titleScanning;
    private android.widget.ImageView titleBlack;
    private android.widget.ImageView titleMessage;
    private android.widget.ListView searchInfoLv;
    private String search_name;
    private EditText titleSearchTv;
    private android.widget.RelativeLayout titleSearchRv;
    private android.widget.ImageView titleCamera;
    private SearchPresenter searchPresenter;
    private SearchListAdapter adapter;

    @Override
    protected int setLayoutId() {
        return R.layout.activity_search_info;
    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        search_name = extras.getString("search_name");
        titleScanning = findViewById(R.id.title_scanning);
        titleBlack =  findViewById(R.id.title_black);
        titleMessage =  findViewById(R.id.title_message);
        searchInfoLv =  findViewById(R.id.search_info_lv);
        titleSearchTv = findViewById(R.id.title_search_tv);
        titleSearchRv =  findViewById(R.id.title_search_rv);
        titleCamera =findViewById(R.id.title_camera);
        initTitle();
    }

    private void initTitle() {
        titleMessage.setImageResource(R.drawable.go_to_right);
        titleScanning.setVisibility(View.GONE);
        titleBlack.setVisibility(View.VISIBLE);
        titleSearchTv.setHint(search_name);
        titleBlack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animOutActivity();
            }
        });

        titleMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pingYin = PinYinUtil.getPingYin(titleSearchTv.getText().toString());
               searchPresenter = new SearchPresenter(pingYin);
                searchPresenter.attachView(SearchInfoActivity.this);
                searchPresenter.doGetHttpRequest();
            }
        });
    }

    @Override
    protected void initData() {

        String pingYin = PinYinUtil.getPingYin(search_name);
        searchPresenter = new SearchPresenter(pingYin);
        searchPresenter.attachView(this);
        searchPresenter.doGetHttpRequest();
    }
    @Override
    public void onRequestHttpDataListSuccess(String message, List data) {
        List<SearchBean> searchBeans = data;

        Log.i("TAG", "onRequestHttpDataListSuccess: "+searchBeans.size());
        adapter = new SearchListAdapter(searchBeans.get(0).getHot_product_list());
        searchInfoLv.setAdapter(adapter);
    }
}
