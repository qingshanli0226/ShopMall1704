package com.example.shopmall.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.buy.bean.ShoppingCartBean;
import com.example.buy.fragment.ShoppingCartFragment;
import com.example.buy.presenter.ShoppingCartPresenter;
import com.example.common.BottomBar;
import com.example.common.ShoppingCartView;
import com.example.framework.base.BaseActivity;
import com.example.framework.manager.ShoppingManager;
import com.example.framework.manager.UserManager;
import com.example.shopmall.R;
import com.example.shopmall.fragment.ClassifyFragment;
import com.example.shopmall.fragment.HomePageFragment;
import com.example.shopmall.fragment.HorizontalFragment;
import com.example.shopmall.fragment.MineFragment;
import com.example.shopmall.jpush.ExampleUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 主页
 */
public class MainActivity extends BaseActivity implements ShoppingManager.OnNumberChangedListener {

    //数据
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    //记录当前正在显示的Fragment
    private Fragment currentFragment;

    private BottomBar bbMain;

    //购物车商品数量
    private int allNumber;

    private ShoppingCartView mRedMessage;
    private RelativeLayout mRlBottom;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }
    @Override
    public void initView() {
        bbMain = findViewById(R.id.bb_main);
        mRedMessage = findViewById(R.id.shopping_message);
        mRlBottom = findViewById(R.id.rl_main);
        fragmentArrayList.add(new HomePageFragment());
        fragmentArrayList.add(new ClassifyFragment());
        fragmentArrayList.add(new HorizontalFragment());
        fragmentArrayList.add(new ShoppingCartFragment());
        fragmentArrayList.add(new MineFragment());
    }

    @Override
    protected void onResume() {
        super.onResume();
        int mainitem = ShoppingManager.getInstance().getMainitem();
        bbMain.setCheckedItem(mainitem);
        if (mainitem == 3) {
            refreshShoppingCartData();
        }
    }

    @Override
    public void initData() {
        ShoppingManager.getInstance().setOnNumberChangedListener(this);
        refreshShoppingCartData();
        //获取购物车商品数量
        allNumber = ShoppingManager.getInstance().getAllNumber();
        mRedMessage.setNum(allNumber);
        ShoppingManager.getInstance().setOnNumberChangedListener(this);

        Log.d("####", "initData: " + allNumber);
        replaceFragment(fragmentArrayList.get(0));

        String[] str = new String[]{"首页", "分类", "发现", "购物车", "个人中心"};

        bbMain.setBottombarName(str);
        Drawable homepageImage = getResources().getDrawable(R.drawable.homepage_image);
        Drawable classifyImage = getResources().getDrawable(R.drawable.classify_image);
        Drawable horizontalImage = getResources().getDrawable(R.drawable.horizontal_image);
        Drawable shoppingcartImage = getResources().getDrawable(R.drawable.shoppingcart_image);
        Drawable mineImage = getResources().getDrawable(R.drawable.mine_image);
        Drawable[] integers = new Drawable[]{homepageImage, classifyImage, horizontalImage, shoppingcartImage, mineImage};
        bbMain.setTapDrables(integers);

        bbMain.setOnTapListener(new BottomBar.OnTapListener() {
            @Override
            public void tapItemClick(int i) {
                replaceFragment(fragmentArrayList.get(i));
                ShoppingManager.getInstance().setAllCount(0);
                ShoppingManager.getInstance().setisSetting(false);
                if (i == 3) {
                    refreshShoppingCartData();
                }
            }
        });

        WindowManager windowManager = getWindowManager();
        Display defaultDisplay = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        int widthPixels = displayMetrics.widthPixels;
        mRedMessage.setX(widthPixels/6*4);
    }


    public void refreshShoppingCartData() {
        boolean loginStatus = UserManager.getInstance().getLoginStatus();
        if(loginStatus){
            String token = ShoppingManager.getInstance().getToken(MainActivity.this);
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("token", token);

            ShoppingCartPresenter presenter = new ShoppingCartPresenter("getShortcartProducts", ShoppingCartBean.class, hashMap);
            presenter.attachGetView((ShoppingCartFragment) fragmentArrayList.get(3));
            presenter.getGetData();
        }else {
            setAlertDialog();
        }

    }

    private void setAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示：");
        builder.setMessage("您还没有登录");
        builder.setPositiveButton("前往登录", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ShoppingManager.getInstance().setMainitem(0);
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.shopmall.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";

    public void registerMessageReceiver() {
        mMessageReceiver = new MessageReceiver();
        IntentFilter filter = new IntentFilter();
        filter.setPriority(IntentFilter.SYSTEM_HIGH_PRIORITY);
        filter.addAction(MESSAGE_RECEIVED_ACTION);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, filter);
    }

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!ExampleUtil.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
//                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }


    private void replaceFragment(Fragment fragment) {
        //获取管理者,开启事务
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //替换功能
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        //判断要添加的fragment时候被添加过
        if (fragment.isAdded()) {
            //显示传过来的
            fragmentTransaction.show(fragment);
        } else {//没有添加过
            //添加传过来的
            fragmentTransaction.add(R.id.fl_main, fragment);
        }

        //提交
        fragmentTransaction.commit();

        //更新当前正在显示的fragment
        currentFragment = fragment;

    }

    @Override
    public void NumberChanged(int num) {

        ShoppingManager.getInstance().setAfternum(num);

        int beforenum = ShoppingManager.getInstance().getBeforenum();
        int afternum = ShoppingManager.getInstance().getAfternum();

        if (beforenum != afternum) {
            ShoppingManager.getInstance().setBeforenum(afternum);
            mRedMessage.setNum(afternum);
        }

    }
}
