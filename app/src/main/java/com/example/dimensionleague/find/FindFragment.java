package com.example.dimensionleague.find;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.example.dimensionleague.R;
import com.example.dimensionleague.home.HomeFragment;
import com.example.framework.base.BaseNetConnectFragment;
import com.flyco.tablayout.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

public class FindFragment extends BaseNetConnectFragment {
    private SlidingTabLayout tab;
    private ViewPager vp;
    private List<Fragment>list;
    private String[] titles;
    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void init(View view) {
        super.init(view);
        list=new ArrayList<>();
        tab=view.findViewById(R.id.find_tab);
        vp =view.findViewById(R.id.find_vp);


    }

    @Override
    public void initDate() {

        list.add(new HomeFragment(1));
        list.add(new HomeFragment(1));
        list.add(new HomeFragment(1));
        list.add(new HomeFragment(1));
        list.add(new HomeFragment(1));
        list.add(new HomeFragment(1));
        titles=new String[]{"关注","喜欢","推荐","5G","直播","视频"};
        vp.setAdapter(new MyVPAdapter(getChildFragmentManager()));
        tab.setViewPager(vp,titles);

    }

    @Override
    public int getRelativeLayout() {
        return 0;
    }

    private class MyVPAdapter extends FragmentPagerAdapter {

        public MyVPAdapter(@NonNull FragmentManager fm) {
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
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }
}
