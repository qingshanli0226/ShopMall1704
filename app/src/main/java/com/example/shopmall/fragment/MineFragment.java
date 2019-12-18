package com.example.shopmall.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buy.activity.SendGoodsActivity;
import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.bean.HeadBean;
import com.example.shopmall.presenter.IntegerPresenter;
import com.example.step.Ui.IntegralActivity;
import com.example.shopmall.activity.LoginActivity;
import com.wyp.avatarstudio.AvatarStudio;

//个人页面
public class MineFragment extends BaseFragment {

    private TitleBar tbMine;
    private TextView tvUserScore;
    private TextView tvUsername;
    private TextView tvSendgoods;
    private ImageView ibUserIconAvator;

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

                    final String getToken = sharedPreferences.getString("getToken", null);
                    new AvatarStudio.Builder(getActivity())
                            .setText("相机","相册","取消")
                            .setTextColor(Color.RED)
                            .setAspect(1,1)
                            .setOutput(100,100)
                            .dimEnabled(true)
                            .show(new AvatarStudio.CallBack() {
                                @Override
                                public void callback(String uri) {

                                    IntegerPresenter integerPresenter = new IntegerPresenter(Constant.BASE_URL_JSON + "upload", HeadBean.class);
//                                    integerPresenter.attachPostView(this);

                                    if(uri!=null){

                                    }

                                }
                            });


                } else {
                    Toast.makeText(getContext(), "请先登录账号", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(),LoginActivity.class));
                }
            }
        });

        tvSendgoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SendGoodsActivity.class));
            }
        });
    }

    @Override
    protected void initView(View view) {
        tbMine = view.findViewById(R.id.tb_mine);
        tvUserScore = view.findViewById(R.id.tv_user_score);
        tvUsername = view.findViewById(R.id.tv_username);
        ibUserIconAvator = view.findViewById(R.id.ib_user_icon_avator);
        tvSendgoods = view.findViewById(R.id.tv_app_sendgoods);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

}
