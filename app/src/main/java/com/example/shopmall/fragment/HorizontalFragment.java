package com.example.shopmall.fragment;

import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.view.View;

import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.framework.manager.ShoppingManager;
import com.example.shopmall.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HorizontalFragment extends BaseFragment {

    TitleBar tbHorizontal;

    @Override
    protected void initData() {
        ShoppingManager.getInstance().setMainitem(2);
        tbHorizontal.setTitleBacKGround(Color.WHITE);
        tbHorizontal.setCenterText("发现",18,Color.BLACK);

        tbHorizontal.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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
        tbHorizontal = view.findViewById(R.id.tb_horizontal);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_horizontal;
    }
}
