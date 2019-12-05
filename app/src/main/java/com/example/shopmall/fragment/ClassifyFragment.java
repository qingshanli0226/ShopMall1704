package com.example.shopmall.fragment;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.base.BaseFragment;
import com.example.common.TitleBar;
import com.example.shopmall.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ClassifyFragment extends BaseFragment {

    TitleBar tb_classify;
    ListView lv_left;
    RecyclerView rv_right;

    private boolean isFirst = true;

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

        if (isFirst){
            
        }

    }
    @Override
    protected void initView(View view) {
        tb_classify = view.findViewById(R.id.tb_classify);;

        lv_left = view.findViewById(R.id.lv_left);
        rv_right = view.findViewById(R.id.rv_right);

    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_classify;
    }
}
