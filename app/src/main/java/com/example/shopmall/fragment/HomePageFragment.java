package com.example.shopmall.fragment;

import androidx.fragment.app.Fragment;

import android.view.View;

import com.example.base.BaseFragment;
import com.example.common.TitleBar;
import com.example.shopmall.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomePageFragment extends BaseFragment {

    TitleBar tb_homepage;

    @Override
    protected void initData() {
        tb_homepage.setCenterText("首页");
    }

    @Override
    protected void initView(View view) {
        tb_homepage = view.findViewById(R.id.tb_homepage);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_home_page;
    }
}
