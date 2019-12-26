package com.example.shopmall.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.buy.activity.PayGoodsActivity;
import com.example.common.TitleBar;
import com.example.framework.base.BaseFragment;
import com.example.buy.activity.SendGoodsActivity;
import com.example.framework.bean.LoginBean;
import com.example.framework.manager.ShoppingManager;
import com.example.net.NetGetService;
import com.example.shopmall.R;
import com.example.shopmall.activity.AddressBarActivity;
import com.example.shopmall.activity.CollectionActivity;
import com.example.shopmall.activity.SetActivity;
import com.example.framework.base.IPostBaseView;
import com.example.framework.bean.ResultBean;
import com.example.framework.manager.UserManager;
import com.example.shopmall.presenter.AutomaticPresenter;
import com.example.shopmall.presenter.UpImgPresenter;
import com.example.step.Ui.IntegralActivity;
import com.example.shopmall.activity.LoginActivity;
import com.wyp.avatarstudio.AvatarStudio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 个人页面
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private File file1;
    private TitleBar tbMine;
    private TextView tvUserScore;
    private TextView tvPay;
    private TextView tvUsername;
    private ImageView ivUserIconAvator;
    private TextView tvSendgoods;
    private UpImgPresenter upImgPresenter;
    private RelativeLayout rlUserLocation;
    private RelativeLayout rlUserCollect;
    private TextView mTvName;
    private int sum = 0;

    @Override
    protected int setLayout() {
        return R.layout.fragment_mine;
    }
    //
    @Override
    protected void initView(View view) {
        tbMine = view.findViewById(R.id.tb_mine);
        tvUserScore = view.findViewById(R.id.tv_user_score);
        tvUsername = view.findViewById(R.id.tv_username);
        tvPay = view.findViewById(R.id.tv_user_pay);
        ivUserIconAvator = view.findViewById(R.id.iv_user_icon_avator);
        rlUserLocation = view.findViewById(R.id.rl_user_location);
        rlUserCollect = view.findViewById(R.id.rl_user_collect);
        tvSendgoods = view.findViewById(R.id.tv_app_sendgoods);
        mTvName = view.findViewById(R.id.tv_user_name);
    }

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

        //用户登录后在UserManager里存储用户信息并触发监听
        UserManager.getInstance().setiUserInterface(new UserManager.IUserInterface() {
            @Override
            public void setUserDesc(final ResultBean resultBean) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (resultBean.getName() != null) {
                            mTvName.setText("用户昵称:" + resultBean.getName());
                            tvUsername.setVisibility(View.GONE);
                        } else {
                            mTvName.setText("");
                            tvUsername.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        });

        //获取登录状态
        boolean loginStatus = UserManager.getInstance().getLoginStatus();
        ResultBean user = UserManager.getInstance().getUser();
        if (loginStatus) {
            mTvName.setText("用户昵称:" + user.getName());
            tvUsername.setVisibility(View.GONE);
        } else {
            mTvName.setText("");
            tvUsername.setVisibility(View.VISIBLE);
        }

        if (loginStatus) {//如果是正在登录状态
            String avatar = UserManager.getInstance().getUser().getAvatar();//获取用户头像地址
            Retrofit.Builder builder = new Retrofit.Builder();
            builder.baseUrl("http://49.233.93.155:8080/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
                    .create(NetGetService.class)
                    .getGetData("http://49.233.93.155:8080" + avatar, new HashMap<String, String>())
                    .subscribe(new Observer<ResponseBody>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ResponseBody body) {
                            try {
                                Log.e("####", body.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e("####", "" + e.getMessage());
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            Toast.makeText(getActivity(), "未登录", Toast.LENGTH_SHORT).show();
        }

        //相机相册
        ivUserIconAvator.setOnClickListener(new View.OnClickListener() {
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
                                    ivUserIconAvator.setImageURI(Uri.parse(uri));

                                    BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inJustDecodeBounds = true;
                                    BitmapFactory.decodeFile(uri, options);
                                    int outWidth = options.outWidth;
                                    int outHeight = options.outHeight;
                                    int sampleSize = 1;
                                    while (outHeight / sampleSize > 50 || outWidth / sampleSize > 50) {
                                        sampleSize *= 2;
                                    }

                                    options.inJustDecodeBounds = false;
                                    //设置缩放比例
                                    options.inSampleSize = sampleSize;
                                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                                    Bitmap bitmap = BitmapFactory.decodeFile(uri, options);

                                    File file = new File(Environment.getExternalStorageDirectory(), "shop");
                                    if (!file.mkdirs()) {
                                        file.mkdir();
                                    }
                                    String s = System.currentTimeMillis() + ".png";
                                    file1 = new File(file, s);
                                    try {
                                        FileOutputStream fos = new FileOutputStream(file1);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                                    } catch (FileNotFoundException e) {
                                        e.printStackTrace();
                                    }

                                    if (uri != null) {
                                        String token = UserManager.getInstance().getToken();
                                        upImgPresenter = new UpImgPresenter(file1.getAbsolutePath(), token);
                                        upImgPresenter.attachPostView(new IPostBaseView() {
                                            @Override
                                            public void onPostDataSucess(Object data) {
                                                Log.e("####", "" + data.toString());
                                            }

                                            @Override
                                            public void onPostDataFailed(String ErrorMsg) {

                                            }
                                        });
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

        //跳转我的积分页面
        tvUserScore.setOnClickListener(this);
        //跳转登录页面
        tvUsername.setOnClickListener(this);
        //跳转收货地址页面
        rlUserLocation.setOnClickListener(this);
        //跳转待发货页面
        tvSendgoods.setOnClickListener(this);
        //跳转我的收藏页面
        rlUserCollect.setOnClickListener(this);
        //跳转待支付页面
        tvPay.setOnClickListener(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (upImgPresenter != null) {
            upImgPresenter.detachView();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        boolean loginStatus = UserManager.getInstance().getLoginStatus();
        if (loginStatus) {
            switch (v.getId()) {
                case R.id.tv_app_sendgoods:
                    intent.setClass(getContext(), SendGoodsActivity.class);
                    break;
                case R.id.rl_user_collect:
                    intent.setClass(getContext(), CollectionActivity.class);
                    break;
                case R.id.rl_user_location:
                    if (UserManager.getInstance().getLoginStatus()) {
                        SharedPreferences token1 = getContext().getSharedPreferences("login", Context.MODE_PRIVATE);
                        boolean isLogin = token1.getBoolean("isLogin", false);
                        if (isLogin) {
                            intent.setClass(getContext(), AddressBarActivity.class);
                        } else {
                            Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
                            intent.setClass(getContext(), LoginActivity.class);
                        }
                    }
                    break;
                case R.id.tv_username:
                    intent.setClass(getContext(), LoginActivity.class);
                    break;
                case R.id.tv_user_score:
                    intent.setClass(getContext(), IntegralActivity.class);
                    break;
                case R.id.tv_user_pay:
                    intent.setClass(getContext(), PayGoodsActivity.class);
                    break;
                default:
                    break;
            }
            startActivity(intent);
        } else {
            startActivity(new Intent(getContext(), LoginActivity.class));
        }
    }
}

