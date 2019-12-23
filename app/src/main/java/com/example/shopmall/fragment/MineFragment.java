package com.example.shopmall.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.buy.activity.SendGoodsActivity;
import com.example.framework.manager.ShoppingManager;
import com.example.shopmall.R;
import com.example.shopmall.activity.AddressBarActivity;
import com.example.shopmall.activity.CollectionActivity;
import com.example.shopmall.activity.SetActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.bean.LoginBean;
import com.example.framework.bean.ResultBean;
import com.example.framework.manager.UserManager;
import com.example.shopmall.presenter.AutomaticPresenter;
import com.example.shopmall.presenter.UpImgPresenter;
import com.example.step.Ui.IntegralActivity;
import com.example.shopmall.activity.LoginActivity;
import com.wyp.avatarstudio.AvatarStudio;

//个人页面
public class MineFragment extends BaseFragment implements IPostBaseView<Object> {

    private TitleBar tbMine;
    private TextView tvUserScore;
    private TextView tvUsername;
    private TextView tvSendgoods;
    private ImageView ibUserIconAvator;
    private AutomaticPresenter automaticPresenter;
    private UpImgPresenter upImgPresenter;
    private LinearLayout llUserLocation;
    private LinearLayout llCollect;

    @Override
    protected void initData() {
        ShoppingManager.getInstance().setMainitem(4);
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

        UserManager.getInstance().setiUserInterface(new UserManager.IUserInterface() {
            @Override
            public void setUserDesc(final ResultBean resultBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvName.setText("用户昵称:" + resultBean.getName());
                    }
                });
            }
        });

        boolean liginStatus = UserManager.getInstance().getLoginStatus();
        if (liginStatus) {
//            ResultBean user = UserManager.getInstance().getUser(getActivity());
//            mTvName.setText("用户昵称:" + user.getName());
        }

        SharedPreferences login = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
        String getToken = login.getString("getToken", "");
        boolean isAutomatic = login.getBoolean("isAutomatic", false);
        if (isAutomatic) {
            automaticPresenter = new AutomaticPresenter(getToken);
            automaticPresenter.attachPostView(this);
            automaticPresenter.getPostFormData();
        }

        ibUserIconAvator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("login", Context.MODE_PRIVATE);
                boolean isLogin = sharedPreferences.getBoolean("isLogin", false);
                if (isLogin) {
                    new AvatarStudio.Builder(getActivity())
                            .setText("相机", "相册", "取消")
                            .setTextColor(Color.RED)
                            .setAspect(1, 1)
                            .setOutput(100, 100)
                            .dimEnabled(true)
                            .show(new AvatarStudio.CallBack() {
                                @Override
                                public void callback(String uri) {
                                    Log.e("####", uri);
                                    ibUserIconAvator.setImageURI(Uri.parse(uri));
                                    if (uri != null) {
                                        String token = UserManager.getInstance().getToken();
                                        upImgPresenter = new UpImgPresenter(uri, token);
                                        upImgPresenter.getPostFile();
                                    }
                                }
                            });


                } else {
                    Toast.makeText(getContext(), "请先登录账号", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), LoginActivity.class));
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
        tvSendgoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), SendGoodsActivity.class));
            }
        });
        llUserLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), CollectionActivity.class));
            }
        });
    }

    private TextView mTvName;

    @Override
    public void onDestroy() {
        super.onDestroy();
        automaticPresenter.detachView();

    }

    @Override
    protected void initView(View view) {
        tbMine = view.findViewById(R.id.tb_mine);
        tvUserScore = view.findViewById(R.id.tv_user_score);
        tvUsername = view.findViewById(R.id.tv_username);
        ibUserIconAvator = view.findViewById(R.id.ib_user_icon_avator);
        llUserLocation = view.findViewById(R.id.ll_user_location);
        mTvName = view.findViewById(R.id.tv_user_name);
        tvSendgoods = view.findViewById(R.id.tv_app_sendgoods);
        llUserLocation = view.findViewById(R.id.ll_user_collect);
    }

    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void onPostDataSucess(Object data) {
        if (data instanceof LoginBean) {
            ResultBean result = ((LoginBean) data).getResult();
            UserManager.getInstance().savaToken(result.getToken());
            Toast.makeText(getActivity(), "自动登录成功", Toast.LENGTH_SHORT).show();
            ShoppingManager.getInstance().setMainitem(0);
        }
        Log.e("####", ""+data.toString());

    }

    @Override
    public void onPostDataFailed(String ErrorMsg) {

    }
}
