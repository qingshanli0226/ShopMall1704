package com.example.dimensionleague.find;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.common.code.Constant;
import com.example.common.view.MyToolBar;
import com.example.dimensionleague.R;
import com.example.framework.base.BaseNetConnectFragment;
import com.flyco.tablayout.SlidingTabLayout;
import java.util.ArrayList;
import java.util.List;

public class FindFragment extends BaseNetConnectFragment {
    private SlidingTabLayout tab;
    private ViewPager vp;
    private List<Fragment> list;
    private String[] titles;
    private MyToolBar my_toolbar;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_find;
    }

    @Override
    public void init(View view) {
        super.init(view);
        list = new ArrayList<>();
        tab = view.findViewById(R.id.find_tab);
        vp = view.findViewById(R.id.find_vp);
        my_toolbar = view.findViewById(R.id.my_toolbar);
        my_toolbar.init(Constant.FIND_STYLE);
        my_toolbar.setBackground(getResources().getDrawable(R.drawable.toolbar_style));
    }

    @Override
    public void initDate() {

        list.add(new FindSendFragment());
        list.add(new FindSendFragment());
        list.add(new FindSendFragment());
        list.add(new FindSendFragment());
        list.add(new FindSendFragment());
        list.add(new FindSendFragment());

        titles = new String[]{getString(R.string.find_attention), getString(R.string.find_like), getString(R.string.find_recommend), getString(R.string.find_5G),getString(R.string.find_streaming),getString(R.string.find_video)};
        vp.setAdapter(new MyVPAdapter(getChildFragmentManager()));
        tab.setViewPager(vp, titles);

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
