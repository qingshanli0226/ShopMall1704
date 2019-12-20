package com.example.step.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

public class HistoryFragmentAdapter extends FragmentPagerAdapter {

    List<Fragment> fragmentList;
    List<String> tableList;
    public HistoryFragmentAdapter(@NonNull FragmentManager fm,List<Fragment>fragmentList,List<String> tableList) {
        super(fm);
        this.fragmentList=fragmentList;
        this.tableList=tableList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tableList.get(position);
    }
}
