package com.example.dimensionleague.find;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.dimensionleague.search.SearchActivity;
import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.R;
import com.example.dimensionleague.login.activity.LoginActivity;
import com.example.dimensionleague.setting.SettingActivity;
import com.example.framework.base.BaseFragment;
import com.example.framework.base.BaseNetConnectFragment;
import com.example.framework.manager.AccountManager;
import com.example.point.message.MessageActivity;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import java.util.List;

public class FindFragment extends BaseFragment {
    private SlidingTabLayout tab;
    private ViewPager vp;
//    private List<Fragment> list = new ArrayList<>();
    private String[] titles;
    private MyToolBar my_toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void init(View view) {
        tab = view.findViewById(R.id.find_tab);
        vp = view.findViewById(R.id.find_vp);
        my_toolbar = view.findViewById(R.id.my_toolbar);
        my_toolbar.init(Constant.FIND_STYLE);
        my_toolbar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
    }

    @Override
    public void initDate() {
        //TODO 用户信息
        my_toolbar.getFind_user().setOnClickListener(v -> {
            if(AccountManager.getInstance().isLogin()){
                startActivity(SettingActivity.class,null);
            }else{
                Bundle bundle = new Bundle();
                bundle.putString("intent","用户设置");
                startActivity(LoginActivity.class,bundle);
            }
        });
        //TODO 搜索页面
        my_toolbar.getFind_search().setOnClickListener(v -> startActivity(SearchActivity.class,null));

        //TODO 跳转到消息页面
        my_toolbar.getFind_message().setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("intent","消息");
            startActivity(MessageActivity.class,bundle);
        });
//        list.add(new FindSendFragment());
//        list.add(new FindSendFragment());
//        list.add(new FindSendFragment());
//        list.add(new FindSendFragment());
//        list.add(new FindSendFragment());
//        list.add(new FindSendFragment());

        titles = new String[]{getString(R.string.find_attention), getString(R.string.find_like), getString(R.string.find_recommend), getString(R.string.find_5G),getString(R.string.find_streaming),getString(R.string.find_video)};
        vp.setAdapter(new MyVPAdapter(getChildFragmentManager()));
        tab.setViewPager(vp, titles);
    }

    private class MyVPAdapter extends FragmentPagerAdapter {

        MyVPAdapter(@NonNull FragmentManager fm) {
            //noinspection deprecation
            super(fm);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return new FindSendFragment();
        }

        @Override
        public int getCount() {
            return titles.length;
        }
    }
}
