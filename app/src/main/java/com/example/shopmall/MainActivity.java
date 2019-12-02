package com.example.shopmall;

import android.graphics.Color;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.base.BaseActivity;
import com.example.common.ConnectManager;
import com.example.common.LoadingPage;
import com.example.common.TitleBar;
import com.example.shopmall.fragment.ClassifyFragment;
import com.example.shopmall.fragment.HomePageFragment;
import com.example.shopmall.fragment.HorizontalFragment;
import com.example.shopmall.fragment.MineFragment;
import com.example.shopmall.fragment.ShoppingCartFragment;

import java.util.ArrayList;

public class MainActivity extends BaseActivity{

    TitleBar titleBar;

    //数据
    private ArrayList<Fragment> fragmentArrayList = new ArrayList<>();

    //记录当前正在显示的Fragment
    private Fragment currentFragment;

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    LoadingPage loadingPage;

    RadioGroup rg_main;
    RadioButton rb_homepage;
    RadioButton rb_classify;
    RadioButton rb_horizontal;
    RadioButton rb_shoppingcart;
    RadioButton rb_mine;

    @Override
    public void initView() {
        loadingPage = findViewById(R.id.loading);
        rg_main = findViewById(R.id.rg_main);
        rb_homepage = findViewById(R.id.rb_homepage);
        rb_classify = findViewById(R.id.rb_classify);
        rb_horizontal = findViewById(R.id.rb_horizontal);
        rb_shoppingcart = findViewById(R.id.rb_shoppingcart);
        rb_mine = findViewById(R.id.rb_mine);

        fragmentArrayList.add(new HomePageFragment());
        fragmentArrayList.add(new ClassifyFragment());
        fragmentArrayList.add(new HorizontalFragment());
        fragmentArrayList.add(new ShoppingCartFragment());
        fragmentArrayList.add(new MineFragment());
    }

    @Override
    public void initData() {
        titleBar.setCenterText("标题栏", 30, Color.RED);
        titleBar.setLeftText("左边");
        titleBar.setRightText("右边");
        titleBar.setTitleBacKGround(Color.BLUE);

        titleBar.setTitleClickLisner(new TitleBar.TitleClickLisner() {
            @Override
            public void LeftClick() {
                Log.e("####", "左边");
            }

            @Override
            public void RightClick() {
                Log.e("####", "右边");
            }

            @Override
            public void CenterClick() {
                Log.e("####", "中间");
            }
        });
        loadingPage.start(LoadingPage.LOADING_FAILURE);

        rg_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_homepage:
                        replaceFragment(fragmentArrayList.get(0));
                        break;
                    case R.id.rb_classify:
                        replaceFragment(fragmentArrayList.get(1));
                        break;
                    case R.id.rb_horizontal:
                        replaceFragment(fragmentArrayList.get(2));
                        break;
                    case R.id.rb_shoppingcart:
                        replaceFragment(fragmentArrayList.get(3));
                        break;
                    case R.id.rb_mine:
                        replaceFragment(fragmentArrayList.get(4));
                        break;
                }
            }
        });

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
