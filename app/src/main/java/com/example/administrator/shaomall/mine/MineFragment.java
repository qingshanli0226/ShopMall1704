package com.example.administrator.shaomall.mine;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.login.LoginActivity;
import com.example.commen.util.ShopMailError;
import com.example.net.AppNetConfig;
import com.example.remindsteporgan.RemindActivity;
import com.shaomall.framework.base.BaseMVPFragment;
import com.shaomall.framework.base.presenter.IBasePresenter;
import com.shaomall.framework.bean.LoginBean;
import com.shaomall.framework.manager.PointManager;
import com.shaomall.framework.manager.UserInfoManager;

public class MineFragment extends BaseMVPFragment<String> implements View.OnClickListener, UserInfoManager.UserInfoStatusListener, PointManager.CallbackIntegralListener {
    private android.widget.ImageView mIvHeader;
    private android.widget.TextView mTvUserName;
    private android.widget.TextView mTvProductAttention;
    private android.widget.TextView mTvShopAttention;
    private android.widget.TextView mTvFavoriteContent;
    private android.widget.TextView mTvBrowsingHistory;
    private android.widget.Button mBtLogout;
    private TextView mTvPoint;
    private UserInfoManager userInfoManager;
    private IBasePresenter logoutPresenter;
    private String point;
    private PointUpLoadPresenter pointUpLoadPresenter;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //注册登录监听
        UserInfoManager.getInstance().registerUserInfoStatusListener(this);
        userInfoManager = UserInfoManager.getInstance(); //用户信息管理类


        mIvHeader = (ImageView) view.findViewById(R.id.iv_header);
        mTvUserName = (TextView) view.findViewById(R.id.tv_userName);
        mTvProductAttention = (TextView) view.findViewById(R.id.tv_productAttention);
        mTvShopAttention = (TextView) view.findViewById(R.id.tv_shopAttention);
        mTvFavoriteContent = (TextView) view.findViewById(R.id.tv_favoriteContent);
        mTvBrowsingHistory = (TextView) view.findViewById(R.id.tv_browsingHistory);
        mBtLogout = (Button) view.findViewById(R.id.bt_logout);
        mTvPoint = (TextView) view.findViewById(R.id.tv_point);

        mIvHeader.setOnClickListener(this);             //用户头像
        mTvUserName.setOnClickListener(this);           //用户名称
        mTvProductAttention.setOnClickListener(this);   //商品关注
        mTvShopAttention.setOnClickListener(this);      //店铺关注
        mTvFavoriteContent.setOnClickListener(this);    //喜欢的内容
        mTvBrowsingHistory.setOnClickListener(this);    //浏览记录
        mBtLogout.setOnClickListener(this);             //退出登录
        mTvPoint.setOnClickListener(this);              //运动积分
    }

    @Override
    protected void initData() {
        super.initData();
        //展示用户信息
        setUserData();

        //积分更新
        PointManager.getInstance().registerCallbackIntegralListener(this);
    }


    @Override
    public void onClick(View v) {

        //判断是否登录, 未登录时,跳转登录界面
        if (!userInfoManager.isLogin()) {
            toClass(LoginActivity.class, 4); //跳转用户登录界面
            return;
        }

        switch (v.getId()) {
            case R.id.iv_header: //点击头像
                toast("点击了头像", false);
                break;
            case R.id.tv_userName:
                toast("点击了用户名", false);
                break;

            case R.id.bt_logout: //退出登录
                setLogout();
                break;
            case R.id.tv_point: //积分系统
                setPoint();
                break;
            default:
                toast("点击了其它", false);
        }
    }

    /**
     * 跳转积分界面
     */
    private void setPoint() {
        //        mTvPoint.setText();
        toClass(RemindActivity.class);
    }

    /**
     * 退出登录功能
     */
    private void setLogout() {
        if (logoutPresenter == null) {
            logoutPresenter = new LogOutPresenter();
            logoutPresenter.attachView(this);
        }
        if (userInfoManager.isLogin()) {
            logoutPresenter.doPostHttpRequest(AppNetConfig.REQUEST_CODE_LOGOUT); //请求退出登录链接
        }
    }

    private void setUserData() {
        //判断是否处于登录状态
        if (userInfoManager.isLogin()) {
            mTvPoint.setVisibility(View.VISIBLE);
            mBtLogout.setVisibility(View.VISIBLE);

            LoginBean loginBean = userInfoManager.readUserInfo();
            Object address = loginBean.getAddress();    //获取地址
            String avatar = (String) loginBean.getAvatar();  //获得头像
            Object email = loginBean.getEmail();    //取得电子邮件
            Object money = loginBean.getMoney();    //得到钱
            String name = loginBean.getName();      //得到名字
            Object phone = loginBean.getPhone();    //取得电话
            point = (String) loginBean.getPoint();  //获得积分

            Log.d("QS", "setUserData: " + avatar);
            //设置头像
            if (avatar == null) {
                avatar = "http://img5.imgtn.bdimg.com/it/u=1441588315,1666293982&fm=26&gp=0.jpg";
                Glide.with(mContext).load(avatar).apply(RequestOptions.circleCropTransform()).into(mIvHeader);
            } else {
                Glide.with(mContext).load(avatar).apply(RequestOptions.circleCropTransform()).into(mIvHeader);
            }
            //这是昵称
            mTvUserName.setText(name);
            //设置积分
            if (point == null) {
                point = "0";
            }
            mTvPoint.setText("积分: " + point);

        } else {
            mTvPoint.setVisibility(View.GONE);
            mBtLogout.setVisibility(View.GONE);
        }
    }


    /**
     * 用户状态监听
     * @param isLogin
     * @param userInfo
     */
    @Override
    public void onUserStatus(boolean isLogin, LoginBean userInfo) {
        if (isLogin) {
            setUserData();
        } else {
            mIvHeader.setImageResource(R.drawable.app_icon);
            mTvUserName.setText(R.string.app_fragment_mine_tv_text_user_name);
            mTvPoint.setVisibility(View.GONE);
            mBtLogout.setVisibility(View.GONE);
        }
    }

    /**
     * 步数换算 积分更新
     *
     * @param pointNum
     */
    @Override
    public void onCallbacksIntegral(int pointNum) {
        int pointSum = pointNum + Integer.getInteger(point);
        mTvPoint.setText("积分: " + pointSum);


        //上传当前积分数量
        if (pointUpLoadPresenter != null) {
            pointUpLoadPresenter = new PointUpLoadPresenter();
            pointUpLoadPresenter.attachView(this);
        }
        pointUpLoadPresenter.setPointSum(pointSum);
        //上传积分
        pointUpLoadPresenter.doPostHttpRequest(AppNetConfig.REQUEST_CODE_UPLOAD_POINT);
    }

    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, String data) {
        super.onRequestHttpDataSuccess(requestCode, message, data);
        if (requestCode == AppNetConfig.REQUEST_CODE_LOGOUT){
            UserInfoManager.getInstance().unLogout();
            toast(message + ": " + data, false);
        }
        if (requestCode == AppNetConfig.REQUEST_CODE_UPLOAD_POINT){
            mTvPoint.setText("积分: "+data);
        }
    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        super.onRequestHttpDataFailed(requestCode, error);
        if (requestCode == AppNetConfig.REQUEST_CODE_LOGOUT){
            toast(error.getErrorMessage(), false);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        UserInfoManager.getInstance().unRegisterUserInfoStatusListener(this);
        PointManager.getInstance().unRegisterCallbackIntegralListener(this);
    }
}
