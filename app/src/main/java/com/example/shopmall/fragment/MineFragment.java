package com.example.shopmall.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import androidx.fragment.app.Fragment;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.step.Ui.IntegralActivity;
import com.example.shopmall.activity.LoginActivity;

//个人页面
public class MineFragment extends BaseFragment {

    private TitleBar tbMine;
    private TextView tvUserScore;
    private TextView tvUsername;
    private ImageButton ibUserIconAvator;

    @Override
    protected void initData() {
        tbMine.setTitleBacKGround(Color.RED);
        tbMine.setCenterText("个人中心", 18, Color.WHITE);
        tbMine.setLeftImg(R.mipmap.new_message_icon);
        tbMine.setRightImg(R.mipmap.new_user_setting);


        tbMine.setTitleClickLisner(new TitleBar.TitleClickLisner() {
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

        tvUserScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), IntegralActivity.class));
            }
        });

        tvUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });


        ibUserIconAvator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
                if (isLogin) {

                } else {
                    Toast.makeText(getContext(), "请先登录账号", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void initView(View view) {
        tbMine = view.findViewById(R.id.tb_mine);
        tvUserScore = view.findViewById(R.id.tv_user_score);
        tvUsername = view.findViewById(R.id.tv_username);
        ibUserIconAvator = view.findViewById(R.id.ib_user_icon_avator);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }
}
