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
public class ClassifyFragment extends BaseFragment {

    TitleBar tb_classify;

    @Override
    protected void initData() {
        tb_classify.setTitleBacKGround(Color.RED);
        tb_classify.setCenterText("分类",18,Color.WHITE);

        tb_classify.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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
        tb_classify = view.findViewById(R.id.tb_classify);;
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_classify;
    }
}
