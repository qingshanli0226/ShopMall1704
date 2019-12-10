package com.example.administrator.shaomall;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.QuickContactBadge;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.shaomall.login.ui.LoginActivity;
import com.shaomall.framework.base.BaseMVPFragment;
import com.shaomall.framework.bean.LoginBean;
import com.shaomall.framework.manager.UserInfoManager;

import static com.example.administrator.shaomall.R.mipmap.ic_launcher_round;

public class MineFragment extends BaseMVPFragment implements View.OnClickListener, UserInfoManager.UserInfoStatusListener {
    private android.widget.ImageView mIvHeader;
    private android.widget.TextView mTvUserName;
    private android.widget.TextView mTvProductAttention;
    private android.widget.TextView mTvShopAttention;
    private android.widget.TextView mTvFavoriteContent;
    private android.widget.TextView mTvBrowsingHistory;
    private android.widget.Button mBtLogout;
    private TextView mTvPoint;
    private UserInfoManager userInfoManager;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        userInfoManager = UserInfoManager.getInstance(); //用户信息管理类
        //注册登录监听
        UserInfoManager.getInstance().registerUserInfoStatusListener(this);

        mIvHeader = (ImageView) view.findViewById(R.id.iv_header);
        mTvUserName = (TextView) view.findViewById(R.id.tv_userName);
        mTvProductAttention = (TextView) view.findViewById(R.id.tv_productAttention);
        mTvShopAttention = (TextView) view.findViewById(R.id.tv_shopAttention);
        mTvFavoriteContent = (TextView) view.findViewById(R.id.tv_favoriteContent);
        mTvBrowsingHistory = (TextView) view.findViewById(R.id.tv_browsingHistory);
        mBtLogout = (Button) view.findViewById(R.id.bt_logout);
        mTvPoint = (TextView) view.findViewById(R.id.tv_point);

        mIvHeader.setOnClickListener(this);
        mTvUserName.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        super.initData();
        //展示用户信息
        setUserData();
    }


    @Override
    public void onClick(View v) {

        //判断是否登录, 未登录时,跳转登录界面
        if (!userInfoManager.isLogin()){
            toClass(LoginActivity.class); //跳转用户登录界面
            return;
        }

        switch (v.getId()) {
            case R.id.iv_header: //点击头像
                toast("点击了头像", false);
                break;
            case R.id.tv_userName:
                toast("点击了用户名", false);
                break;
        }
    }

    private void setUserData() {
        //判断是否处于登录状态
        if (userInfoManager.isLogin()) {
            mTvPoint.setVisibility(View.VISIBLE);
            mTvPoint.setVisibility(View.VISIBLE);

            LoginBean loginBean = userInfoManager.readUserInfo();
            Object address = loginBean.getAddress();    //获取地址
            String avatar = (String) loginBean.getAvatar();  //获得头像
            Object email = loginBean.getEmail();    //取得电子邮件
            Object money = loginBean.getMoney();    //得到钱
            String name = loginBean.getName();      //得到名字
            Object phone = loginBean.getPhone();    //取得电话
            Object point = loginBean.getPoint();    //获得积分

            //设置头像
            Glide.with(mContext).load(avatar).apply(RequestOptions.circleCropTransform()).into(mIvHeader);
            //这是昵称
            mTvUserName.setText(name);
            //设置积分
            mTvPoint.setText(point.toString());

        } else {
            mTvPoint.setVisibility(View.GONE);
            mTvPoint.setVisibility(View.GONE);
        }
    }

    @Override
    public void onUserStatus(boolean isLogin, LoginBean userInfo) {
        if (isLogin){
            setUserData();
        }else {
            mIvHeader.setImageResource(R.drawable.app_icon);
            mTvUserName.setText(R.string.app_fragment_mine_tv_text_user_name);
            mTvPoint.setVisibility(View.GONE);
            mTvPoint.setVisibility(View.GONE);
        }
    }
}
