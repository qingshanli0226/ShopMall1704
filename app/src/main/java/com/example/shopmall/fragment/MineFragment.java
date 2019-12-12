package com.example.shopmall.fragment;


import android.content.Intent;
import android.graphics.Color;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.shopmall.R;
import com.example.step.Ui.IntegralActivity;
import com.example.shopmall.activity.LoginActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment {

    TitleBar tb_mine;
    private TextView tv_user_score;
    private TextView tv_username;
    private ImageButton imageButton;

    @Override
    protected void initData() {
        tb_mine.setTitleBacKGround(Color.RED);
        tb_mine.setCenterText("个人中心",18, Color.WHITE);
        tb_mine.setLeftImg(R.mipmap.new_message_icon);
        tb_mine.setRightImg(R.mipmap.new_user_setting);

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

        tv_user_score.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), IntegralActivity.class));
            }
        });

        tv_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });





    }

    @Override
    protected void initView(View view) {
        tb_mine = view.findViewById(R.id.tb_mine);
        tv_user_score = view.findViewById(R.id.tv_user_score);
        tv_username = view.findViewById(R.id.tv_username);
        imageButton=view.findViewById(R.id.ib_user_icon_avator);
    }


    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }
}
