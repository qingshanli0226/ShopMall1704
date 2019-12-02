package com.example.shopmall.fragment;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.base.BaseFragment;
import com.example.common.TitleBar;
import com.example.shopmall.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorizontalFragment extends BaseFragment {

    TitleBar tb_classify;

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {
        tb_classify = view.findViewById(R.id.tb_classify);
    }

    @Override
    protected int setLayout() {
        return 0;
    }
}
