package com.example.shopmall.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.net.Constant;
import com.example.shopmall.R;
import com.example.shopmall.activity.AddressBarActivity;
import com.example.shopmall.activity.SetActivity;
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
    private ImageView ibUserIconAvator;
    private LinearLayout llUserLocation;

    @Override
    protected void initData() {

        tbMine.setTitleBacKGround(Color.WHITE);
        tbMine.setCenterText("个人中心", 18, Color.BLACK);
        tbMine.setLeftImg(R.mipmap.new_message_icon);
        tbMine.setRightImg(R.mipmap.new_user_setting);

        tbMine.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {

            }

            @Override
            public void RightClick() {
                startActivity(new Intent(getContext(), SetActivity.class));
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

        llUserLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences token1 = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                boolean isLogin = token1.getBoolean("isLogin", false);
                if (isLogin){
                    startActivity(new Intent(getContext(), AddressBarActivity.class));
                }else {
                    Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getContext(),LoginActivity.class));
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
        llUserLocation = view.findViewById(R.id.ll_user_location);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

}
