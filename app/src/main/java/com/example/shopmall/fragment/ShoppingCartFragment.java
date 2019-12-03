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
public class ShoppingCartFragment extends BaseFragment {

    TitleBar tb_shopping_cart;

    @Override
    protected void initData() {
        tb_shopping_cart.setCenterText("购物车",18,Color.RED);

        tb_shopping_cart.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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
        tb_shopping_cart = view.findViewById(R.id.tb_shopping_cart);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_shopping_cart;
    }
}
