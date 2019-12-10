package com.example.shopmall.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.common.TitleBar;
import com.example.framework.manager.CaCheManager;
import com.example.framework.base.BaseFragment;
import com.example.shopmall.R;
import com.example.shopmall.activity.MessageActivity;
import com.example.shopmall.adapter.MyHomePageAdapter;
import com.example.framework.bean.HomepageBean;

public class HomePageFragment extends BaseFragment{

    TitleBar tb_homepage;
    RecyclerView rv_home_page;

    @Override
    protected void initData() {
        tb_homepage.setTitleBacKGround(Color.RED);
        tb_homepage.setCenterText("首页",18,Color.WHITE);
        tb_homepage.setRightImg(R.mipmap.new_message_icon);

        tb_homepage.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                Log.e("####", "左边");
            }

            @Override
            public void RightClick() {
                Log.e("####", "右边");
                startActivity(new Intent(getContext(), MessageActivity.class));
            }

            @Override
            public void CenterClick() {
                Log.e("####", "中间");
            }
        });
        HomepageBean cacheBean = new CaCheManager(getContext()).getCacheBean(getContext());
        MyHomePageAdapter myHomePageAdapter = new MyHomePageAdapter(getContext(), cacheBean);
        rv_home_page.setAdapter(myHomePageAdapter);



    }

    @Override
    protected void initView(View view) {
        tb_homepage = view.findViewById(R.id.tb_homepage);
        rv_home_page = view.findViewById(R.id.rv_home_page);

        rv_home_page.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_home_page;
    }
}
