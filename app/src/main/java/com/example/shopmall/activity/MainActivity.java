package com.example.shopmall.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.buy.bean.ShoppingCartBean;
import com.example.buy.fragment.ShoppingCartFragment;
import com.example.buy.presenter.ShoppingCartPresenter;
import com.example.common.BottomBar;
import com.example.framework.base.BaseActivity;
import com.example.framework.manager.ShoppingManager;
import com.example.shopmall.R;
import com.example.shopmall.fragment.ClassifyFragment;
import com.example.shopmall.fragment.HomePageFragment;
import com.example.shopmall.fragment.HorizontalFragment;
import com.example.shopmall.fragment.MineFragment;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends BaseActivity {

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int intExtra = intent.getIntExtra("replacefragment", 0);
        bbMain.setCheckedItem(intExtra);

        if (intExtra == 3) {
            refreshShoppingCartData();
        }
    }

    //数据
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    //记录当前正在显示的Fragment
    private Fragment currentFragment;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    BottomBar bbMain;

    @Override
    public void initView() {
        bbMain = findViewById(R.id.bb_main);

        fragmentArrayList.add(new HomePageFragment());
        fragmentArrayList.add(new ClassifyFragment());
        fragmentArrayList.add(new HorizontalFragment());
        fragmentArrayList.add(new ShoppingCartFragment());
        fragmentArrayList.add(new MineFragment());
    }

    @Override
    public void initData() {
        replaceFragment(fragmentArrayList.get(0));

        String[] str = new String[]{"首页","分类","发现","购物车","个人中心"};

        bbMain.setBottombarName(str);
        Drawable homepageImage = getResources().getDrawable(R.drawable.homepage_image);
        Drawable classifyImage = getResources().getDrawable(R.drawable.classify_image);
        Drawable horizontalImage = getResources().getDrawable(R.drawable.horizontal_image);
        Drawable shoppingcartImage = getResources().getDrawable(R.drawable.shoppingcart_image);
        Drawable mineImage = getResources().getDrawable(R.drawable.mine_image);
        Drawable[] integers = new Drawable[]{homepageImage,classifyImage,horizontalImage,shoppingcartImage,mineImage};
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
    }

    public void refreshShoppingCartData() {
        String token = ShoppingManager.getInstance().getToken(MainActivity.this);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("token", token);

        ShoppingCartPresenter presenter = new ShoppingCartPresenter("getShortcartProducts", ShoppingCartBean.class, hashMap);
        presenter.attachGetView((ShoppingCartFragment) fragmentArrayList.get(3));
        presenter.getGetData();
    }

    private void replaceFragment(Fragment fragment) {
        //获取管理者,开启事务
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        //替换功能
        if (currentFragment != null){
            fragmentTransaction.hide(currentFragment);
        }

        //判断要添加的fragment时候被添加过
        if (fragment.isAdded()){
            //显示传过来的
            fragmentTransaction.show(fragment);
        }else {//没有添加过
            //添加传过来的
            fragmentTransaction.add(R.id.fl_main,fragment);
        }

        //提交
        fragmentTransaction.commit();

        //更新当前正在显示的fragment
        currentFragment = fragment;

    }

}
