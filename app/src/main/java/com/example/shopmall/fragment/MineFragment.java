package com.example.shopmall.fragment;


import android.graphics.Color;
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
public class MineFragment extends BaseFragment {

    TitleBar tb_mine;

    @Override
    protected void initData() {
        tb_mine.setTitleBacKGround(Color.RED);
        tb_mine.setCenterText("个人中心",18, Color.WHITE);
        tb_mine.setLeftImg(R.mipmap.new_user_setting);
        tb_mine.setRightImg(R.mipmap.new_message_icon);

        tb_mine.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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
        tb_mine = view.findViewById(R.id.tb_mine);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }
}
