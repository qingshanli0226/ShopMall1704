package com.example.dimensionleague.mine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.buy.activity.GoodsActivity;
import com.example.buy.activity.OrderActivity;
import com.example.buy.databeans.GoodsBean;
import com.example.common.HomeBean;
import com.example.common.utils.IntentUtil;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.setting.SettingActivity;
import com.example.framework.base.BaseRecyclerAdapter;
import com.example.framework.base.BaseViewHolder;
import com.example.framework.manager.AccountManager;
import com.example.common.port.IAccountCallBack;
import com.example.dimensionleague.R;
import com.example.dimensionleague.home.HomePresenter;
import com.example.dimensionleague.login.activity.LoginActivity;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.net.AppNetConfig;
import com.example.point.activity.IntegralActivity;
import com.example.point.activity.StepActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MineFragment extends BaseNetConnectFragment implements IAccountCallBack {

    private RecyclerView rvList, rvChannel, rvRecommend;
    private ImageView img;
    private TextView name;
    private RelativeLayout relative;
    private NestedScrollView nestedScrollView;
    private MyToolBar myToolBar;

    private HomePresenter homePresenter;
    private List<MineBean> list = new ArrayList<>();
    private List<HomeBean.ResultBean.ChannelInfoBean> channelList = new ArrayList<>();
    private List<HomeBean.ResultBean.SeckillInfoBean.ListBean> recommendlList = new ArrayList<>();

    //TODO 缓存用户信息管理类
    private AccountManager accountManager = AccountManager.getInstance();

    @Override
    public void init(View view) {
        super.init(view);
        myToolBar = view.findViewById(R.id.mine_toolbar);
        nestedScrollView = view.findViewById(R.id.scroll_mine);
        rvList = view.findViewById(R.id.mine_rv_h);
        rvChannel = view.findViewById(R.id.mine_rv_v);
        rvRecommend = view.findViewById(R.id.mine_rv_recommend);
        name = view.findViewById(R.id.mine_user_name);
        img = view.findViewById(R.id.mine_img);
        relative = view.findViewById(R.id.mine_Relative);

        homePresenter = new HomePresenter();
        rvList.setNestedScrollingEnabled(false);
        rvChannel.setNestedScrollingEnabled(false);
        rvRecommend.setNestedScrollingEnabled(false);

        myToolBar.init(Constant.MINE_STYLE);
        myToolBar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));


        //TODO 给 更多 页面进行注册监听
        accountManager.registerUserCallBack(this);
    }

    @Override
    public void showLoading() {
    }

    @Override
    public void hideLoading() {
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void initDate() {
        ifUser();
        list.add(new MineBean(R.drawable.mine_pay, getString(R.string.mine_pay)));
        list.add(new MineBean(R.drawable.mine_receiver, getString(R.string.mine_receiver)));
        list.add(new MineBean(R.drawable.mine_evaluate, getString(R.string.mine_evaluate)));
        list.add(new MineBean(R.drawable.mine_refund, getString(R.string.mine_refund)));
        list.add(new MineBean(R.drawable.mine_indent, getString(R.string.mine_indent)));
        list.add(new MineBean(R.drawable.mine_bean, getString(R.string.mine_bean)));
        list.add(new MineBean(R.drawable.mine_white, getString(R.string.mine_white)));
        list.add(new MineBean(R.drawable.mine_discounts, getString(R.string.mine_discounts)));
        list.add(new MineBean(R.drawable.mine_run, getString(R.string.mine_run)));
        list.add(new MineBean(R.drawable.mine_wallet, getString(R.string.mine_wallet)));
        homePresenter.attachView(this);
        homePresenter.doHttpGetRequest();
        mineListeners();
        nestedScrollView.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if (scrollY < 530) {
                myToolBar.setAlpha(scrollY / 530.0f);
            } else {
                myToolBar.setAlpha(1.0f);
            }
        });

        rvList.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rvList.setAdapter(new BaseRecyclerAdapter<MineBean>(R.layout.item_mine_rv, list) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                holder.getTextView(R.id.mine_rcv_name, list.get(position).getTitle());
                holder.getImageView(R.id.mine_rcv_img, list.get(position).getImg())
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (AccountManager.getInstance().isLogin()) {
                                    Bundle bundle = new Bundle();
                                    switch (position) {
                                        case 0:
                                            //待付款
                                            bundle.putString(IntentUtil.ORDER_SHOW, Constant.WAIT_PAY);
                                            startActivity(OrderActivity.class, bundle);
                                            break;
                                        case 1:
                                            //待发货
                                            bundle.putString(IntentUtil.ORDER_SHOW, Constant.WAIT_SEND);
                                            startActivity(OrderActivity.class, bundle);
                                            break;
                                        case 4:
                                            //我的订单
                                            bundle.putString(IntentUtil.ORDER_SHOW, Constant.ALL_ORDER);
                                            startActivity(OrderActivity.class, bundle);
                                            break;
                                        case 5:
                                            //我的积分
                                            startActivity(IntegralActivity.class, null);
                                            break;
                                        case 8:
                                            //运动
                                            startActivity(StepActivity.class, null);
                                            break;
                                        default:
                                            toast(getActivity(), list.get(position).getTitle());
                                    }
                                } else {
                                    startActivity(LoginActivity.class, null);
                                }
                            }
                        });
            }
        });
        //Channel设置
        rvChannel.setLayoutManager(new GridLayoutManager(getContext(), 5));
        rvChannel.setAdapter(new BaseRecyclerAdapter<HomeBean.ResultBean.ChannelInfoBean>(R.layout.item_mine_rv_h, channelList) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                holder.getTextView(R.id.mine_rcv_h_name, channelList.get(position).getChannel_name());
                holder.getImageView(R.id.mine_rcv_h_img, AppNetConfig.BASE_URl_IMAGE + channelList.get(position).getImage());
            }
        });
        //Recommend设置
        rvRecommend.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvRecommend.setAdapter(new BaseRecyclerAdapter<HomeBean.ResultBean.SeckillInfoBean.ListBean>(R.layout.item_mine_rv_recommend, recommendlList) {
            @Override
            public void onBind(BaseViewHolder holder, int position) {
                holder.getTextView(R.id.mine_rcv_recommend_title, recommendlList.get(position).getName());
                holder.getTextView(R.id.mine_rcv_recommend_price, recommendlList.get(position).getCover_price());
                holder.getImageView(R.id.mine_rcv_recommend_img, AppNetConfig.BASE_URl_IMAGE + recommendlList.get(position).getFigure())
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(holder.itemView.getContext(), GoodsActivity.class);
                                intent.putExtra(IntentUtil.GOTO_GOOD, new GoodsBean(
                                        recommendlList.get(position).getProduct_id(),
                                        0,
                                        recommendlList.get(position).getName(),
                                        recommendlList.get(position).getFigure(),
                                        recommendlList.get(position).getCover_price()));
                                holder.itemView.getContext().startActivity(intent);
                            }
                        });
            }
        });
    }

    private void mineListeners() {
//        所有监听事件
        relative.setOnClickListener(v -> {
            if ((getString(R.string.mine_login).equals(name.getText().toString()))) {
//                登录注册跳转
                startActivity(LoginActivity.class, null);
            } else {
//                跳转到个人信息
                startActivity(SettingActivity.class, null);
            }
        });
    }


    private void ifUser() {
        if (AccountManager.getInstance().isLogin()) {
            if (AccountManager.getInstance().getUser().getName() != null) {
                //登录
                name.setText(AccountManager.getInstance().getUser().getName());
                if (AccountManager.getInstance().getUser().getAvatar() != null) {
                    Glide.with(Objects.requireNonNull(getContext())).load("" + AppNetConfig.BASE_URL + AccountManager.getInstance().getUser().getAvatar()).apply(new RequestOptions().circleCrop()).into(img);
                }
            }
        } else {
            //没有登录
            name.setText(R.string.mine_login);
            Glide.with(getContext()).load(R.drawable.default_head_image).apply(new RequestOptions().circleCrop()).into(img);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    public int getRelativeLayout() {
        return 0;
    }

    @Override
    public void onRequestSuccess(Object data) {
        super.onRequestSuccess(data);

        if (data != null) {
            channelList.clear();
            recommendlList.clear();

            int code = ((HomeBean) data).getCode();
            String msg = ((HomeBean) data).getMsg();
            if (code == Integer.parseInt(Constant.CODE_OK)) {
                channelList.addAll(((HomeBean) data).getResult().getChannel_info());
                recommendlList.addAll(((HomeBean) data).getResult().getRecommend_info());
                rvChannel.getAdapter().notifyDataSetChanged();
                rvRecommend.getAdapter().notifyDataSetChanged();
            } else {
                toast(getActivity(), msg);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (homePresenter != null) {
            homePresenter.detachView();
        }
        homePresenter = null;
        if (accountManager != null) {
            accountManager.unRegisterUserCallBack(this);
        }
        accountManager = null;
    }

    //TODO 用户注册成功后回调
    @Override
    public void onRegisterSuccess() {

    }

    //TODO 用户登录成功后回调
    @Override
    public void onLogin() {
        name.setText(AccountManager.getInstance().getUser().getName());
    }

    @Override
    public void onLogout() {
        name.setText(R.string.mine_login);
        Glide.with(getContext()).load(R.drawable.default_head_image).apply(new RequestOptions().circleCrop()).into(img);
    }

    //TODO 用户更新头像后回调
    @Override
    public void onAvatarUpdate(String url) {
        Glide.with(Objects.requireNonNull(getContext())).load(url).apply(new RequestOptions().circleCrop()).into(img);
    }

    @Override
    public void onConnected() {
        homePresenter.doHttpGetRequest();
        myToolBar.isConnection(true);
    }

    @Override
    public void onDisConnected() {
        myToolBar.isConnection(false);
    }
}
