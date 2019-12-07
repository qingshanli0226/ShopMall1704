package com.example.shopmall.fragment;


import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.view.View;

import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.shopmall.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorizontalFragment extends BaseFragment {

    TitleBar tb_horizontal;

    @Override
    protected void initData() {
        tb_horizontal.setTitleBacKGround(Color.RED);
        tb_horizontal.setCenterText("发现",18,Color.WHITE);

        tb_horizontal.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {

            }

            @Override
            public void CenterClick() {

            }
        });

    }



    @Override
    protected void initView(View view) {
        tb_horizontal = view.findViewById(R.id.tb_horizontal);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_horizontal;
    }
}
