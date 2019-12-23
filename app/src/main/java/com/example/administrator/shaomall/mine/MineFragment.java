package com.example.administrator.shaomall.mine;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.shaomall.R;
import com.example.administrator.shaomall.activity.MessageActivity;
import com.example.administrator.shaomall.activity.SettingActivity;
import com.example.administrator.shaomall.function.FunctionActivity;
import com.example.administrator.shaomall.login.LoginActivity;
import com.example.commen.util.ShopMailError;
import com.example.net.AppNetConfig;
import com.example.remindsteporgan.RemindActivity;
import com.shaomall.framework.base.BaseMVPFragment;
import com.shaomall.framework.base.presenter.IBasePresenter;
import com.shaomall.framework.bean.LoginBean;
import com.shaomall.framework.manager.PointManager;
import com.shaomall.framework.manager.ShoppingManager;
import com.shaomall.framework.manager.UserInfoManager;

import java.io.File;

public class MineFragment extends BaseMVPFragment<String> implements View.OnClickListener, UserInfoManager.UserInfoStatusListener, PointManager.CallbackIntegralListener, UserInfoManager.AvatarUpdateListener {
    private android.widget.ImageView mIvHeader;
    private android.widget.TextView mTvUserName;
    private android.widget.Button mBtLogout;
    private TextView mTvPoint;
    private UserInfoManager userInfoManager;
    private IBasePresenter iBasePresenter;
    private String point = "0";
    private PointUpLoadPresenter pointUpLoadPresenter;
    private TextView mTvNoPayment;
    private TextView mTvSendGoods;
    private android.widget.LinearLayout mLlLayoutShow;
    private TextView mTvUserSetting;
    private ImageView mIvMessage;
    private String avatar;
    private int pointSum;

    @Override
    public int setLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        //注册登录监听
        UserInfoManager.getInstance().registerUserInfoStatusListener(this);
        //积分更新
        PointManager.getInstance().registerCallbackIntegralListener(this);
        //头像监听
        UserInfoManager.getInstance().registerAvatarUpdateListener(this);

        userInfoManager = UserInfoManager.getInstance(); //用户信息管理类


        mTvUserSetting = (TextView) view.findViewById(R.id.tv_userSetting); //设置
        mIvMessage = (ImageView) view.findViewById(R.id.iv_message); //消息
        mIvHeader = (ImageView) view.findViewById(R.id.iv_header);
        mTvUserName = (TextView) view.findViewById(R.id.tv_userName);
        TextView mTvProductAttention = (TextView) view.findViewById(R.id.tv_productAttention);
        TextView mTvShopAttention = (TextView) view.findViewById(R.id.tv_shopAttention);
        TextView mTvFavoriteContent = (TextView) view.findViewById(R.id.tv_favoriteContent);
        TextView mTvBrowsingHistory = (TextView) view.findViewById(R.id.tv_browsingHistory);
        mBtLogout = (Button) view.findViewById(R.id.bt_logout);
        mTvPoint = (TextView) view.findViewById(R.id.tv_point);
        mTvNoPayment = (TextView) view.findViewById(R.id.tv_noPayment); //待支付
        mTvSendGoods = (TextView) view.findViewById(R.id.tv_sendGoods); //待发货
        mLlLayoutShow = (LinearLayout) view.findViewById(R.id.ll_layout_show);


        mTvUserSetting.setOnClickListener(this);        //设置
        mIvMessage.setOnClickListener(this);            //消息
        mIvHeader.setOnClickListener(this);             //用户头像
        mTvUserName.setOnClickListener(this);           //用户名称
        mTvProductAttention.setOnClickListener(this);   //商品关注
        mTvShopAttention.setOnClickListener(this);      //店铺关注
        mTvFavoriteContent.setOnClickListener(this);    //喜欢的内容
        mTvBrowsingHistory.setOnClickListener(this);    //浏览记录
        mBtLogout.setOnClickListener(this);             //退出登录
        mTvPoint.setOnClickListener(this);              //运动积分

        mTvNoPayment.setOnClickListener(this);          //待支付
        mTvSendGoods.setOnClickListener(this);          //待发货
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
        if (!userInfoManager.isLogin()) {
            toClass(LoginActivity.class, 4); //跳转用户登录界面
            return;
        }

        switch (v.getId()) {
            case R.id.tv_userSetting: //设置界面

                Bundle bundle = new Bundle();
                bundle.putString("name", mTvUserName.getText().toString());
                bundle.putString("head", AppNetConfig.BASE_URL + avatar);
                toClass(SettingActivity.class, bundle);
                break;
            case R.id.iv_message:   //消息界面
                toClass(MessageActivity.class);
                break;
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
            case R.id.tv_noPayment: //待支付
                findForPayData();
                break;
            case R.id.tv_sendGoods: //待发货
                findForSendData();
                break;

            default:
                toast("点击了其它", false);
        }
    }

    /**
     * 待发货
     */
    private void findForSendData() {
        String title = mTvSendGoods.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        bundle.putString("type", title);
        toClass(FunctionActivity.class, bundle);
    }

    /**
     * 待支付
     */
    private void findForPayData() {
        String title = mTvNoPayment.getText().toString().trim();
        Bundle bundle = new Bundle();
        bundle.putString("type", title);
        bundle.putString("title", title);
        toClass(FunctionActivity.class, bundle);
    }


    /**
     * 跳转积分界面
     */
    private void setPoint() {
        //        mTvPoint.setText();
        //把用户名先传到计步器的页面可以使用数据库存储用户名
        Bundle bundle = new Bundle();
        bundle.putString("username", mTvUserName.getText().toString());
        toClass(RemindActivity.class, bundle);

    }

    /**
     * 退出登录功能
     */
    private void setLogout() {
        //是否处于登录状态
        if (userInfoManager.isLogin()) {
            iBasePresenter = new LogOutPresenter();
            iBasePresenter.attachView(this);
            iBasePresenter.doPostHttpRequest(AppNetConfig.REQUEST_CODE_LOGOUT); //请求退出登录链接
        }
    }


    /**
     * 设置用户数据
     */
    private void setUserData() {

        //判断是否处于登录状态
        if (userInfoManager.isLogin()) {
            showLayoutInfo(true); //显示界面
            mTvPoint.setText("积分: " + pointSum);
            LoginBean loginBean = userInfoManager.readUserInfo();
            String address = (String) loginBean.getAddress();    //获取地址
            //获得头像
            avatar = (String) loginBean.getAvatar();
            String name = loginBean.getName();      //得到名字
            point = (String) loginBean.getPoint();  //获得积分
            //            String email = (String) loginBean.getEmail();    //取得电子邮件
            //            String money = (String) loginBean.getMoney();    //得到钱
            //            String phone = (String) loginBean.getPhone();    //取得电话

            //TODO 设置头像
            if (avatar == null) { //本地默认头像
                avatar = "http://img5.imgtn.bdimg.com/it/u=1441588315,1666293982&fm=26&gp=0.jpg";
                Glide.with(mContext).load(avatar).apply(RequestOptions.circleCropTransform()).into(mIvHeader);
            } else {
                Glide.with(mContext).load(AppNetConfig.BASE_URL + avatar).apply(RequestOptions.circleCropTransform()).into(mIvHeader);
            }

            //这是昵称
            mTvUserName.setText(name);
            //设置积分
            if (point == null) {
                point = "0";
            }

            String str = "积分: " + this.point;
            mTvPoint.setText(str);

        } else {
            mTvPoint.setVisibility(View.GONE);
            mLlLayoutShow.setVisibility(View.GONE);
        }
    }

    /**
     * 头像更新
     *
     * @param path
     */
    @Override
    public void onAvatarUpdate(String path) {
        avatar = path;
        mIvHeader.setImageURI(Uri.fromFile(new File(path)));
        Glide.with(mContext).load(AppNetConfig.BASE_URL + path).apply(RequestOptions.circleCropTransform()).into(mIvHeader);
    }


    /**
     * 用户状态监听
     *
     * @param isLogin
     * @param userInfo
     */
    @Override
    public void onUserStatus(boolean isLogin, LoginBean userInfo) {
        if (isLogin) {
            setUserData();
        } else {
            showLayoutInfo(false);
        }
    }

    /**
     * 步数换算 积分更新
     *
     * @param pointNum
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void onCallbacksIntegral(int pointNum) {
        pointSum = Integer.parseInt(point) + pointNum;
        mTvPoint.setText("积分: " + pointSum);


        //上传当前积分数量
        iBasePresenter = new PointUpLoadPresenter(pointSum);
        iBasePresenter.attachView(this);
        //上传积分
        iBasePresenter.doPostHttpRequest(AppNetConfig.REQUEST_CODE_UPLOAD_POINT);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onRequestHttpDataSuccess(int requestCode, String message, String data) {
        super.onRequestHttpDataSuccess(requestCode, message, data);
        if (requestCode == AppNetConfig.REQUEST_CODE_LOGOUT) {
            UserInfoManager.getInstance().unLogout();
            toast(message + ": " + data, false);
            //清空商品数据
            ShoppingManager.getInstance().removeShoppingCartAllData();
        }
        if (requestCode == AppNetConfig.REQUEST_CODE_UPLOAD_POINT) {
            mTvPoint.setText("积分: " + pointSum);
        }
    }

    @Override
    public void onRequestHttpDataFailed(int requestCode, ShopMailError error) {
        super.onRequestHttpDataFailed(requestCode, error);
        if (requestCode == AppNetConfig.REQUEST_CODE_LOGOUT) {
            toast(error.getErrorMessage(), false);
            userInfoManager.unLogout();
        }
    }

    private void showLayoutInfo(boolean isShow) {
        if (isShow) {
            mTvPoint.setVisibility(View.VISIBLE);
            mLlLayoutShow.setVisibility(View.VISIBLE);
        } else {
            mIvHeader.setImageResource(R.drawable.app_icon);
            mTvUserName.setText(R.string.app_fragment_mine_tv_text_user_name);
            mTvPoint.setVisibility(View.GONE);
            mLlLayoutShow.setVisibility(View.GONE);
        }
    }


    @Override
    public void onDestroy() {
        if (iBasePresenter != null) {
            iBasePresenter.detachView();
        }
        UserInfoManager.getInstance().unRegisterUserInfoStatusListener(this);
        PointManager.getInstance().unRegisterCallbackIntegralListener();
        UserInfoManager.getInstance().unRegisterAvatarUpdateListener(this);

        super.onDestroy();
    }
}
